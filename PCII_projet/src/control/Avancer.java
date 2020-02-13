package control;

import java.awt.Point;

import model.Piste;
import model.Vehicule;
import view.Affichage;

public class Avancer extends Thread{
	Affichage A;
	Piste P;
	Vehicule V;
	public static final int TPSWAIT = 100;
	
	public Avancer (Affichage view, Piste track, Vehicule ve) {
		this.A = view;
		this.P = track;
		this.V = ve;
	}
	
	@Override
	public void run() {
		System.out.println("Fly Thread Started");
		while(this.V.getFlyStatus()) {
			try {
				Thread.sleep(TPSWAIT) ;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Point coordV = V.getCoord();
			
			switch(this.V.getMoveStatus()) {
				case "DOWN":
					if (coordV.y > this.A.getHeight()/2 && coordV.y < this.A.getHeight()) {
						this.P.speedUp();
						System.out.println("Speed Up");
					}
					break;
				case "UP":
					if (coordV.y < this.A.getHeight()/2) {
						this.P.speedDown();
						System.out.println("Speed Down");
					}
					break;
			}
			this.P.moveTrack();
			this.A.change();
		}
	}
	
}