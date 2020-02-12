package control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Vehicule;
import view.Affichage;

public class ControlSouris  implements MouseListener{
		
	//Linking Classes together so Control has all necessary components to operate.
	public Affichage A;
	public Vehicule V;
	public Voler Fly;
		
	/**
	* Class constructor :
	* @param A : Affichage the class to link and on which we operate.
	*/
	public ControlSouris(Affichage A) {
		this.A = A;
	}
		
	/**
	* void startGame function that creates the necessary threads each time a new game starts.
	*/
	public void startGame() {
		this.V.startRace();
		
		/**Initializing threads.*/
		this.Fly = new Voler(this.V);
			
		/**Starting said threads.*/
		(this.Fly).start();
	}
		
	/**
	* void stoptGame function that interrupts any running threads each time a game ends.
	*/
	public void stopGame() {
		this.V.stopRace();
		(this.Fly).interrupt();
	}

	/**
	* The only event we listen to. Used to start, restart or play the project.
	*/
	@Override
	public void mouseClicked(MouseEvent e) {
		/** If startButton is clicked */
		if (e.getSource()== this.A.startButton){	
			/**Updating button and label conditions*/
			this.A.startlabel.setText("Game Started");
			this.A.startButton.setVisible(false);
			this.A.scorelabel.setVisible(true);
			/**Starting need threads*/
			this.startGame();
	
		} else
		/** if restartButton is clicked*/
		if (e.getSource()== this.A.restartButton){
			/**Updating button and label conditions*/
			this.A.restartButton.setVisible(false);
			this.A.deathlabel.setVisible(false);
			this.A.startlabel.setVisible(true);
			this.A.scorelabel.setVisible(true);
				
			/**Setting to default for the project to restart. /!\ Do not change the order /!\*/
			this.V.restart();
			
			/**Repainting our work canvas.*/
			this.A.repaint();				
		} 
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}


