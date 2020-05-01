package control;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JOptionPane;

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
	
	long affTimePassed, veTimePassed, cpTimePassed;
	
	public Avancer (Affichage a, Piste track, Vehicule ve) {
		this.P = track;
		this.V = ve;
		this.A = a;
		
		this.cpTimePassed = System.currentTimeMillis(); 
		
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
	        
			Point coordV = V.getCenter();
			
			
			if (coordV.y > screenSize.height/2 && coordV.y < screenSize.height - V.getHitHeight()/3 &&
				coordV.x > P.getTrackL().get(1).x && coordV.x < P.getTrackR().get(1).x) {
				P.speedUp();
				//System.out.println("Speed Up");
			}
			else {
				P.speedDown();
				if (P.getSpeed() == 10) V.move("DOWN", 2);;
				//System.out.println("Speed Down");
			}
			
			
			if(P.getDist()%5000 >= 0 && P.getDist()%5000 <= 40) {
				cpTimePassed = System.currentTimeMillis();
				System.out.println("Passed");
				V.addTime(15);
			}
			
	        affTimePassed = System.currentTimeMillis()-A.getStarttime();
	        veTimePassed = System.currentTimeMillis()-cpTimePassed;
	        
	        A.setSecPassed(affTimePassed/1000);	
	        V.timeDecrease(veTimePassed/1000);

	        
	        if(A.getSec()== 60)
	        {
	            A.setSecPassed(0);
	            A.setStarttime();
	        }
	        
	        if((A.getSec()%60)==0) A.setMinPassed(1);
	        
			P.moveTrack();
			
			P.checkObst(V);
		
			A.change();
		}
		
		JOptionPane.showMessageDialog(null, 
				"GAME OVER \n" +
				"Space to retry \n" +
				"Escape to quit"
			 );
	}
}