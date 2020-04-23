package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.CheckPoint;
import model.Obstacle;
import model.Opponent;
import model.Piste;
import model.Vehicule;

public class Affichage extends JPanel{
	/**Needed labels and buttons for the project.*/
	public JLabel startlabel, deathlabel; 

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
    private String parallax_mountains, spaceships, effects, road;
	ArrayList<BufferedImage> imgV, imgEff, imgBg, imgG, imgR; 
    
    //Animation vehicule
    private int blink, green_light;
    
    //Timer
    private long starttime, secPassed, minPassed;
    
    
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
    	this.road = "./assets/road/";
    	
    	this.imgBg= new ArrayList<BufferedImage>();
    	this.imgEff= new ArrayList<BufferedImage>();
    	this.imgV= new ArrayList<BufferedImage>();
    	this.imgG = new ArrayList<BufferedImage>();
    	this.imgR = new ArrayList<BufferedImage>();
    	
    	this.loadImgV();
    	this.loadImgEff();
    	this.loadImgBg();
    	this.loadImgGround();
    	this.loadImgRoad();
    	
    	this.blink = 0;
    	this.green_light = 0;
    	
		this.starttime = System.currentTimeMillis();

		/**Initializing buttons.*/
    	
		this.startlabel = new JLabel("Press SPACE to Start Game");
		this.deathlabel = new JLabel();
		
		
		/**These labels and the button are meant to be seen after death, or in-game. Not before.*/
		this.deathlabel.setVisible(false);

    }
    
    /** Gives the default width of the interface
     * @return an int, the width of the interface
     **/
    public int getWidth() {
    	return WIDTH;
    }
    
    /** Gives the default heigth of the interface
     * @return an int, the height of the interface
     **/
    public int getHeight() {
    	return HEIGHT;
    }
    
    public long getStarttime() {
    	return starttime;
    }
    
    public long getMins() {
    	return minPassed;
    }
    
    public long getSec() {
    	return secPassed;
    }
    
    public void setStarttime() {
    	starttime = System.currentTimeMillis();
    }
    
    public void setMinPassed(long m) {
    	minPassed = m;
    }
    
    public void setSecPassed(long s) {
    	secPassed = s;
    }
    
    /** Draws the track
     * @param g the Graphics on which we draw
     **/
    private void drawPiste(Graphics g) {
    	//Track
    	g.drawLine(0, P.getHorizon(), WIDTH, P.getHorizon());
    	
    	ArrayList<Point> ptL = P.getTrackL();
    	ArrayList<Point> ptR = P.getTrackR();
    	ArrayList<CheckPoint> cpL = P.getCP();
    	
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
		
		//Image
		Polygon poly = new Polygon();

		for(int i=0; i<ptL.size(); i++) {
			Point TempL = ptL.get(i);
			poly.addPoint(TempL.x, TempL.y);
		}
		for(int i=ptL.size()-1; i>=0; i--) {
			Point Temp = ptR.get(i);
			poly.addPoint(Temp.x, Temp.y);
		}

		Graphics g2 = g.create();
		g2.fillPolygon(poly);
		//g2.drawString("test", 500, 500);
		//g2.setClip(poly);
		//Image NewI = imgG.get(0);
    	//g2.drawImage(NewI, 0, 0, null);
		
		/*
		BufferedImage source = imgG.get(0);
		
		GeneralPath clip = new GeneralPath();
		int[] polX = poly.xpoints;
		int[] polY = poly.ypoints;
		clip.moveTo(polX[0], polY[0]);
		for(int i=1; i<polX.length; i++) {
			clip.lineTo(polX[i], polY[i]);
		}
		clip.closePath();
		
		Rectangle bounds = clip.getBounds();
		BufferedImage img = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
		
		clip.transform(AffineTransform.getTranslateInstance(-65, -123));
		g2.setClip(clip);
		g2.translate(-65, -123);
		g2.drawImage(source, 0, 0, null);
		g2.dispose();
		*/
    }
    
    /** Draws the obstacles of the track
     * @param g the Graphics on which we draw
     **/
    private void drawObstacles(Graphics g) {
    	ArrayList<Obstacle> oL = P.getOL();
    	for(Obstacle o : oL) {
    		g.setColor(Color.GRAY);
    		g.drawRect(o.getX(), o.getY(), o.getW(), o.getH());
    		g.setColor(Color.BLACK);
    	}
    	ArrayList<Opponent> opL = P.getOpL();
    	for(Opponent o : opL) {
    		g.setColor(Color.GRAY);
    		//g.fillRect(o.getX(), o.getY(), o.getW(), o.getH()); //pour faire la difference entre les obstacles et les adversaires pendant le test
    		g.drawRect(o.getX(), o.getY(), o.getW(), o.getH());
    		g.setColor(Color.BLACK);
    	}
    }
    
    /** Draws the current speed and the distance traveled
     * @param g the Graphics on which we draw
     **/
    private void drawScore(Graphics g) {
    	g.setColor(Color.WHITE);
    	String speed = "speed : " + P.getSpeed();
    	String distToCheck = "CheckPoint : " + P.toCp();
    	String dist = "Score : " + P.getDist();

    	g.drawString(speed, 10, 25);
    	g.drawString(dist, 10, 45);
    	g.drawString(distToCheck, 10, 65);
    }
    
    /** Draws the time since beginning of the game and time left until next checkpoint
     * @param g the Graphics on which we draw
     **/
    private void drawTimer(Graphics g) {
    	g.setColor(Color.WHITE);
    	
    	String totTime = "Timer : " + minPassed + "::" + secPassed;
    	
    	long minTl = V.getTTL()/60;
    	long secTl = V.getTTL() - minTl*60;
    	String TimeLeft = "Game Over : " + minTl + "::" + secTl;
    	
    	g.drawString(totTime, WIDTH-90, 25);
    	g.drawString(TimeLeft, WIDTH-120, 45);
    	
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
    	
    	switch(V.getMoveStatus()) {
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
        
        if(V.getFlyStatus()) { drawFlyEffect(g, vcoord);}
        
        
    	NewI = imgV.get(blink).getScaledInstance(V.getHitWidth(), V.getHitHeight(), BufferedImage.SCALE_SMOOTH);
    	g2d.drawImage(NewI, vcoord.x, vcoord.y, null);
        
        
    	g.drawLine(center.x+5, center.y+5, center.x-5, center.y-5);
    	g.drawLine(center.x-5, center.y+5, center.x+5, center.y-5);
    }
    
    
    /** Draws the background image
     * @param g the graphics on which we draw
     **/
    private void drawBg(Graphics g){
    	Image NewI;
    	for(BufferedImage I : imgBg) {
    		NewI = I.getScaledInstance(WIDTH, horHeight-2, BufferedImage.SCALE_SMOOTH);
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
    	
    	NewI = imgEff.get(green_light).getScaledInstance(V.getHitWidth(), V.getHitHeight(), BufferedImage.SCALE_SMOOTH);
    	g2d.drawImage(NewI, coord.x, coord.y+V.getHitHeight()/2, null);
    }
    
    /** Draws every element of this interface on a Graphics
     * @param g a Graphics on which we draw
     **/
    public void paint(Graphics g) {
    	paintComponent(g);
    	
    	drawPiste(g);
    	drawBg(g);
    	drawObstacles(g);
    	drawScore(g);
    	drawTimer(g);
    	drawVehicule(g);
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
    	if(blink < 2) { blink++; } 
    	else { blink = 0;}
    	
    	if(green_light < 3) { green_light ++; }
    	else { green_light = 0; }
    }


    /**Loads and adds all the vehicle images to the imgV ArrayList**/
    private void loadImgV() throws IOException {
     	imgV.add(ImageIO.read(new File(spaceships+"colored-ufo.png")));
    	imgV.add(ImageIO.read(new File(spaceships+"colored-ufo-2.png")));
    	imgV.add(ImageIO.read(new File(spaceships+"colored-ufo-3.png")));
    }
    
    
    /**Loads and adds all the images representing flying effects to the imgEff ArrayList**/
    private void loadImgEff() throws IOException {
    	imgEff.add(ImageIO.read(new File(effects+"1.png")));
    	imgEff.add(ImageIO.read(new File(effects+"2.png")));
    	imgEff.add(ImageIO.read(new File(effects+"3.png")));
    	imgEff.add(ImageIO.read(new File(effects+"4.png")));
    	
    }

    /** Loads and adds all the elements of the background image to the imgBg ArrayList **/
    private void loadImgBg() throws IOException {
    	imgBg.add(ImageIO.read(new File(parallax_mountains+"parallax-mountain-bg.png")));
    	imgBg.add(ImageIO.read(new File(parallax_mountains+"parallax-mountain-montain-far.png")));
    	imgBg.add(ImageIO.read(new File(parallax_mountains+"parallax-mountain-mountains.png")));
    	imgBg.add(ImageIO.read(new File(parallax_mountains+"parallax-mountain-trees.png")));
    	imgBg.add(ImageIO.read(new File(parallax_mountains+"parallax-mountain-foreground-trees.png")));
    	
    }
    
    private void loadImgGround() throws IOException {
    	imgG.add(ImageIO.read(new File(parallax_mountains+"parallax-mountain-bg.png"))); //temporaire, uste pour tester
    }
    

    private void loadImgRoad() throws IOException {
    	imgR.add(ImageIO.read(new File(road+"Toon Road Texture.png")));
    }
}


