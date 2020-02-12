package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JPanel;

import model.Piste;
import model.Vehicule;

public class Affichage extends JPanel{

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
    	this.P.createTrack();
    }
    
    private void dessinePiste(Graphics g) {
    	g.drawLine(0, this.P.getHorizon(), this.WIDTH, this.P.getHorizon());
    	//Montains
    	ArrayList<Point> ptList = this.P.getBG();
    	for(int i=0; i<ptList.size()-2; i++) {
    		Point p1 = ptList.get(i);
    		Point p2 = ptList.get(i+1);
    		g.drawLine(p1.x, p1.y, p2.x, p2.y);
    	}
    	//Track
    	ptList = this.P.getTrack();
    	for(int i=0; i<ptList.size()-2; i++) {
    		Point p1 = ptList.get(i);
    		Point p2 = ptList.get(i+1);
    		g.drawLine(p1.x, p1.y, p2.x, p2.y);
    	}
    }
    
    private void dessineVehicule() {
    	
    }
    
    public void paint(Graphics g) {
    	paintComponent(g);
    	setBackground(Color.WHITE);
    	this.dessinePiste(g);
    }
    
}
