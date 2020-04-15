package model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class Vehicule {
	
	//Abscisse et ordonnee
	private int x, y;
	
	//Hitbox Dimensions
	private int hitWidth, hitHeight;
	
	//Vehicle status
	private boolean inFlight, isAlive;
	
	private String mvStat;
	
	private long secTl; //LEFT TO LIVE in seconds
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Vehicule() {
		this.x = screenSize.width/2-this.hitWidth;
		this.y = screenSize.height/2+this.hitHeight;
		this.hitHeight = 75;
		this.hitWidth = 150;
		
		this.inFlight = false;
		this.isAlive = true;
		
		this.mvStat = "NEUTRAL";
	
		this.secTl = 70;
	}
	
	/**Gives a point with the coordinates of the vehicle
	 * @return a Point with the coordinates of the vehicle
	 **/
	public Point getCoord() {
		return new Point(this.x, this.y);
	}

	/**Gives the width of the hitbox of the vehicle
	 * @return an int, the width of the hitbox
	 **/
	public int getHitWidth() {
		return this.hitWidth;
	}
	
	/**Gives the height of the hitbox of the vehicle
	 * @return an int, the height of the hitbox
	 **/
	public int getHitHeight() {
		return this.hitHeight;
	}
	
	/**Gives the flying status of the vehicle
	 * @return true if he vehicle is in flight, false otherwise
	 **/
	public boolean getFlyStatus() {
		return this.inFlight;
	}
	
	/** Tells if the game is on
	 * @return true if the vehicle is playing, false otherwise
	 * **/
	public boolean getAlive() {
		return this.isAlive;
	}
	
	public long getTTL() {
		return this.secTl;
	}
	
	/**Moves the vehicle a given distance in a given direction : left, right, up or down the screen
	 * @param mvDir a String, the direction where the vehicle will go
	 * @param coef an int, the distance the vehicle will travel
	 * **/
	public void move(String mvDir, int coef) {
		this.mvStat = mvDir;
		switch (mvStat) {
			case "LEFT":
				if(this.x  <= this.hitWidth) {
					this.x = this.hitWidth;
					break;
				}
				this.x -= coef;
				break;
			case "RIGHT":
				if(this.x  >= screenSize.width - 2*this.hitWidth) {
					this.x = screenSize.width - 2*this.hitWidth;
					break;
				}
				this.x += coef;
				break;
			case "UP":
				if(this.y  <= (int)(screenSize.height*0.2)) {
					this.y = (int)(screenSize.height*0.2);
					break;
				}
				this.y -= coef;
				break;
			case "DOWN":
				if(this.y  >= screenSize.height - 4*this.hitHeight) {
					this.y = screenSize.height - 4*this.hitHeight;
					break;
				}
				this.y += coef;
				break;
			case "NEUTRAL":
				break;
		}
	}
	
	public void timeDecrease(long l) {
		if(this.secTl > 1) this.secTl -= l;
	}
	/** Gives the status of the movement : tells if the vehicle is going up, down, left, right or if it doesn't move
	 * @return a String, the status of the movement
	 **/
	public String getMoveStatus() {
		return this.mvStat;
	}
	
	/** Starts the race : puts the vehicle in flight
	 * 
	 **/
	public void startRace() {
		this.inFlight = true;
	}
	
	/**Stops the race : sets the statuses of the vehicle as not in flight and not alive
	 * 
	 **/
	public void stopRace() {
		this.isAlive = false;
		this.inFlight = false;
	}
	
	/** Puts the vehicle at it's initial values
	 * 
	 **/
	public void restart() {
		this.x = screenSize.width/2-this.hitWidth;
		this.y = screenSize.height/2+this.hitHeight;
		
		this.inFlight = false;
	}
}
