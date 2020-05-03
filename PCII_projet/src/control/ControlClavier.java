package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Piste;
import model.Vehicule;
import view.Affichage;

/** 
* This class is the Keyboard Listener class, it is responsible for User/Program intercation.
*/
public class ControlClavier implements KeyListener{
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                     - OBJECTS -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * - V : the vehicle used by the player.
	 * - P : the track which the player has to follow.
	 * - A : the graphic space .
	 *  
	 */
	public Affichage A;
	public Vehicule V;
	public Piste P;
	
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                     - THREADS -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * - Fly : the thread responsible for vehicle flight options.
	 * - Advance : the thread for moving everything.
	 * - Eff : the thread for the effects to make the game a little bit more livelier.
	 *  
	 */
	private Voler Fly;
	private Avancer Advance;
	private Effects Eff; 
	
	/*-------------------------------------------------------------------------------------------------------*/
    
    /**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                    - CONSTRUCTOR -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
    
    /**
     * Constructor for the Keyboard Listener Class of the MVNI Project.
     * @param a the view
     * @param v the players vehicle
     * @param p the track
     */
	public ControlClavier (Affichage a, Vehicule v, Piste p) {
		this.A = a;
		this.V = v;
		this.P = p;
	}
	
	/*-------------------------------------------------------------------------------------------------------*/
	
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                    - FUNCTIONS -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	/**
	* void startGame function that creates the necessary threads each time a new game starts.
	*/
	public void startGame(KeyEvent e) {
		/**Setting Vehicle to "InFlight" status.*/
		V.startRace();
		
		/**Initializing threads.*/
		Fly = new Voler(A, P, V);
		Advance = new Avancer(A, P, V);
		Eff = new Effects(A, V);
			
		/**Starting said threads.*/
		(Fly).start();
		(Advance).start();
		(Eff).start();

	}
		
	/**
	* void stopGame function that interrupts any running threads each time a game ends.
	*/
	public void stopGame() {
		/**Setting Vehicle status to "Dead" alike */
		V.stopRace();
		/**Stopping threads.*/
		(Fly).interrupt();
		(Advance).interrupt();
		(Eff).interrupt();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_DOWN :
				/** Going down */
				Fly.update_moveStatus(3);
				break;
				
			case KeyEvent.VK_UP :
				/** Going up */
				Fly.update_moveStatus(2);
				break;
				
			case KeyEvent.VK_LEFT :
				/** Going left */
				Fly.update_moveStatus(0);
				break;
				
			case KeyEvent.VK_RIGHT :
				/** Going right */
				Fly.update_moveStatus(1);
				break;
				
			case KeyEvent.VK_SPACE:
				/** If the game is not yet started : */
				if(!V.getFlyStatus() && V.getAlive()) {
					/** Starting it. */
					startGame(e);
				
				} 
				/** If the player is Game Over : */
				else if(!V.getAlive()){	
					/** Reinitializing our classes. */
					V.restart();
					P.restart();
					A.restart();
					
					/** Repainting our work canvas. */
					A.change();
			
					/** Restarting game. */
					startGame(e);
				}
				break;
			case KeyEvent.VK_H:
				/** Hide/Show Hitboxes. */
				A.showHitbox();
				break;
			case KeyEvent.VK_ESCAPE:
				/** Stopping the processus. */
				System.exit(0);
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		/**Stopping any vechicle turning motion and returning to original straight position. */
		Fly.update_moveStatus(4);
	}

}
