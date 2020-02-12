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
    	this.P.setMaxX(this.WIDTH);
    }
    
    private void drawPiste(Graphics g) {
    	g.drawLine(0, this.P.getHorizon(), this.WIDTH, this.P.getHorizon());
    	ArrayList<Point> ptList = this.P.getBG();
    	//for(int i=0; i<ptList.size())
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
    
}
