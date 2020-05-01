 import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException; 

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import control.ControlClavier;
import model.Piste;
import model.Vehicule;
import view.Affichage;

public class Main { 

	private static Vehicule V;
	private static Piste P;
	private static Affichage A;
	private static ControlClavier controlClavier;
	
	public static void main(String[] args) throws IOException {
		//On cree la fenetre
		JFrame mainFrame = new JFrame("Course moto");

		mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		//Elements utiles
		//Modele
		V = new Vehicule();
		P = new Piste();
		
		//Vue
		A = new Affichage(V, P);
		
		//Controle
		controlClavier = new ControlClavier(A, V, P);
		
		mainFrame.addKeyListener(controlClavier);
		
		mainFrame.add(A);
		
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JOptionPane.showMessageDialog(null, 
										"Space : Start game \n" +
										"H : Toggle Hitboxes"
									 );
		
	}
	

}
