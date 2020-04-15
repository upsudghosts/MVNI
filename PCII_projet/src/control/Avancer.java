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
		
		A.setMinPassed(0);
		while(this.V.getFlyStatus()) {
			try {
				Thread.sleep(TPSWAIT) ;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Point coordV = V.getCoord();
			
			if (coordV.y > screenSize.height/2 && coordV.y < screenSize.height - 3*this.V.getHitHeight()) {
				this.P.speedUp();
				//System.out.println("Speed Up");
			}
			else if (coordV.y < screenSize.height/2) {
				this.P.speedDown();
				//System.out.println("Speed Down");
			}
			
	        long timepassed = System.currentTimeMillis()-A.getStarttime();
	        
	        A.setSecPassed(timepassed/1000);
	        V.timeDecrease(timepassed/1000);
	        
	        if(A.getSec()==60)
	        {
	            A.setSecPassed(0);
	            A.setStarttime();
	        }
	        if((A.getSec()%60)==0)
	        A.setMinPassed(1);
	        
			this.P.moveTrack();
			this.A.change();
		}
	}
	
}