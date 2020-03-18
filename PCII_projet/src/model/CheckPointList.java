package model;

import java.util.ArrayList;

public class CheckPointList {
	private ArrayList<CheckPoint> cpList;
	private int horHeight;
	private int timeToAdd; //time in ms to add to the timer once we reach the next checkpoint
	private int timeToSub; //time to substract to the time to add
	private int timer;
	private long prevTime;
	
	public CheckPointList(int horY) {
		this.cpList = new ArrayList<CheckPoint>();
		this.horHeight = horY;
		
		this.timer = 0;
		this.timeToAdd = 15*1000;
		this.timeToSub = 2*1000;
		this.prevTime = System.currentTimeMillis();
	}
	
	/** Gives the current checkpoint list
	 * @return the checkpoint list
	 **/
	public ArrayList<CheckPoint> getCpList() {
		return this.cpList;
	}
	
	/** Adds a new checkPoint, at the horizon, to the list
	 * 
	 **/
	public void addCP() {
		this.cpList.add(new CheckPoint(this.horHeight));
	}
	
	/** Moves the checkPoint :
	 * Lowers the visible height of every checkpoint by a given value and removes checkpoints that go too low
	 * @param moveVal, an integer. The height of every checkpoint decreases by it.
	 * @param maxY an integer. If a checkpoint's height is higher (ad it seems lower on the screen), the checkpoint is removed
	 **/
	public void moveCP(int moveVal, int maxY) {
		//The checkpoints move and we remove it if needed
		for(int i=0; i<this.cpList.size(); i++) {
			CheckPoint cp = this.cpList.get(i);
			cp.decreaseHeight(moveVal);
			if(cp.getHeight()>maxY) {
				this.cpList.remove(cp);
			}
		}
	}
	
	/** Changes the value of the timer
	 * 
	 **/
	//Probablement pas tres efficace
	public void changeTime() {
		long currTime = System.currentTimeMillis();
		this.timer -= (currTime - prevTime);
		this.prevTime = currTime;
	}
	
	/** Adds time to the timer and lowers the time the player will have to reach the next checkPoint
	 * This method is used when the player reaches a checkpoint
	 **/
	public void reachedCP() {
		this.timer += this.timeToAdd;
		this.timeToAdd -= timeToSub;
	}
}
