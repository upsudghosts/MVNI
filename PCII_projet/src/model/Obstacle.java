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
	
	public boolean contact(int xV, int yV, int wV, int hV) {
		if(xV<this.x || yV<this.y || xV+wV>this.x+this.w || yV+hV>this.y+this.h) {
			return true;
		}
		return false;
	}
}
