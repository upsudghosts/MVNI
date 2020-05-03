package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.CheckPoint;
import model.Obstacle;
import model.Opponent;
import model.Piste;
import model.Vehicule;

public class Affichage extends JPanel{
	private static final long serialVersionUID = 1L;

	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                    - MISCELLANIOUS -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	/**Default interface size*/
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public final int WIDTH = screenSize.width;
    public final int HEIGHT = screenSize.height;
    
    /** Gives the default width of the interface
     * @return an int, the width of the interface
     **/
    public int getWidth() {
    	return WIDTH;
    }
    
    /** Gives the default height of the interface
     * @return an int, the height of the interface
     **/
    public int getHeight() {
    	return HEIGHT;
    }
    
    
    /**Horizon height*/
    private final int horHeight = (int) (this.HEIGHT*0.17);
    
    private boolean showHitbox;
    
    /**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                     - OBJECTS -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * - V : the vehicle used by the player.
	 * - P : the track which the player has to follow.
	 * - A : the graphic space .
	 *  
	 */
    private Vehicule V;
    private Piste P;
    
    /**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                     - IMAGE ACCESS -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * - String : the paths to the folders which contain our PNG files..
	 * - img[...] : the ArrayLists which regroup all the images for one object.
	 *  
	 */
    private String spaceships, effects, parallax_mountains, ground, obstacle, checkpt;
	private ArrayList<BufferedImage> imgV, imgEff, imgBg, imgG, imgObst, imgCp; 
	
	/**Loads and adds all the images representing the Vehicle to the imgV ArrayList**/
	private void loadImgV() throws IOException {
    	this.spaceships = "./assets/spaceships/";
    	this.imgV= new ArrayList<BufferedImage>();
    	
     	imgV.add(ImageIO.read(new File(spaceships+"colored-ufo.png")));
     	imgV.add(ImageIO.read(new File(spaceships+"ennemy-ufo.png")));

    }
     
    /**Loads and adds all the images representing flying effects to the imgEff ArrayList**/
    private void loadImgEff() throws IOException {
    	this.effects = "./assets/effects/green-particle/";
    	this.imgEff= new ArrayList<BufferedImage>();
    	
    	imgEff.add(ImageIO.read(new File(effects+"1.png")));
    	imgEff.add(ImageIO.read(new File(effects+"2.png")));
    	imgEff.add(ImageIO.read(new File(effects+"3.png")));
    	imgEff.add(ImageIO.read(new File(effects+"4.png")));
    	
    }

    /** Loads and adds all the elements of the background image to the imgBg ArrayList **/
    private void loadImgBg() throws IOException {
    	this.parallax_mountains = "./assets/parallax_mountain_pack/layers/";
    	this.imgBg= new ArrayList<BufferedImage>();
    	
    	imgBg.add(ImageIO.read(new File(parallax_mountains+"parallax-mountain-bg.png")));
    	imgBg.add(ImageIO.read(new File(parallax_mountains+"parallax-mountain-montain-far.png")));
    	imgBg.add(ImageIO.read(new File(parallax_mountains+"parallax-mountain-mountains.png")));
    	imgBg.add(ImageIO.read(new File(parallax_mountains+"parallax-mountain-trees.png")));
    	imgBg.add(ImageIO.read(new File(parallax_mountains+"parallax-mountain-foreground-trees.png")));
    	
    }
    
    /**Loads and adds all the images representing the ground to the imgG ArrayList**/
    private void loadImgGround() throws IOException {
    	this.ground = "./assets/ground/";
    	this.imgG = new ArrayList<BufferedImage>();
    	
    	imgG.add(ImageIO.read(new File(ground+"grass.png")));
    	
    	for(int i = 1; i < 11; i++) {
    		imgG.add(ImageIO.read(new File(ground+"moving_grass_"+i+".png")));
    	}
    } 

    /**Loads and adds all the images representing Obstacles to the imgObst ArrayList**/
    private void loadImgObst() throws IOException {
    	this.obstacle = "./assets/obstacles/";
    	this.imgObst = new ArrayList<BufferedImage>();
    	
    	imgObst.add(ImageIO.read(new File(obstacle + "pine.png")));
    }
	
    /**Loads and adds all the images representing Obstacles to the imgObst ArrayList**/
    private void loadImgCp() throws IOException {
    	this.checkpt = "./assets/checkpoints/";
    	this.imgCp = new ArrayList<BufferedImage>();
    	
    	imgCp.add(ImageIO.read(new File(checkpt + "cp.png")));
    }
	
    /**Flying orb effect*/
    private int green_light;
   
    /**For parallax effect :speed of different layers of background*/
    private int[] mvBg;

    /**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                     - TIMER -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * - starttime : System.currentMillis() at which the timer starts
	 * - [...]Passes: Seconds and Minutes passed since the beginning of the program.
	 *  
	 */
    private long starttime, secPassed;
    
    /**
     * Function to get the starttime
     * @return a long : the starttime
     */
    public long getStarttime() {
    	return starttime;
    }
    
    /**
     * Function to get the seconds that have passed.
     * @return a long : the seconds
     */
    public long getSec() {
    	return secPassed;
    }
    
    /**
     * Function to set starttime at current time of System
     */
    public void setStarttime() {
    	starttime = System.currentTimeMillis();
    }

    
    /**
     * Function to add to the seconds that have passed
     * @param s the long to add
     */
    public void setSecPassed(long s) {
    	secPassed = s;
    }
    
    
    /*-------------------------------------------------------------------------------------------------------*/
    
    /**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                    - CONSTRUCTOR -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
    
    /**
     * Constructor for the Affichage Class of the MVNI Project.
     * @param : Vehicule the players vehicle
     * @param : Piste the track
     */
    public Affichage(Vehicule v, Piste p) throws IOException {
    	this.mvBg = new int[]{0, 0, 0, 0};

    	this.showHitbox = false;
    	this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    	this.V = v;
    	this.P = p;
    	
    	this.P.setHorizon(this.horHeight);
    	this.P.setMaxX(this.WIDTH);
    	this.P.setMaxY(this.HEIGHT);
    	
    	this.P.createTrack();  	
  
    	this.loadImgV();
    	this.loadImgEff();
    	this.loadImgBg();
    	this.loadImgObst();
    	this.loadImgGround();
    	this.loadImgCp();
    	
    	this.green_light = 0;
    	
		this.starttime = System.currentTimeMillis();
    }
    
    /*-------------------------------------------------------------------------------------------------------*/
    
    /**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                    - DRAWING FUNCTIONS -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

    /** Draws the track
     * @param g2d the graphics2D on which we draw
     **/
    private void drawPiste(Graphics2D g2d) {
    	/** Track */
    	g2d.drawLine(0, P.getHorizon(), WIDTH, P.getHorizon());
    	
    	ArrayList<Point> ptL = P.getTrackL();
    	ArrayList<Point> ptR = P.getTrackR();
    	ArrayList<CheckPoint> cpL = P.getCP();
    	
    	/** Filling the road in black */
    	Polygon poly = new Polygon();

    	for(int i=0; i<ptL.size(); i++) {
    		Point TempL = ptL.get(i);
    		poly.addPoint(TempL.x, TempL.y);
    	}
    	for(int i=ptL.size()-1; i>=0; i--) {
    		Point Temp = ptR.get(i);
    		poly.addPoint(Temp.x, Temp.y);
    	}
    			
    	g2d.fillPolygon(poly);
    	
    	g2d.setColor(Color.WHITE);
    	Stroke s = new BasicStroke(
    				2.0f, // Width 
    				BasicStroke.CAP_SQUARE, // End cap 
    				BasicStroke.JOIN_MITER,// Join style 
    				10.0f, // Miter limit 
    				new float[] {20.0f,20.0f}, // Dash pattern 
    				0.0f);
    	g2d.setStroke(s);
    	g2d.setColor(Color.lightGray);
    	// White lines
    	//Left
    	Polygon polyLG = new Polygon();

    	for(int i=0; i<ptL.size(); i++) {
    		Point TempL = ptL.get(i);
    		int ecart = ptR.get(i).x - TempL.x;
    		polyLG.addPoint(TempL.x+ecart/10, TempL.y);
    	}
    	for(int i=ptL.size()-1; i>=0; i--) {
    		Point TempL = ptL.get(i);
    		int ecart = ptR.get(i).x - TempL.x;
    		polyLG.addPoint(TempL.x+ecart/10+ecart/20, TempL.y);
    	}
    	g2d.fillPolygon(polyLG);
    	
    	//Right 
    	Polygon polyLR = new Polygon();

    	for(int i=0; i<ptR.size(); i++) {
    		Point Temp = ptR.get(i);
    		int ecart = Temp.x - ptL.get(i).x;
    		polyLR.addPoint(Temp.x-ecart/12-ecart/20, Temp.y);
    	}
    	for(int i=ptL.size()-1; i>=0; i--) {
    		Point Temp = ptR.get(i);
    		int ecart = Temp.x - ptL.get(i).x;
    		polyLR.addPoint(Temp.x-ecart/12, Temp.y);
    	}
    	g2d.fillPolygon(polyLR);
    	
    			
    	/** Left track : trackL */
    	Point prevL = null;
		for(int i = 0; i < ptL.size(); i++) {
			Point Temp = ptL.get(i);
			if(prevL != null) {
				g2d.drawLine(prevL.x, prevL.y, Temp.x, Temp.y);
				if(showHitbox) {
					/** Acceleration zone : left delimiter */
					g2d.setColor(Color.RED);
					g2d.drawLine(Temp.x, HEIGHT, Temp.x, Temp.y);
					g2d.setColor(Color.BLACK);
				}
			}
			prevL = Temp;
		}
    	
		/** Right track : trackR */
		Point prevR = null;
		for(int i = 0; i < ptR.size(); i++) {
			Point Temp = ptR.get(i);
			if(prevR != null) {
				g2d.drawLine(prevR.x, prevR.y, Temp.x, Temp.y);
				if(showHitbox) {
					/** Acceleration zone : right delimiter */
					g2d.setColor(Color.RED);
					g2d.drawLine(Temp.x, HEIGHT, Temp.x, Temp.y);
					g2d.setColor(Color.BLACK);
				}
			}
			prevR= Temp;
		}
		
		/** Checkpoints */
		for(CheckPoint cp : cpL) {
			if(showHitbox) {
				g2d.drawLine(0, cp.getHeight(), WIDTH, cp.getHeight());
			}
			for(int x = 0; x < WIDTH; x += imgCp.get(0).getWidth()) {
				g2d.drawImage(
						imgCp.get(0),
						x, cp.getHeight(),
						100, 30,
						null);
			}
			
			
		}
    }
    
    /** Draws the obstacles of the track
     * @param g2d the graphics2D on which we draw
     **/
    private void drawObstacles(Graphics2D g2d) {
    	ArrayList<Obstacle> oL = P.getOL();
 
    	for(int i = oL.size()-1; i >= 0; i--) {
    		Obstacle o = oL.get(i);
    		g2d.setColor(Color.GRAY);

    		g2d.drawImage(
    				imgObst.get(0),
    				o.getX(), o.getY(),
    				o.getW(), o.getH(),
    				null);
    		if(this.showHitbox) {
	    		g2d.drawRect(o.getX(), o.getY(), o.getW(), o.getH());
	    		g2d.setColor(Color.BLACK);
    		}
    		
    	}
    	
    	ArrayList<Opponent> opL = P.getOpL();
    	for(Opponent o : opL) {
    		g2d.drawImage(
    				imgV.get(1),
    				o.getX(), o.getY(),
    				o.getW(), o.getH(),
    				null);
    		if(this.showHitbox) {
    			g2d.setColor(Color.GRAY);
	    		g2d.drawRect(o.getX(), o.getY(), o.getW(), o.getH());
	    		g2d.setColor(Color.BLACK);
    		}
    	}
    }
    
    /** Draws the current speed and the distance traveled
     * @param g2d the graphics2D on which we draw
     **/
    private void drawScore(Graphics2D g2d) {
    	g2d.setColor(Color.WHITE);
    	String speed = "speed : " + P.getSpeed() * 10 + " km/h";
    	String distToCheck = "CheckPoint : " + P.toCp() + " m";
    	String dist = "Score : " + P.getScore();

    	g2d.drawString(speed, 10, 25);
    	g2d.drawString(dist, 10, 45);
    	g2d.drawString(distToCheck, 10, 65);
    }
    
    /** Draws the time since beginning of the game and time left until next checkpoint
     * @param g2d the graphics2D on which we draw
     **/
    private void drawTimer(Graphics2D g2d) {
    	g2d.setColor(Color.WHITE);
    	
    	long minPassed = secPassed/60;
    	long affSecPassed = secPassed - minPassed*60;
    	String totTime = "Timer : " + minPassed + "m " + affSecPassed + "s";
    	
    	long minTl = V.getTTL()/60;
    	long secTl = V.getTTL() - minTl*60;
    	String TimeLeft = "Game Over : " + minTl + "m " + secTl + "s";
    	
    	g2d.drawString(totTime, WIDTH-130, 25);
    	g2d.drawString(TimeLeft, WIDTH-200, 45);
    	
    }
  
    
    /** Draws the vehicle at the right coordinates
     * @param g2d the graphics2D on which we draw
     **/
    private void drawVehicule(Graphics2D g2d){
    	Point vcoord = V.getCoord();
    	Point center = new Point(vcoord.x+ V.getHitWidth()/2, vcoord.y+V.getHitHeight()/2);
    	
    	Rectangle rect1 = new Rectangle(vcoord.x, vcoord.y, V.getHitWidth(), V.getHitHeight());
    	
    	switch(V.getMoveStatus()) {
	    	case "LEFT":
				g2d.rotate(Math.toRadians(-20), rect1.x+rect1.width/4, rect1.y);
				break;
			case "RIGHT":
				g2d.rotate(Math.toRadians(20),rect1.x+rect1.width-(rect1.width/4), rect1.y);
				break;
			case "UP":
				break;
			case "DOWN":
				break;
			case "NEUTRAL":
				break;
    	}
    	    
        if(V.getFlyStatus()) { drawFlyEffect(g2d, vcoord);}
        
    	g2d.drawImage(
    			imgV.get(0),
    			vcoord.x, vcoord.y,
    			V.getHitWidth(), V.getHitHeight(),
    			null);
    	
    	if(this.showHitbox) {
    		g2d.draw(rect1);
       	 
        	g2d.drawLine(center.x+5, center.y+5, center.x-5, center.y-5);
        	g2d.drawLine(center.x-5, center.y+5, center.x+5, center.y-5);
    	}
    	
    }
    
    /** Draws the background image
     * @param g2d the graphics2D on which we draw
     **/
    private void drawBg(Graphics2D g2d){ 	
    	for(int i = 0; i < imgBg.size(); i++){
    		BufferedImage I = imgBg.get(i);
    		if (i == 0) {
    			g2d.drawImage(
        				I, 
        				0,0,
        				WIDTH, horHeight, 
        				null);
    		} else {
    			int imageW = I.getWidth();
    			// Tile the image to fill our area.
    	        for (int x = -10*WIDTH; x < 10*WIDTH; x += imageW) {
	    	        g2d.drawImage(
	    	        		I, 
	    	        		x + mvBg[i-1], 0,
	    	        		I.getWidth(), horHeight,
	    	        		this);
    	        }
    		}
    	}
    }
   
    
    /** Draws the ground image
     * @param g2d the graphics2D on which we draw
     **/
    private void drawGround(Graphics2D g2d) {
    		
    	BufferedImage I = imgG.get(0);
    	
    	g2d.drawImage(
				I, 
				0, horHeight - 2,
				WIDTH, HEIGHT - V.getCoord().y/6,
				null);
		
		
    	
    }
    
    /** Draws an image representing effects where the vehicle is flying
     * @param g2d the graphics2D on which we draw
     * @param coord the Point where we draw the image
     **/
    private void drawFlyEffect(Graphics2D g2d, Point coord) {
    	g2d.drawImage(
    			imgEff.get(green_light), 
    			coord.x, coord.y+V.getHitHeight()/2, 
    			V.getHitWidth(), V.getHitHeight(),
    			null);
    }
   
    /** Draws every element of this interface on a Graphics
     * @param g a Graphics on which we draw
     **/
    public void paint(Graphics g) {
    	
    	paintComponent(g);
    	
    	Graphics2D g2d = (Graphics2D) g;
    	Graphics2D temp;
    	temp = (Graphics2D) g2d.create();
    	
    	g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
    	
    	drawGround(g2d);
    	drawPiste(g2d);
    	drawBg(g2d);
    	drawObstacles(temp);
    	drawScore(g2d);
    	drawTimer(g2d);
    	drawVehicule(g2d);
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
    	/*
    	if(grass_move < 10) { grass_move ++; }
    	else { grass_move = 1; }
    	*/
    	if(green_light < 3) { green_light ++; }
    	else { green_light = 0; }
    }

    /**Loads and adds all the vehicle images to the imgV ArrayList**/
    public void showHitbox() {
    	if(this.showHitbox) this.showHitbox = false;
    	else this.showHitbox = true;
    }
    
    /**
     * Sets the movement coefficient for our background images.
     * @param dir the direction in which the vehicle is going.
     */
    public void set_bg_parallax(String dir) {
    	switch(dir) {
    		case "LEFT":
    			mvBg [0] += 1;
    			mvBg [1] += 2;
    			mvBg [2] += 3;
    			mvBg [3] += 4;
    			break;
    		case "RIGHT":
    			mvBg [0] -= 1;
    			mvBg [1] -= 2;
    			mvBg [2] -= 3;
    			mvBg [3] -= 4;
    			break;
    	}
    }
    
    
    /**
     * Restores everything to default.s
     */
    public void restart() {
    	this.mvBg = new int[]{0, 0, 0, 0};
    	
    	this.showHitbox = false;
    	this.green_light = 0;
    	
		this.starttime = System.currentTimeMillis();
    	this.P.createTrack();
    }
}


