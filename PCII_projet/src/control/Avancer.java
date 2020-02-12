package control;

import model.Piste;
import model.Vehicule;
import view.Affichage;

public class Avancer implements Runnable {
	Affichage aff;
	Piste t;
	Vehicule v;
	public static final int TPSWAIT = 100;
	
	public Avancer (Affichage a, Piste track, Vehicule ve) {
		this.aff = a;
		this.t = track;
		this.v = ve;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!this.v.getFlyStatus()) {
			try {
				Thread.sleep(TPSWAIT) ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.t.moveTrack();
			aff.change();
		}
	}
	
}