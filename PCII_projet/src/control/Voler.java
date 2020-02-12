/**
 * 
 */
package control;

import model.Vehicule;

/**
 * @author piotrborissov
 *
 */
public class Voler extends Thread {
	
	/**Number of pixels our bike will move in any of the four directions.*/
	private int leftCoef, rightCoef, upCoef, downCoef;
	private Vehicule V;

	/**
	 * 
	 */
	public Voler(Vehicule v) {
		this.leftCoef = 5;
		this.rightCoef = 5;
		this.upCoef = 5;
		this.downCoef = 5;
		
		this.V = v;
	}

	@Override
	public void run() {
		System.out.println("Fly Thread Started");
		/**If the project is on : */
		if(V.getFlyStatus()) {
			/**While it's on : */
			while(V.getFlyStatus()) {
				switch (V.getMoveStatus()) {
					case "LEFT":
						break;
					case "RIGHT":
						break;
					case "UP":
						break;
					case "DOWN":
						break;
					case "NEUTRAL":
						break;
				}
			}
		}
	}
}
