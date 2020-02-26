import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JFrame;
import control.ControlClavier;
import model.Piste;
import model.Vehicule;
import view.Affichage;

public class Main {

	public static void main(String[] args) throws IOException {
		//On cree la fenetre
		JFrame mainFrame = new JFrame("Course moto");

		mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		//Elements utiles
		//Modele
		Vehicule V = new Vehicule();
		Piste P = new Piste();
		
		//Vue
		Affichage A = new Affichage(V, P);
		
		//Controle
		ControlClavier controlClavier = new ControlClavier(A, V, P);
		
		mainFrame.addKeyListener(controlClavier);

		
		mainFrame.add(A);
		
		/**Adding labels and buttons in correct order.*/ 
		
		mainFrame.add(A.startlabel);

		mainFrame.add(A.deathlabel);

		
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
