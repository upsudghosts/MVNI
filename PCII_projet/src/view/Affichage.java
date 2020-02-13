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

import model.Piste;
import model.Vehicule;

public class Affichage extends JPanel{
	/**Needed labels and buttons for the project.*/
	public JLabel startlabel, scorelabel, deathlabel; 

	private static final long serialVersionUID = 1L;

	//Taille par defaut de l'interface
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public final int WIDTH = screenSize.width;
    public final int HEIGHT = screenSize.height;
    
    //Emplacement de l'horizon
    private final int horHeight = (int) (this.HEIGHT*0.3);
    
    //Modele
    private Vehicule V;
    private Piste P;
    
    //Img Access
    private String parallax_mountains;
	
    
    public Affichage(Vehicule v, Piste p) {
    	this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    	this.V = v;
    	this.P = p;
    	this.P.setHorizon(this.horHeight);
    	this.P.setMaxX(this.WIDTH+100);
    	this.P.setMaxY(this.HEIGHT);
    	this.P.genereArrierePlan();

    	this.P.setCurrY(this.HEIGHT);
    	this.P.createTrack2();
    	
    	this.parallax_mountains = "./Img/parallax_mountain_pack/layers/";

		/**Initializing buttons.*/
		this.startlabel = new JLabel("Press SPACE to Start Game");
		this.scorelabel = new JLabel("Score : " + this.V.getPos());
		this.deathlabel = new JLabel();
		
		/**These labels and the button are meant to be seen after death, or in-game. Not before.*/
		this.scorelabel.setVisible(false);
		this.deathlabel.setVisible(false);

    }
    
    public int getWidth() {
    	return this.WIDTH;
    }
    
    public int getHeight() {
    	return this.HEIGHT;
    }
    
    private void drawPiste(Graphics g) {
    	g.drawLine(0, this.P.getHorizon(), this.WIDTH, this.P.getHorizon());
    	//Montains
    	
    	ArrayList<Point> ptList = this.P.getBG();
    	
    	/*
    	for(int i=0; i<ptList.size()-1; i++) {
    		Point p1 = ptList.get(i);
    		Point p2 = ptList.get(i+1);
    		g.drawLine(p1.x, p1.y, p2.x, p2.y);
    	}*/
    	
    	//Track
    	ptList = this.P.getTrack();
    	for(int i=0; i<ptList.size()-1; i++) {
    		Point p1 = ptList.get(i);
    		Point p2 = ptList.get(i+1);
    		g.drawLine(p1.x, p1.y, p2.x, p2.y);
    	}
    }
    
    private void drawVehicule(Graphics g) {
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
    	g.drawLine(center.x+5, center.y+5, center.x-5, center.y-5);
    	g.drawLine(center.x-5, center.y+5, center.x+5, center.y-5);
    }
    
    private void drawBg(Graphics g) throws IOException {
    	ArrayList<BufferedImage> Images = new ArrayList<BufferedImage>();
    	
    	Images.add(ImageIO.read(new File(this.parallax_mountains+"parallax-mountain-bg.png")));
    	Images.add(ImageIO.read(new File(this.parallax_mountains+"parallax-mountain-montain-far.png")));
    	Images.add(ImageIO.read(new File(this.parallax_mountains+"parallax-mountain-mountains.png")));
    	Images.add(ImageIO.read(new File(this.parallax_mountains+"parallax-mountain-trees.png")));
    	Images.add(ImageIO.read(new File(this.parallax_mountains+"parallax-mountain-foreground-trees.png")));
    	
    	
    	for(BufferedImage I : Images) {
    		Image NewI = I.getScaledInstance(this.WIDTH, this.horHeight-2, BufferedImage.SCALE_SMOOTH);
    		g.drawImage(NewI, 0,0, null);
    	}
    }
    
    public void paint(Graphics g) {
    	paintComponent(g);
    	this.drawPiste(g);
    	try {
			this.drawBg(g);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	this.drawVehicule(g);
    	
    }
    
    public void change() {
    	repaint();
    }
    
}
