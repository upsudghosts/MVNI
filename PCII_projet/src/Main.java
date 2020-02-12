import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import control.Avancer;
import control.ControlClavier;
import control.ControlSouris;
import model.Piste;
import model.Vehicule;
import view.Affichage;

public class Main {

	public static void main(String[] args) {
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
		ControlClavier controlClavier = new ControlClavier(A, V);
		A.addKeyListener(controlClavier);
		Thread thr = new Thread(new Avancer(A, P, V));
		(thr).start();
		
		//ControlClavier controlClavier = new ControlClavier(A, V);
		ControlSouris controlSouris  = new ControlSouris(A);
		
		mainFrame.addKeyListener(controlClavier);
		
		/**Adding our mouse Listener to enable user/program live interaction.*/
		mainFrame.addMouseListener(controlSouris);
		A.startButton.addMouseListener(controlSouris);
		A.restartButton.addMouseListener(controlSouris);
				
		//Adding view to our frame.
		mainFrame.add(A);
		
		/**Adding labels and buttons in correct order.*/ 
		
		mainFrame.add(A.startlabel);
		mainFrame.add(A.startButton);
        
		mainFrame.add(A.scorelabel);
		mainFrame.add(A.deathlabel);
        
		mainFrame.add(A.restartButton);
		
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
