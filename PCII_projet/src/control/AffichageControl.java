package control;

import view.Affichage;

public class AffichageControl extends Thread {
	public static final int TPSWAIT = 42;
	
	private boolean modif;
	private Affichage A;
	
	public AffichageControl(Affichage a) {
		this.modif = false;
		this.A = a;
	}
	
	@Override
	public void run() {
		System.out.println("Affichage Control Thread Started");
		
		if (modif) {
			A.change();
			
			this.modif = false;
			try {
				Thread.sleep(TPSWAIT) ;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void modifEff() {
		this.modif = true;
		this.A.incrView();
	}
	
	public void viewRepaint() {
		this.modif = true;
	}

}
