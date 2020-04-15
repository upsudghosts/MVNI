package model;

public class Obstacle {
	//Coordinates
	private int x;
	private int y;
	//Width and height of the hitbox
	private int w;
	private int h;
	
	public Obstacle(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.w = 20;
		this.h = 15;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getW() {
		return this.w;
	}
	
	public int getH() {
		return this.h;
	}
	
	/*
	public void moveObt(int moveVal, int maxY) {
		//The obstacles move and we remove it if needed
		for(int i=0; i<this.  .size(); i++) {
			CheckPoint cp = this.cpList.get(i);
			cp.decreaseHeight(moveVal);
			if(cp.getHeight()>maxY) {
				this.cpList.remove(cp);
			}
		}
	}
	*/
	public void decreaseHeight(int moveVal) {
		this.y += moveVal;
	}
	
	public boolean contact(int xV, int yV, int wV, int hV) {
		if(xV<this.x || yV<this.y || xV+wV>this.x+this.w || yV+hV>this.y+this.h) {
			return true;
		}
		return false;
	}
}
