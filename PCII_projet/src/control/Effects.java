package control;

import model.Piste;
import model.Vehicule;
import view.Affichage;

public class Effects extends Thread{
	public static final int TPSWAIT = 80;
	
	private Vehicule V;
	private Piste P;
	
	private AffichageControl AC;
	
	public Effects(AffichageControl ac, Piste track, Vehicule ve) {
		this. AC = ac;
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
				this.AC.modifEff();
				try {
					Thread.sleep(TPSWAIT) ;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
