package model;

import java.awt.Point;

public class Vehicule {
	
	//Abscisse et ordonnee
	private int x, y;
	private int hitWidth, hitHeight;
	
	public Vehicule(int abs,  int ord) {
		this.x = abs;
		this.y = ord;
		this.hitHeight = 50;
		this.hitWidth = 100;
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
}
