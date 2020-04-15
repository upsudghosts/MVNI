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
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
	
	/*
	public void moveObt(int moveVal, int maxY) {
		//The obstacles move and we remove it if needed
		for(int i=0; i<  .size(); i++) {
			CheckPoint cp = cpList.get(i);
			cp.decreaseHeight(moveVal);
			if(cp.getHeight()>maxY) {
				cpList.remove(cp);
			}
		}
	}
	*/
	public void decreaseHeight(int moveVal) {
		y += moveVal;
	}
	
	public boolean contact(int xV, int yV, int wV, int hV) {
		if(xV<x || yV<y || xV+wV>x+w || yV+hV>y+h) {
			return true;
		}
		return false;
	}
}
