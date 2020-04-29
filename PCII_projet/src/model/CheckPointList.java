package model;

import java.util.ArrayList;

public class CheckPointList {
	private ArrayList<CheckPoint> cpList;
	private int horHeight;
	
	/** Constructor of the CheckPointList class
	 * @param horY the height of the horizon
	 **/
	public CheckPointList(int horY) {
		this.cpList = new ArrayList<CheckPoint>();
		this.horHeight = horY;
	}
	
	/** Gives the current checkpoint list
	 * @return the checkpoint list
	 **/
	public ArrayList<CheckPoint> getCpList() {
		return cpList;
	}
	
	/** Adds a new checkPoint, at the horizon, to the list
	 * @param dist the distance of the CheckPoint
	 **/
	public void addCP(int dist) {
		cpList.add(new CheckPoint(horHeight, 20, dist));
	}
	
	/** Moves the checkPoint :
	 * Lowers the visible height of every checkpoint by a given value and removes checkpoints that go too low
	 * @param moveVal, an integer. The height of every checkpoint decreases by it.
	 * @param maxY an integer. If a checkpoint's height is higher (ad it seems lower on the screen), the checkpoint is removed
	 **/
	public void moveCP(int moveVal, int maxY) {
		//The checkpoints move and we remove it if needed
		for(int i=0; i<cpList.size(); i++) {
			CheckPoint cp = cpList.get(i);
			cp.decreaseHeight(moveVal);
			if(cp.getHeight()>maxY) {
				cpList.remove(cp);
			}
		}
	}
	
}
