package control;

import model.Piste;
import model.Vehicule;
import view.Affichage;

public class Effects extends Thread{
	
	private int t_eff;
	private Vehicule V;
	private Affichage A;
	private Piste P;
	
	public Effects(Affichage a, Piste track, Vehicule ve) {
		this. A = a;
		this.V = ve;
		this.P = track;
		
		this.t_eff = 100;
	}
	
	@Override
	public void run() {
		System.out.println("Fly Thread Started");
		/**If the vehicle is flying : */
		if(V.getFlyStatus()) {
			/**While it's on : */
			while(V.getFlyStatus()) {
				this.A.incrView();
				
				/**Refreshing view and putting thread to sleep for 50 ms to see updates on our screen.*/
				this.A.change();
				
				try { Thread.sleep(t_eff); }
				catch (Exception exc) { exc.printStackTrace(); }
			}
		}
	}

}
