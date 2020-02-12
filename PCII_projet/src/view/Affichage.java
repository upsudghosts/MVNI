package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JPanel;

import model.Piste;
import model.Vehicule;

public class Affichage extends JPanel{

	private static final long serialVersionUID = 1L;

	//Taille par defaut de l'interface
	Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
	public final int LARG = tailleEcran.width;
    public final int HAUT = tailleEcran.height;
    
    //Modele
    private Vehicule vehicule;
    private Piste piste;
	
    
    public Affichage(Vehicule v, Piste p) {
    	this.setPreferredSize(new Dimension(LARG, HAUT));
    	this.vehicule = v;
    	this.piste = p;
    }
    
    private void dessinePiste(Graphics g) {
    	g.drawLine(0, this.piste.getHorizon(), this.LARG, this.piste.getHorizon());
    }
    
    private void dessineVehicule() {
    	
    }
    
}
