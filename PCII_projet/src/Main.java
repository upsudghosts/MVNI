import java.awt.FlowLayout;
import java.io.IOException; 

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import control.ControlClavier;
import model.Piste;
import model.Vehicule;
import view.Affichage;

/**
 * ===================================================
 *                      contents
 * ===================================================
 * ---------------------ON SCREEN----------------------
 * - The player's spaceship -> lighter
 * - Ground obstacles shown as trees
 * - Ennemy's spaceships -> darker 
 * - A Parallax Background
 * - A Dark Road
 * - A ground
 * - Checkpoints as white and black lines
 * 
 * - RIGHT CORNER : TOTAL TIME & TIME UNTIL DEATH
 * - LEFT CORNER : CURRENT SPEED & SCORE & DISTANCE UNTIL NEXT CHECKPOINT
 *
 * ----------------------------------------------------
 *                      Features
 * ----------------------------------------------------
 * - the ability to fly left
 * - the ability to fly right
 * - the ability to fly up
 * - the ability to fly down
 * - the ability to restart a game
 * - the ability to show/hide all hitboxes
 *
 * ----------------------------------------------------
 *                      Problems
 * ----------------------------------------------------
 * - Perspective view not working perfectly on adversaries. 
 * - Once game is restarted, timer is often bugged.
 * - Parallax Background is not infinite
 * - Parallax Ground not implemented
 * ===================================================
*/

/** 
* This class is the Main class, it instantiates all the classes needed for the project.
*/
public class Main { 

	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                     - OBJECTS -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * - V : the vehicle used by the player.
	 * - P : the track which the player has to follow.
	 * - A : the graphic space .
	 * - controlClavier : the keyboard listener. 
	 */
	private static Vehicule V;
	private static Piste P;
	private static Affichage A;
	private static ControlClavier controlClavier;
	
	public static void main(String[] args) throws IOException {
		/**Creating JFrame.*/
		JFrame mainFrame = new JFrame("Course moto");

		mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
	
		/**Initializing Models.*/
		V = new Vehicule();
		P = new Piste();
		
		/**Initializing View.*/
		A = new Affichage(V, P);
		
		/**Initializing Keyboard Listener ...*/
		controlClavier = new ControlClavier(A, V, P);
		/**... and adding it to the frame.*/
		mainFrame.addKeyListener(controlClavier);
		
		/**Adding our graphic contents.*/
		mainFrame.add(A);
		
		/**Showing the JFrame.*/
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/**Showing starting instructions*/
		JOptionPane.showMessageDialog(null, 
										"Space : Start game \n" +
										"H : Toggle Hitboxes"
									 );
		
	}
	

}
