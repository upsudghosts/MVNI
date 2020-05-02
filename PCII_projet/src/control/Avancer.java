package control;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JOptionPane;

import model.Piste;
import model.Vehicule;
import view.Affichage;

/** 
* This class is the Moving class, it is responsible for creating a new thread that will move 
* our Track and the objects on it.
*/

public class Avancer extends Thread{
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                     - OBJECTS -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * - V : the vehicle used by the player.
	 * - P : the track which the player has to follow.
	 * - A : the graphic space .
	 *  
	 */
	
	Piste P;
	Vehicule V;
	Affichage A;
	
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                     - OTHER-
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * - TPSWAIT : The sleep time for the thread.
	 * - screenSize : To get user's screen dimensions.
	 * - affTimePassed : Total time Passed since the start of the thread four our View.
	 * - veTimePassed : Time Passed since a checkpoint four our Vehicle.
	 * - cpTimePassed : The time at which a checkpoint was passed.
	 * 					Used to calculate veTimePassed.
	 *  
	 */
	
	public static final int TPSWAIT = 100;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	long affTimePassed, veTimePassed, cpTimePassed;
	
	/*-------------------------------------------------------------------------------------------------------*/
    
    /**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                    - CONSTRUCTOR -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
    
    /**
     * Constructor for the "Moving" Class of the MVNI Project.
     * @param : Affichage the view
     * @param : Piste the track
     * @param : Vehicule the players vehicle
     */
	public Avancer (Affichage a, Piste track, Vehicule ve) {
		this.P = track;
		this.V = ve;
		this.A = a;
		
		//Initializing first time for checkpoint as our thread's starting time.
		this.cpTimePassed = System.currentTimeMillis(); 
		
	}
	
	/*-------------------------------------------------------------------------------------------------------*/
    
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                    - FUNCTIONS -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	@Override
	public void run() {
		System.out.println("Move Thread Started");
		
		/**Setting View minutes to 0.*/
		A.setMinPassed(0);
		
		/**While the vehicle is in flight (i.e not dead) :*/
		while(V.getFlyStatus()) {
			/**Pausing thread each time for synchronization.*/
			try {
				Thread.sleep(TPSWAIT) ;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        
			/**Getting the center of the player's vehicle.*/
			Point coordV = V.getCenter();
			
			/**If the vehicle is not too far above the track and not on the sides of the track :*/
			if (coordV.y > screenSize.height/2 && coordV.y < screenSize.height - V.getHitHeight()/3 &&
				coordV.x > P.getTrackL().get(1).x && coordV.x < P.getTrackR().get(1).x) {
				/**Increase track's velocity .*/
				P.speedUp();
			}
			else {
				/**Decrease track's velocity.*/
				P.speedDown();
				/**Plus if the speed is at minimum then making the vehicle slowly go down.*/
				if (P.getSpeed() == 10) V.move("DOWN", 2);;
			}
			
			/**Check if the checkpoint was passed. 
			 *Checking with boundaries not exact value or else will not work.*/
			if(P.getDist()%5000 >= 0 && P.getDist()%5000 <= 25) {
				/**Setting checpoint's passage time.*/
				cpTimePassed = System.currentTimeMillis();
				/**Adding time to our ship's timer.*/
				V.addTime(25);
			}
			
			/**Calculating total time.*/
	        affTimePassed = System.currentTimeMillis()-A.getStarttime();
	        
	        /**Calculating time since checkpoint was passed.*/
	        veTimePassed = System.currentTimeMillis()-cpTimePassed;
	        
	        /**Setting view's seconds (which will then be converted to minutes)*/
	        A.setSecPassed(affTimePassed/1000);	
	        /**Decreasing ship's timer.*/
	        V.timeDecrease(veTimePassed/1000);
	        
	        /**If a minute has passed then setting View's seconds to 0.
	         *Resetting Starting time as well.*/
	        if(A.getSec()== 60)
	        {
	            A.setSecPassed(0);
	            A.setStarttime();
	        }
	        /**Minutes counter in View going up each 60 seconds.*/
	        if((A.getSec()%60)==0) A.setMinPassed(1);
	        
	        /**Moving the track.*/
			P.moveTrack();
			/**Checking for collisions between vehicle and obstacles on track.*/
			P.checkObst(V);
			/**Repainting our view.*/
			A.change();
		}
		
		/**If the vehicle is not alive anymore showing information dialog to the player.*/
		JOptionPane.showMessageDialog(null, 
				"GAME OVER \n" +
				"Space to retry \n" +
				"Escape to quit"
			 );
	}
}