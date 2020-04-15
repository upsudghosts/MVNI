package control;

import model.Piste;
import model.Vehicule;
import view.Affichage;

public class Effects extends Thread{
	public static final int TPSWAIT = 80;
	
	private Affichage A;
	private Vehicule V;
	private Piste P;
	
	
	public Effects(Affichage a, Piste track, Vehicule ve) {
		this.A = a;
		this.V = ve;
		this.P = track;
		
	}
	
	@Override
	public void run() {
		System.out.println("Effects Thread Started");
		/**If the vehicle is flying : */
		if(V.getFlyStatus()) {
			/**While it's on : */
			while(V.getFlyStatus()) {
				this.A.incrView();
				try {
					Thread.sleep(TPSWAIT) ;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
