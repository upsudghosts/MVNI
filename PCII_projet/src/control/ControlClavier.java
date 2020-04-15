package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Piste;
import model.Vehicule;
import view.Affichage;

public class ControlClavier implements KeyListener{
	
	public Affichage A;
	public Vehicule V;
	public Piste P;
	
	private Voler Fly;
	private Avancer Advance;
	private Effects Eff; 
	
	public ControlClavier (Affichage a, Vehicule v, Piste p) {
		this.A = a;
		this.V = v;
		this.P = p;
	}
	
	/**
	* void startGame function that creates the necessary threads each time a new game starts.
	*/
	public void startGame(KeyEvent e) {
		V.startRace();
		//P.setCPTimer((KeyListener) this);
		
		/**Initializing threads.*/
		Fly = new Voler(A, P, V);
		Advance = new Avancer(A, P, V);
		Eff = new Effects(A, P, V);
			
		/**Starting said threads.*/
		(Fly).start();
		(Advance).start();
		(Eff).start();

	}
		
	/**
	* void stoptGame function that interrupts any running threads each time a game ends.
	*/
	public void stopGame() {
		V.stopRace();
		(Fly).interrupt();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_DOWN :
				Fly.update_moveStatus(3);
				break;
				
			case KeyEvent.VK_UP :
				Fly.update_moveStatus(2);
				break;
				
			case KeyEvent.VK_LEFT :
				Fly.update_moveStatus(0);
				break;
				
			case KeyEvent.VK_RIGHT :
				Fly.update_moveStatus(1);
				break;
				
			case KeyEvent.VK_SPACE:
				if(!V.getFlyStatus() && V.getAlive()) {
					/**Updating button and label conditions*/
					A.startlabel.setText("Game Started");
					/**Starting need threads*/
					startGame(e);
				}else if(!V.getAlive()){
					A.deathlabel.setVisible(false);
					A.startlabel.setVisible(true);
						
					/**Setting to default for the project to restart. /!\ Do not change the order /!\*/
					V.restart();
					
					/**Repainting our work canvas.*/
					A.change();
				}
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Fly.update_moveStatus(4);
		//System.out.println(V.getMoveStatus());
		
	}

}
