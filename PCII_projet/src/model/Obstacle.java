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
		
		this.w = 25;
		this.h = 50;
		
		//this.x = x+500;
		this.x = x;
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
	
	public int getD() {
		return d;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setW(int w) {
		this.w = w;
	}
	
	public void setH(int h) {
		this.h = h;
	}
	
	public void setD(int d) {
		this.d = d;
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
		
		//the obstacle gets bigger
		this.y -=2;
		this.h += 2;
		this.x -= 2;
		this.w += 2;
	}
	
	public void vMoveLeft(int n) {
		this.x+=n;
	}
	
	public void vMoveRight(int n) {
		this.x-=n;
	}
	
	public void vMoveUp(int n) {
		this.y+=n/2;
		this.x += n/2;
		//this.x+=n/4;
		
		//this.w-=n/2;
		this.h-=n/2;
		this.w-=n;
		
	}
	
	public void vMoveDown(int n) {
		this.y-=n/2;
		this.x -= n/2;
		//this.x-=n/4;
		
		//this.w+=n/2;
		this.h+=n/2;
		this.w+=n;
		//this.x -= n/2;
	}
	
	public void vMoveUp(int n, int w, int h) {
		this.y+=n/2;
		this.x += n/2;
		
		//this.h = h;
		//this.w = w;
		this.h = h;
		this.w = w;
	} 
	
	public void vMoveDown(int n, int w, int h) {
		this.y-=n/2;
		this.x -= n/2;
		
		this.h = h;
		this.w = w;
	}
	
	public boolean hitV(Vehicule V) {
		//If the obstacle is too far away from the vehicle, we know they cannot touch each other
		/*
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
		*/
		
		if(V.getZ() < this.d-this.z/2 || V.getZ() > this.d+this.z/2) {
			return false;
		}
		
		int maxG = Math.max(this.x, V.getCoord().x);
		int minD = Math.min(this.x + this.w, V.getCoord().x + V.getHitWidth());
		int minH = Math.min(this.y + this.h, V.getCoord().y + V.getHitHeight());
		int maxB = Math.max(this.y, V.getCoord().y);
		
		if(maxG<minD && maxB<minH) {
			return true;
		}
		return false;
	}
	
	public boolean hitObs(Obstacle obs) {
		if(obs.getD() < this.d-this.z/2 || obs.getD() > this.d+this.z/2) {
			return false;
		}
		
		int maxG = Math.max(this.x, obs.getX());
		int minD = Math.min(this.x + this.w, obs.getX() + obs.getW());
		int minH = Math.min(this.y + this.h, obs.getY() + obs.getH());
		int maxB = Math.max(this.y, obs.getY());
		
		if(maxG<minD && maxB<minH) {
			return true;
		}
		return false;
	}
}
