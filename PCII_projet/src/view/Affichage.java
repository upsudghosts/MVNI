package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.CheckPoint;
import model.Piste;
import model.Vehicule;

public class Affichage extends JPanel{
	/**Needed labels and buttons for the project.*/
	public JLabel startlabel, scorelabel, deathlabel; 

	private static final long serialVersionUID = 1L;

	//DEfault interface size
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public final int WIDTH = screenSize.width;
    public final int HEIGHT = screenSize.height;
    
    //Horizon height
    private final int horHeight = (int) (this.HEIGHT*0.3);
    
    //Model
    private Vehicule V;
    private Piste P;
    
    //Img Access
    private String parallax_mountains, spaceships, effects;
	ArrayList<BufferedImage> imgV, imgEff, imgBg; 
    
    //Animation vehicule
    private int blink, green_light;
	
    
    public Affichage(Vehicule v, Piste p) throws IOException {
    	this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    	this.V = v;
    	this.P = p;
    	
    	this.P.setHorizon(this.horHeight);
    	this.P.setMaxX(this.WIDTH);
    	this.P.setMaxY(this.HEIGHT);
    	
    	this.P.createTrack();
    	
    	this.parallax_mountains = "./assets/parallax_mountain_pack/layers/";
    	this.spaceships = "./assets/spaceships/";
    	this.effects = "./assets/effects/green-particle/";
    	
    	this.imgBg= new ArrayList<BufferedImage>();
    	this.imgEff= new ArrayList<BufferedImage>();
    	this.imgV= new ArrayList<BufferedImage>();
    	
    	this.loadImgV();
    	this.loadImgEff();
    	this.loadImgBg();
    	
    	
    	this.blink = 0;
    	this.green_light = 0;

		/**Initializing buttons.*/
		this.startlabel = new JLabel("Press SPACE to Start Game");
		this.scorelabel = new JLabel("Score : " + this.V.getPos());
		this.deathlabel = new JLabel();
		
		/**These labels and the button are meant to be seen after death, or in-game. Not before.*/
		this.scorelabel.setVisible(false);
		this.deathlabel.setVisible(false);

    }
    
    /** Gives the default width of the interface
     * @return an int, the width of the interface
     **/
    public int getWidth() {
    	return this.WIDTH;
    }
    
    /** Gives the default heigth of the interface
     * @return an int, the height of the interface
     **/
    public int getHeight() {
    	return this.HEIGHT;
    }
    
    /** Draws the track
     * @param g the Graphics on which we draw
     **/
    private void drawPiste(Graphics g) {
    	g.drawLine(0, this.P.getHorizon(), this.WIDTH, this.P.getHorizon());
    	
    	ArrayList<Point> ptL = this.P.getTrackL();
    	ArrayList<Point> ptR = this.P.getTrackR();
    	ArrayList<CheckPoint> cpL = this.P.getCP();
    	
    	//TrackL
    	Point prevL = null;
		for(Point Temp : ptL) {
			if(prevL != null) {
				g.drawLine(prevL.x, prevL.y, Temp.x, Temp.y);
			}
			prevL = Temp;
		}
    	
    	//TrackR
		Point prevR = null;
		for(Point Temp : ptR) {
			if(prevR != null) {
				g.drawLine(prevR.x, prevR.y, Temp.x, Temp.y);
			}
			prevR= Temp;
		}
		
		//Checkpoints
		for(CheckPoint cp : cpL) {
			g.drawLine(0, cp.getHeight(), WIDTH, cp.getHeight());
		}
    }
    
    /** Draws the current speed and the distance traveled
     * @param g the Graphics on which we draw
     **/
    private void drawScore(Graphics g) {
    	g.setColor(Color.WHITE);
    	String speed = "speed : " + this.P.getSpeed();
    	//g.drawString(speed, WIDTH-90, 25);
    	String dist = "Score : " + this.P.getDist();
    	//g.drawString(dist, WIDTH-90, 45);
    	g.drawString(speed, 10, 25);
    	g.drawString(dist, 10, 45);
    }
    
    /** Draws the vehicle at the right coordinates
     * @param g the Graphics on which we draw
     **/
    private void drawVehicule(Graphics g){
    	Image NewI;
    	
    	Graphics2D g2d = (Graphics2D) g;
    	
    	Point vcoord = V.getCoord();
    	Point center = new Point(vcoord.x+ V.getHitWidth()/2, vcoord.y+V.getHitHeight()/2);
    	
    	Rectangle rect1 = new Rectangle(vcoord.x, vcoord.y, V.getHitWidth(), V.getHitHeight());
    	
    	switch(this.V.getMoveStatus()) {
	    	case "LEFT":
				g2d.rotate(Math.toRadians(-45), rect1.x+rect1.width/4, rect1.y);
				break;
			case "RIGHT":
				g2d.rotate(Math.toRadians(45),rect1.x+rect1.width-(rect1.width/4), rect1.y);
				break;
			case "UP":
				break;
			case "DOWN":
				
				break;
			case "NEUTRAL":
				break;
    	}
    	
        g2d.draw(rect1);
        
        if(this.V.getFlyStatus()) { this.drawFlyEffect(g, vcoord);}
        
        
    	NewI = imgV.get(this.blink).getScaledInstance(this.V.getHitWidth(), this.V.getHitHeight(), BufferedImage.SCALE_SMOOTH);
    	g2d.drawImage(NewI, vcoord.x, vcoord.y, null);
        
        
    	g.drawLine(center.x+5, center.y+5, center.x-5, center.y-5);
    	g.drawLine(center.x-5, center.y+5, center.x+5, center.y-5);
    }
    
    
    /** Draws the background image
     * @param g the graphics on which we draw
     **/
    private void drawBg(Graphics g){
    	Image NewI;
    	for(BufferedImage I : this.imgBg) {
    		NewI = I.getScaledInstance(this.WIDTH, this.horHeight-2, BufferedImage.SCALE_SMOOTH);
    		g.drawImage(NewI, 0,0, null);
    	}
    }
    
    /** Draws an image representing effects where the vehicle is flying
     * @param g the Graphics on which we draw
     * @param coord the Point where we draw the image
     **/
    private void drawFlyEffect(Graphics g, Point coord) {
    	Image NewI;
    	
    	Graphics2D g2d = (Graphics2D) g;
    	
    	NewI = imgEff.get(this.green_light).getScaledInstance(this.V.getHitWidth(), this.V.getHitHeight(), BufferedImage.SCALE_SMOOTH);
    	g2d.drawImage(NewI, coord.x, coord.y+this.V.getHitHeight()/2, null);
    }
    
    /** Draws every element of this interface on a Graphics
     * @param g a Graphics on which we draw
     **/
    public void paint(Graphics g) {
    	paintComponent(g);
    	
    	this.drawPiste(g);
    	this.drawBg(g);
    	this.drawScore(g);
    	this.drawVehicule(g);
    }
    
    /**Repaints the interface : used if there is a change
     * 
     **/
    public void change() {
    	repaint();
    }
    

    /** Changes the**/ /*Indice*/ /** of the vehicle and effect images to animate them
     * 
     **/
    public void incrView() {
    	if(this.blink < 2) { this.blink++; } 
    	else { this.blink = 0;}
    	
    	if(this.green_light < 3) { this.green_light ++; }
    	else { this.green_light = 0; }
    }

    /**Loads and adds all the vehicle images to the imgV ArrayList**/
    private void loadImgV() throws IOException {
     	this.imgV.add(ImageIO.read(new File(this.spaceships+"colored-ufo.png")));
    	this.imgV.add(ImageIO.read(new File(this.spaceships+"colored-ufo-2.png")));
    	this.imgV.add(ImageIO.read(new File(this.spaceships+"colored-ufo-3.png")));
    }
    
    
    /**Loads and adds all the images representing flying effects to the imgEff ArrayList**/
    private void loadImgEff() throws IOException {
    	this.imgEff.add(ImageIO.read(new File(this.effects+"1.png")));
    	this.imgEff.add(ImageIO.read(new File(this.effects+"2.png")));
    	this.imgEff.add(ImageIO.read(new File(this.effects+"3.png")));
    	this.imgEff.add(ImageIO.read(new File(this.effects+"4.png")));
    	
    }

    /** Loads and adds all the elements of the background image to the imgBg ArrayList **/
    private void loadImgBg() throws IOException {
    	this.imgBg.add(ImageIO.read(new File(this.parallax_mountains+"parallax-mountain-bg.png")));
    	this.imgBg.add(ImageIO.read(new File(this.parallax_mountains+"parallax-mountain-montain-far.png")));
    	this.imgBg.add(ImageIO.read(new File(this.parallax_mountains+"parallax-mountain-mountains.png")));
    	this.imgBg.add(ImageIO.read(new File(this.parallax_mountains+"parallax-mountain-trees.png")));
    	this.imgBg.add(ImageIO.read(new File(this.parallax_mountains+"parallax-mountain-foreground-trees.png")));
    	
    }
}


