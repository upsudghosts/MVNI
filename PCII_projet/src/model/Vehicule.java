package model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import view.Affichage;

public class Vehicule {
	
	//Abscisse et ordonnee
	private int x, y;
	
	//Hitbox Dimensions
	private int hitWidth, hitHeight;
	
	//Vehicle status
	private boolean inFlight;
	private enum moveStatus{ LEFT, RIGHT, UP, DOWN, NEUTRAL};
	private moveStatus flyStatus;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Vehicule() {
		this.x = screenSize.width/2-this.hitWidth;
		this.y = screenSize.height/2+this.hitHeight;
		this.hitHeight = 50;
		this.hitWidth = 100;
		
		this.inFlight = false;
		this.flyStatus = moveStatus.NEUTRAL;
	}
	
	public Point getCoord() {
		return new Point(this.x, this.y);
	}

	public int getHitWidth() {
		return this.hitWidth;
	}
	
	public int getHitHeight() {
		return this.hitHeight;
	}
	
	public boolean getFlyStatus() {
		return this.inFlight;
	}
	
	public String getMoveStatus() {
		return ""+this.flyStatus+"";
	}
}
