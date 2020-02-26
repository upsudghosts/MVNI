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
	
	public Voler Fly;
	public Avancer Advance;
	public Effects Eff;
	
	public ControlClavier (Affichage a, Vehicule v, Piste p) {
		this.A = a;
		this.V = v;
		this.P = p;
	}
	
	/**
	* void startGame function that creates the necessary threads each time a new game starts.
	*/
	public void startGame() {
		this.V.startRace();
		
		/**Initializing threads.*/
		this.Fly = new Voler(this.A, this.P, this.V);
		this.Advance = new Avancer(this.A, this.P, this.V);
		this.Eff = new Effects(this.A, this.P, this.V);
			
		/**Starting said threads.*/
		(this.Fly).start();
		(this.Advance).start();
		(this.Eff).start();
	}
		
	/**
	* void stoptGame function that interrupts any running threads each time a game ends.
	*/
	public void stopGame() {
		this.V.stopRace();
		(this.Fly).interrupt();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_DOWN :
				this.V.update_moveStatus(3);
				break;
				
			case KeyEvent.VK_UP :
				this.V.update_moveStatus(2);
				break;
				
			case KeyEvent.VK_LEFT :
				this.V.update_moveStatus(0);
				break;
				
			case KeyEvent.VK_RIGHT :
				this.V.update_moveStatus(1);
				break;
				
			case KeyEvent.VK_SPACE:
				if(!this.V.getFlyStatus() && this.V.getAlive()) {
					/**Updating button and label conditions*/
					this.A.startlabel.setText("Game Started");
					this.A.scorelabel.setVisible(true);
					/**Starting need threads*/
					this.startGame();
				}else if(!this.V.getAlive()){
					this.A.deathlabel.setVisible(false);
					this.A.startlabel.setVisible(true);
					this.A.scorelabel.setVisible(true);
						
					/**Setting to default for the project to restart. /!\ Do not change the order /!\*/
					this.V.restart();
					
					/**Repainting our work canvas.*/
					this.A.change();
				}
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.V.update_moveStatus(4);
		System.out.println(this.V.getMoveStatus());
		
	}

}
