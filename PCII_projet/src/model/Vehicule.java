package model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class Vehicule {
	
	//Abscisse et ordonnee
	private int x, y;
	
	//Hitbox Dimensions
	private int hitWidth, hitHeight;
	
	private int position;
	
	//Vehicle status
	private boolean inFlight, isAlive;
	
	private String mvStat;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Vehicule() {
		this.x = screenSize.width/2-this.hitWidth;
		this.y = screenSize.height/2+this.hitHeight;
		this.hitHeight = 75;
		this.hitWidth = 150;
		
		this.inFlight = false;
		this.isAlive = true;
		
		this.mvStat = "NEUTRAL";
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
	
	public int getPos() {
		return this.position;
	}
	
	public boolean getFlyStatus() {
		return this.inFlight;
	}
	
	public boolean getAlive() {
		return this.isAlive;
	}
	
	public void move(String mvDir, int coef) {
		this.mvStat = mvDir;
		switch (mvStat) {
			case "LEFT":
				if(this.x  == this.hitWidth) {
					this.x = this.hitWidth;
					break;
				}
				this.x -= coef;
				break;
			case "RIGHT":
				if(this.x  == screenSize.width) {
					this.x = screenSize.width;
					break;
				}
				this.x += coef;
				break;
			case "UP":
				if(this.y  == this.hitHeight) {
					this.y = this.hitHeight;
					break;
				}
				this.y -= coef;
				break;
			case "DOWN":
				if(this.y  == screenSize.height - 3*this.hitHeight) {
					this.y = screenSize.height - 3*this.hitHeight;
					break;
				}
				this.y += coef;
				break;
			case "NEUTRAL":
				break;
		}
	}
	
	public String getMoveStatus() {
		return this.mvStat;
	}
	
	public void startRace() {
		this.inFlight = true;
	}
	
	public void stopRace() {
		this.isAlive = false;
		this.inFlight = false;
	}
	
	public void restart() {
		this.x = screenSize.width/2-this.hitWidth;
		this.y = screenSize.height/2+this.hitHeight;
		
		this.inFlight = false;
	}
}
