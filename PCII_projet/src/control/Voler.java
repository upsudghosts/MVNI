/**
 * 
 */
package control;

import model.Piste;
import model.Vehicule;
import view.Affichage;

/** 
* This class is the Flying class, it is responsible creating a thread to control the behavior
* of the player's ship in flight.
*/
public class Voler extends Thread {
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
	private Vehicule V;
	private Piste P;
	private Affichage A;
	
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                     - OTHER-
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * - TPSWAIT : The sleep time for the thread.
	 * - [...]Coef : Number of pixels our ship will move in any of the four directions.
	 * - moveStatus : An enumeration type to know what the vehicle is doing.
	 */
	public static final int TPSWAIT = 50;
	private int leftCoef, rightCoef, upCoef, downCoef;
	
	protected enum moveStatus{ 
		LEFT, RIGHT, UP, DOWN, NEUTRAL;
		/** A list that regroup all the values right up. */
		private static moveStatus[] list = moveStatus.values();
		
		/** A function to return the value through an integer index. 
		 * @param int the index.
		 * @return list[i] the wanted value.
		 * */
	    public static moveStatus getVal(int i) {
	        return list[i];
	    }
	}	private moveStatus mvStat;

	/*-------------------------------------------------------------------------------------------------------*/
    
    /**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                    - CONSTRUCTOR -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
    
    /**
     * Constructor for the Effects Class of the MVNI Project.
     * @param : Affichage the view
     * @param : Piste the track
     * @param : Vehicule the players vehicle
     */
	public Voler(Affichage a, Piste track, Vehicule ve) {
		/** 20 seemed like a good choice. */
		this.leftCoef = 20; 
		this.rightCoef = 20;
		this.upCoef = 20;
		this.downCoef = 20;
		
		this.V = ve;
		this.A = a;
		this.P = track; 
		
		/** The vehicle is in neutral state when not turning or going up/down. */
		this.mvStat = moveStatus.NEUTRAL;
	}

	/*-------------------------------------------------------------------------------------------------------*/
	
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                    - FUNCTIONS -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	@Override
	public void run() {
		System.out.println("Fly Thread Started");
		/** If the vehicle is flying : */
		if(V.getFlyStatus()) {
			/** While it's on : */
			while(V.getFlyStatus()) {
				switch (mvStat) {
					/** Moving our track, vehicle and background according to the direction. */
					case LEFT:
						V.move("LEFT", leftCoef);
						P.trackEffect("LEFT", leftCoef);
						A.set_bg_parallax("LEFT");
						break;
					case RIGHT:
						V.move("RIGHT", rightCoef);
						P.trackEffect("RIGHT", rightCoef);
						A.set_bg_parallax("RIGHT");
						break;
					case UP:
						V.move("UP", upCoef);
						P.trackEffect("UP", upCoef);
						break;
					case DOWN:
						V.move("DOWN", downCoef);
						P.trackEffect("DOWN", downCoef);
						break;
					case NEUTRAL:
						V.move("NEUTRAL", 0);
						break;
				}
				/**Refreshing view and putting thread to sleep for 50 ms to see updates on our screen.*/
				A.change();
				try { Thread.sleep(TPSWAIT); }
				catch (Exception exc) { exc.printStackTrace(); }
			}
		}
	}
	
	/**
	 * Function to String get the moveStatus 
	 * @return our moveStatus in a String form
	 */
	public String getMoveStatus() {
		return ""+mvStat+"";
	}
	
	/**
	 * Function to update our move status given an index.
	 * @param index an integer.
	 */
	public void update_moveStatus(int index) {
		if(V.getFlyStatus() && V.getAlive()) {
			mvStat = moveStatus.getVal(index);
		}
	}
	
}
