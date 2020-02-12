import javax.swing.JFrame;

import control.ControlClavier;
import model.Piste;
import model.Vehicule;
import view.Affichage;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//On cree la fenetre
		JFrame fenetre = new JFrame("Course moto");
		
		//Elements utiles
		//Modele
		Vehicule vehicule = new Vehicule();
		Piste piste = new Piste();
		
		//Vue
		Affichage affichage = new Affichage(vehicule, piste);
		
		//Controle
		ControlClavier controlClavier = new ControlClavier(affichage, vehicule);
		affichage.addKeyListener(controlClavier);
				
		//On ajoute l'affichage a la fenetre
		fenetre.add(affichage);
						
		fenetre.pack();
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
