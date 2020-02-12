package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Vehicule;
import view.Affichage;

public class ControlClavier implements KeyListener{
	
	public Affichage affichage;
	public Vehicule vehicule;
	
	public ControlClavier (Affichage a, Vehicule v) {
		this.affichage = a;
		this.vehicule = v;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//if(e.equals(KeyEvent.VK_KP_LEFT)) {
		//	System.out.print("G");
		//}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
