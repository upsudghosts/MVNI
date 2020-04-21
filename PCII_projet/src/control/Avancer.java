package control;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import model.CheckPoint;
import model.Piste;
import model.Vehicule;
import view.Affichage;

public class Avancer extends Thread{
	Piste P;
	Vehicule V;
	Affichage A;
	public static final int TPSWAIT = 100;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	long affTimePassed, cpTimePassed, veTimePassed;
	
	public Avancer (Affichage a, Piste track, Vehicule ve) {
		this.P = track;
		this.V = ve;
		this.A = a;
		
		this.cpTimePassed = 0; 
	}
	
	@Override
	public void run() {
		System.out.println("Move Thread Started");
		
		A.setMinPassed(0);
		
		while(V.getFlyStatus()) {
			try {
				Thread.sleep(TPSWAIT) ;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Point coordV = V.getCoord();
			
			CheckPoint cp = checkPassed();
			if(cp != null) {
				setCpTimePassed();
				V.addTime(cp.getSecToAdd());
			}
			
			if (coordV.y > screenSize.height/2 && coordV.y < screenSize.height - 3*V.getHitHeight()) {
				P.speedUp();
				//System.out.println("Speed Up");
			}
			else if (coordV.y < screenSize.height/2) {
				P.speedDown();
				//System.out.println("Speed Down");
			}
			
	        affTimePassed = System.currentTimeMillis()-A.getStarttime();
	        veTimePassed = affTimePassed - cpTimePassed;
	        
	        A.setSecPassed(affTimePassed/1000);	     
	        V.timeDecrease(veTimePassed/1000);

	        
	        if(A.getSec()== 60)
	        {
	            A.setSecPassed(0);
	            A.setStarttime();
	        }
	        
	        if((A.getSec()%60)==0) A.setMinPassed(1);
	        
			P.moveTrack();
			
			if(P.hitObst(V)){
				V.stopRace();
			}
			
			A.change();
		}
	}
	
	private CheckPoint checkPassed() {
		if(!P.getCP().isEmpty()) {
			CheckPoint cp = P.getCP().get(0);
			if(cp.getDist() == P.getDist()) {
				return cp;
			}
		}
		return null;
	}
	
	private void setCpTimePassed() {
		cpTimePassed = affTimePassed;
	}
}