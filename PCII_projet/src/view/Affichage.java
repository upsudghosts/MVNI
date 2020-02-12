package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Piste;
import model.Vehicule;

public class Affichage extends JPanel{
	/**Needed labels and buttons for the project.*/
	public JLabel startlabel, scorelabel, deathlabel; 
	public JButton startButton, restartButton; 

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
    	
    	/**Initializing buttons.*/
		this.startButton = new JButton("Click Here to Start Game");
		this.restartButton = new JButton("Click Here to Start a New Game");
		
		/**Initializing buttons.*/
		this.startlabel = new JLabel("Start Game ?");
		this.scorelabel = new JLabel("Score : " + this.V.getPos());
		this.deathlabel = new JLabel();
		
		/**These labels and the button are meant to be seen after death, or in-game. Not before.*/
		this.scorelabel.setVisible(false);
		this.deathlabel.setVisible(false);
		this.restartButton.setVisible(false);
    }
    
    private void drawPiste(Graphics g) {
    	g.drawLine(0, this.P.getHorizon(), this.WIDTH, this.P.getHorizon());
    	//Montains
    	ArrayList<Point> ptList = this.P.getBG();
    	for(int i=0; i<ptList.size()-1; i++) {
    		Point p1 = ptList.get(i);
    		Point p2 = ptList.get(i+1);
    		g.drawLine(p1.x, p1.y, p2.x, p2.y);
    	}
    	//Track
    	ptList = this.P.getTrack();
    	for(int i=0; i<ptList.size()-1; i++) {
    		Point p1 = ptList.get(i);
    		Point p2 = ptList.get(i+1);
    		g.drawLine(p1.x, p1.y, p2.x, p2.y);
    	}
    }
    
    private void drawVehicule(Graphics g) {
    	Point vcoord = V.getCoord();
    	Point center = new Point(vcoord.x+ V.getHitWidth()/2, vcoord.y+V.getHitHeight()/2);
    	
    	g.drawRect(vcoord.x, vcoord.y, V.getHitWidth(), V.getHitHeight());
    	g.drawLine(center.x+5, center.y+5, center.x-5, center.y-5);
    	g.drawLine(center.x-5, center.y+5, center.x+5, center.y-5);
    }
    
    public void paint(Graphics g) {
    	paintComponent(g);
    	setBackground(Color.WHITE);
    	this.drawPiste(g);
    	this.drawVehicule(g);
    }
    
    public void change() {
    	repaint();
    }
    
}
