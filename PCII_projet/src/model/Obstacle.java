package model;

public class Obstacle {
	//Coordinates
	private int x;
	private int y;
	//Width and height of the hitbox
	private int w;
	private int h;
	
	//Length of the hitbox
	private int z;
	
	//Travelled distance 
	private int d;
	
	public Obstacle(int x, int y) {
		this.z = 100;
		this.d = 0; 
		
		this.w = 400;
		this.h = 200;
		
		this.x = x+500;
		this.y = y-this.h;
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
		this.d += moveVal;
	}
	
	public boolean hitV(int xV, int yV, int wV, int hV, int zV) {
		
		//If the obstacle is too far away from the vehicle, we know they cannot touch each other
		
		if(zV < this.d-this.z/2 || zV > this.d+this.z/2) {
			return false;
		}
		
		int maxG = Math.max(this.x, xV);
		int minD = Math.min(this.x+this.w, xV+wV);
		int minH = Math.min(this.y+this.h, yV+hV);
		int maxB = Math.max(this.y, yV);
		
		if(maxG<minD && maxB<minH) {
			return true;
		}
		return false;
	}
}
