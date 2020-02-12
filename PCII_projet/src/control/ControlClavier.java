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
		if(e.equals(KeyEvent.VK_DOWN)) {
			System.out.print("Down");
		}else
		if(e.equals(KeyEvent.VK_UP)) {
			System.out.print("Up");
		}else
		if(e.equals(KeyEvent.VK_LEFT)) {
			System.out.print("Left");
		}else
		if(e.equals(KeyEvent.VK_RIGHT)) {
			System.out.print("Right");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
