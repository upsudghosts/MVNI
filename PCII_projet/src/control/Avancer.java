package control;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import model.Piste;
import model.Vehicule;
import view.Affichage;

public class Avancer extends Thread{
	Piste P;
	Vehicule V;
	Affichage A;
	public static final int TPSWAIT = 100;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Avancer (Affichage a, Piste track, Vehicule ve) {
		this.P = track;
		this.V = ve;
		this.A = a;
	}
	
	@Override
	public void run() {
		System.out.println("Move Thread Started");
		while(this.V.getFlyStatus()) {
			try {
				Thread.sleep(TPSWAIT) ;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Point coordV = V.getCoord();
			
			switch(this.V.getMoveStatus()) {
				case "DOWN":
					if (coordV.y > screenSize.height/2 && coordV.y < screenSize.height - 3*this.V.getHitHeight()) {
						this.P.speedUp();
						//System.out.println("Speed Up");
					}
					break;
				case "UP":
					if (coordV.y < screenSize.height/2) {
						this.P.speedDown();
						//System.out.println("Speed Down");
					}
					break;
			}
			this.P.moveTrack();
			this.A.change();
		}
	}
	
}