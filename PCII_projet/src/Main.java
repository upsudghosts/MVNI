import javax.swing.JFrame;

import control.ControlClavier;
import model.Piste;
import model.Vehicule;
import view.Affichage;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//On cree la fenetre
		JFrame mainFrame = new JFrame("Course moto");
		
		//Elements utiles
		//Modele
		Vehicule V = new Vehicule(20,20);
		Piste P = new Piste();
		
		//Vue
		Affichage affichage = new Affichage(V, P);
		
		//Controle
		ControlClavier controlClavier = new ControlClavier(affichage, V);
		affichage.addKeyListener(controlClavier);
				
		//On ajoute l'affichage a la fenetre
		mainFrame.add(affichage);
						
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
