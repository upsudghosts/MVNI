package model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class Vehicule {
	
	//Abscisse et ordonnee
	private int x, y;
	private int z;
	
	//Hitbox Dimensions
	private int hitWidth, hitHeight;
	
	//Vehicle status
	private boolean inFlight, isAlive;
	
	private String mvStat;
	
	private long startTime, secTl; //LEFT TO LIVE in seconds
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	/*-------------------------------------------------------------------------------------------------------*/
    
    /**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                    - CONSTRUCTOR -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
    
    /**
     * Constructor for the Vehicule Class of the MVNI Project.
     */
	public Vehicule() {
		this.x = screenSize.width/2-this.hitWidth;
		this.y = screenSize.height/2+this.hitHeight;
		this.hitHeight = 150;
		this.hitWidth = 200;
		
		this.inFlight = false;
		this.isAlive = true;
		
		this.mvStat = "NEUTRAL";
	
		this.startTime = -20;
		this.secTl = -20;
		
		this.z = this.y;
	}
	
	/*-------------------------------------------------------------------------------------------------------*/
    
	
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                    - FUNCTIONS -
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	/**Gives a point with the coordinates of the vehicle
	 * @return a Point with the coordinates of the vehicle
	 **/
	public Point getCoord() {
		return new Point(x, y);
	}
	
	public Point getCenter() {
		return new Point(x+hitWidth/2, y+hitHeight/2);
	}
	
	/** Gives the distance of the vehicle
	 * @return an integer, the z-axis value of the vehicle
	 **/
	public int getZ() {
		return this.z;
	}

	/**Gives the width of the hitbox of the vehicle
	 * @return an int, the width of the hitbox
	 **/
	public int getHitWidth() {
		return hitWidth;
	}
	
	/**Gives the height of the hitbox of the vehicle
	 * @return an int, the height of the hitbox
	 **/
	public int getHitHeight() {
		return hitHeight;
	}
	
	/**Gives the flying status of the vehicle
	 * @return true if he vehicle is in flight, false otherwise
	 **/
	public boolean getFlyStatus() {
		return inFlight;
	}
	
	/** Tells if the game is on
	 * @return true if the vehicle is playing, false otherwise
	 * **/
	public boolean getAlive() {
		return isAlive;
	}
	
	/** Gives the remaining time to live of the vehicle 
	 * @return the time in milliseconds
	 **/
	public long getTTL() {
		return secTl;
	}
	
	/**Moves the vehicle a given distance in a given direction : left, right, up or down the screen
	 * @param mvDir a String, the direction where the vehicle will go
	 * @param coef an int, the distance the vehicle will travel
	 * **/
	public void move(String mvDir, int coef) {
		mvStat = mvDir;
		switch (mvStat) {
			case "LEFT":
				if(x  <= hitWidth) {
					x = hitWidth;
					break;
				}
				x -= coef;
				break;
			case "RIGHT":
				if(x  >= screenSize.width - hitWidth) {
					x = screenSize.width - hitWidth;
					break;
				}
				x += coef;
				break;
			case "UP":
				if(y  <= (int)(screenSize.height*0.4)) {
					y = (int)(screenSize.height*0.4);
					break;
				}
				y -= coef;
				break;
			case "DOWN":
				if(y  >= screenSize.height - 2*hitHeight) {
					y = screenSize.height - 2*hitHeight;
					break;
				}
				y += coef;
				break;
			case "NEUTRAL":
				break;
		}
	}
	
	/** Reduces the time of the start timer if it is higher than 1ms
	 * @param l the time we remove
	 **/
	public void timeDecrease(long l) {
		if(secTl >= 1) secTl = startTime - l;
		if(secTl == 0) stopRace();
	}
	
	/** Adds time to the start timer
	 * @param l the time we add
	 **/
	public void addTime(long l) {
		startTime = secTl + l;
		secTl += l;
	}
	
	/** Gives the status of the movement : tells if the vehicle is going up, down, left, right or if it doesn't move
	 * @return a String, the status of the movement
	 **/
	public String getMoveStatus() {
		return mvStat;
	}
	
	/** Starts the race : puts the vehicle in flight
	 * 
	 **/
	public void startRace() {
		inFlight = true;
	}
	
	/**Stops the race : sets the statuses of the vehicle as not in flight and not alive
	 * 
	 **/
	public void stopRace() {
		isAlive = false;
		inFlight = false;
	}
	
	/** Puts the vehicle at it's initial values
	 * 
	 **/
	public void restart() {
		x = screenSize.width/2-hitWidth;
		y = screenSize.height/2+hitHeight;

		this.inFlight = false;
		this.isAlive = true;
		
		this.mvStat = "NEUTRAL";
	
		this.startTime = 0;
		this.secTl = 0;
		
		this.z = this.y;
	}
}
