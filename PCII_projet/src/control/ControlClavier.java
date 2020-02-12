package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Vehicule;
import view.Affichage;

public class ControlClavier implements KeyListener{
	
	public Affichage A;
	public Vehicule V;
	
	public ControlClavier (Affichage a, Vehicule v) {
		this.A = a;
		this.V = v;
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
		}	
		/**Refreshing view and putting thread to sleep for 50 ms to see updates on our screen.*/
		A.repaint();
		try { Thread.sleep(50); }
		catch (Exception exc) { exc.printStackTrace(); }
		System.out.println(this.V.getMoveStatus());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
