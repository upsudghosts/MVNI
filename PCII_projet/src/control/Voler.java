/**
 * 
 */
package control;

import model.Piste;
import model.Vehicule;
import view.Affichage;

/**
 * @author piotrborissov
 *
 */
public class Voler extends Thread {
	
	/**Number of pixels our bike will move in any of the four directions.*/
	private int leftCoef, rightCoef, upCoef, downCoef;
	private Vehicule V;
	private Affichage A;

	/**
	 * 
	 */
	public Voler(Affichage a, Piste track, Vehicule ve) {
		this.leftCoef = 15; 
		this.rightCoef = 15;
		this.upCoef = 15;
		this.downCoef = 15;
		
		this.V = ve;
		this.A = a;
	}

	@Override
	public void run() {
		System.out.println("Fly Thread Started");
		/**If the vehicle is flying : */
		if(V.getFlyStatus()) {
			/**While it's on : */
			while(V.getFlyStatus()) {
				switch (V.getMoveStatus()) {
					case "LEFT":
						this.V.move(leftCoef);
						break;
					case "RIGHT":
						this.V.move(rightCoef);
						break;
					case "UP":
						this.V.move(upCoef);
						break;
					case "DOWN":
						this.V.move(downCoef);
						break;
					case "NEUTRAL":
						this.V.move(0);
						break;
				}
				/**Refreshing view and putting thread to sleep for 50 ms to see updates on our screen.*/
				this.A.change();
				try { Thread.sleep(50); }
				catch (Exception exc) { exc.printStackTrace(); }
			}
		}
	}
}
