package model;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class CheckPointList {
	private ArrayList<CheckPoint> cpList;
	private int horHeight;
	
	public CheckPointList(int horY) {
		this.cpList = new ArrayList<CheckPoint>();
		this.horHeight = horY;

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
		this.cpList.add(new CheckPoint(this.horHeight, 15*1000));
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
	
}
