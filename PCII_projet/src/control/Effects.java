package control;

import model.Vehicule;
import view.Affichage;

/** 
* This class is the Effects class, it is responsible creating a thread to animate effects on screen.
*/
public class Effects extends Thread{
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                     - OBJECTS -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * - V : the vehicle used by the player.
	 * - A : the graphic space .
	 *
	 */
	private Affichage A;
	private Vehicule V;
	
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                     - OTHER-
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * - TPSWAIT : The sleep time for the thread.
	 *  
	 */
	
	public static final int TPSWAIT = 80;
	
	/*-------------------------------------------------------------------------------------------------------*/
    
    /**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                    - CONSTRUCTOR -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
    
    /**
     * Constructor for the Effects Class of the MVNI Project.
     * @param : Affichage the view
     * @param : Vehicule the players vehicle
     */
	public Effects(Affichage a, Vehicule ve) {
		this.A = a;
		this.V = ve;
	}
	
	/*-------------------------------------------------------------------------------------------------------*/
	
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                    - FUNCTIONS -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	@Override
	public void run() {
		System.out.println("Effects Thread Started");
		/**If the vehicle is flying : */
		if(V.getFlyStatus()) {
			/**While it's on : */
			while(V.getFlyStatus()) {
				/** Change the images for the green bolt under the space ship. */
				A.incrView();
				try {
					Thread.sleep(TPSWAIT) ;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
