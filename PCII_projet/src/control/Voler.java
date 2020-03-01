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
	public static final int TPSWAIT = 50;
	
	/**Number of pixels our bike will move in any of the four directions.*/
	private int leftCoef, rightCoef, upCoef, downCoef;
	private Vehicule V;
	private Piste P;
	private Affichage A;
	
	protected enum moveStatus{ 
		LEFT, RIGHT, UP, DOWN, NEUTRAL;
		
		private static moveStatus[] list = moveStatus.values();

	    public static moveStatus getVal(int i) {
	        return list[i];
	    }
	};
	private moveStatus mvStat;

	/**
	 * 
	 */
	public Voler(Affichage a, Piste track, Vehicule ve) {
		this.leftCoef = 20; 
		this.rightCoef = 20;
		this.upCoef = 20;
		this.downCoef = 20;
		
		this.V = ve;
		this.A = a;
		this.P = track; 
		
		this.mvStat = moveStatus.NEUTRAL;
	}

	@Override
	public void run() {
		System.out.println("Fly Thread Started");
		/**If the vehicle is flying : */
		if(V.getFlyStatus()) {
			/**While it's on : */
			while(V.getFlyStatus()) {
				switch (this.mvStat) {
					case LEFT:
						this.V.move("LEFT", leftCoef);
						this.P.trackEffect("LEFT", leftCoef);
						break;
					case RIGHT:
						this.V.move("RIGHT", rightCoef);
						this.P.trackEffect("RIGHT", rightCoef);
						break;
					case UP:
						this.V.move("UP", upCoef);
						this.P.trackEffect("UP", upCoef);
						break;
					case DOWN:
						this.V.move("DOWN", downCoef);
						this.P.trackEffect("DOWN", downCoef);
						break;
					case NEUTRAL:
						this.V.move("NEUTRAL", 0);
						break;
				}
				/**Refreshing view and putting thread to sleep for 50 ms to see updates on our screen.*/
				this.A.change();
				try { Thread.sleep(TPSWAIT); }
				catch (Exception exc) { exc.printStackTrace(); }
			}
		}
	}
	
	public String getMoveStatus() {
		return ""+this.mvStat+"";
	}
	
	public void update_moveStatus(int index) {
		if(this.V.getFlyStatus() && this.V.getAlive()) {
			this.mvStat = moveStatus.getVal(index);
		}
	}
	
}
