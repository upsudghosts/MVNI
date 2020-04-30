package model;

public class Obstacle {
	//Coordinates
	private int x;
	private int y;
	//Width and height of the hitbox
	protected int w;
	protected int h;
	
	//Length of the hitbox
	private int z;
	
	//Travelled distance 
	private int d;
	
	/** Constructor of the Obstacle class
	 * @param x the abscissa at the higher left corner of the obstacle
	 * @param y the ordinate at the higher left corner of the obstacle
	 **/
	public Obstacle(int x, int y) {
		this.z = 100;
		this.d = 0; 
		

		this.w = 25;
		this.h = 50;
		
		//this.x = x+500;
		this.x = x;
		this.y = y-this.h;
	}
	
	/** Gives the abscissa at the higher left corner of the obstacle
	 * @return an integer, the abscissa
	 **/
	public int getX() {
		return x;
	}
	
	/** Gives the ordinate at the higher left corner of the obstacle
	 * @return an integer, the ordinate
	 **/
	public int getY() {
		return y;
	}
	
	/** Gives the width of the obstacle
	 * @return an integer, the width
	 **/
	public int getW() {
		return w;
	}
	
	/** Gives the height of the obstacle
	 * @return an integer, the height
	 **/
	public int getH() {
		return h;
	}
	
	/** Gives the distance traveled by the obstacle
	 * @return an integer, the distance
	 **/
	public int getD() {
		return d;
	}
	
	/** Sets the abscissa at the higher left corner of the obstacle
	 * @param x the abscissa
	 **/
	public void setX(int x) {
		this.x = x;
	}
	
	/** Sets the ordinate at the higher left corner of the obstacle
	 * @param y the ordinate
	 **/
	public void setY(int y) {
		this.y = y;
	}
	
	/** Sets the width of the obstacle
	 * @param w the width
	 **/
	public void setW(int w) {
		this.w = w;
	}
	
	/** Sets the height of the obstacle
	 * @param h the height
	 **/
	public void setH(int h) {
		this.h = h;
	}
	
	/** Sets the distance traveled by the obstacle
	 * @param d the distance
	 **/
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
	
	/** Moves the obstacle to the bottom from a given value
	 * @param moveVal the value that we want to move the obstacle
	 **/
	public void decreaseHeight(int moveVal) {
		y += moveVal;
		this.d += moveVal;
		
		//the obstacle gets bigger
		this.y -=2;
		this.h += 2;
		this.x -= 2;
		this.w += 2;
	}
	
	/** Moves the obstacle to the left from a given value
	 * @param n the value that we want to move the obstacle
	 **/
	public void vMoveLeft(int n) {
		this.x+=n;
	}
	
	/** Moves the obstacle to the right from a given value
	 * @param n the value that we want to move the obstacle
	 **/
	public void vMoveRight(int n) {
		this.x-=n;
	}
	
	/** Moves the obstacle up from a given value
	 * @param n the value that we want to move the obstacle
	 **/
	public void vMoveUp(int n) {
		this.y+=n/2;
		this.x += n/2;
		//this.x+=n/4;
		
		//this.w-=n/2;
		this.h-=n/2;
		this.w-=n;
		
	}
	
	/** Moves down the obstacle from a given value
	 * @param n the value that we want to move the obstacle
	 **/
	public void vMoveDown(int n) {
		this.y-=n/2;
		this.x -= n/2;
		//this.x-=n/4;
		
		//this.w+=n/2;
		this.h+=n/2;
		this.w+=n;
		//this.x -= n/2;
	}
	
	/** Zooms out the obstacle to represent the vehicle going higher by reducing the height and width of the obstacle
	 * @param n the value that we want to move the obstacle
	 * @param w the width we remove
	 * @param h the height we remove
	 **/
	public void vMoveUp(int n, int w, int h) {
		this.y+=n/2;
		this.x += n/2;
		
		//this.h = h;
		//this.w = w;
		this.h = h;
		this.w = w;
	} 
	
	/** Zooms in the obstacle to represent the vehicle going lower by increasing the height and width of the obstacle
	 * @param n the value that we want to move the obstacle
	 * @param w the width we add
	 * @param h the height we add
	 **/
	public void vMoveDown(int n, int w, int h) {
		this.y-=n/2;
		this.x -= n/2;
		
		this.h = h;
		this.w = w;
	}
	
	/** Tests if the obstacle is hitting a given vehicle
	 * @param V the vehicle
	 * @return true if the vehicle is hitting the obstacle, false otherwise
	 **/
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
	
	/** Tests if the obstacle is hitting an other obstacle
	 * @param obs the other obstacle
	 * @return true if the obstacle is hitting the other obstacle, false otherwise
	 **/
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
