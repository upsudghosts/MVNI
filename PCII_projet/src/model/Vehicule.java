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
	private boolean inFlight;
	
	private enum moveStatus{ 
		LEFT, RIGHT, UP, DOWN, NEUTRAL;
		
		private static moveStatus[] list = moveStatus.values();

	    public static moveStatus getVal(int i) {
	        return list[i];
	    }
	};
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
	
	public int getPos() {
		return this.position;
	}
	
	public boolean getFlyStatus() {
		return this.inFlight;
	}
	
	public String getMoveStatus() {
		return ""+this.flyStatus+"";
	}
	
	public void update_moveStatus(int index) {
		this.flyStatus = moveStatus.getVal(index);
	}
	
	public void move(int coef) {
		switch (this.flyStatus) {
			case LEFT:
				this.x -= coef;
				break;
			case RIGHT:
				this.x += coef;
				break;
			case UP:
				this.y += coef;
				break;
			case DOWN:
				this.y -= coef;
				break;
			case NEUTRAL:
				System.out.println("Not started");
				break;
		}
	}
	
	public void startRace() {
		this.inFlight = true;
	}
	
	public void stopRace() {
		this.inFlight = false;
	}
	
	public void restart() {
		this.x = screenSize.width/2-this.hitWidth;
		this.y = screenSize.height/2+this.hitHeight;
		
		this.inFlight = false;
		this.flyStatus = moveStatus.NEUTRAL;
	}
}
