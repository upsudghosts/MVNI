package model;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

public class Opponent extends Obstacle{
	
	private int speed;
	private int mvVal;
	private String mvStat;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	/** Constructor of the Opponent class
	 * @param x the abscissa at the higher left corner of the opponent
	 * @param y the ordinate at the higher left corner of the opponent
	 **/
	public Opponent(int x, int y) {
		super(x, y);
		this.w = 75;
		this.h = 15;
		this.speed = 1;
		this.mvVal = 0;
		this.mvStat = "NEUTRAL";
	}
	
	/*
	@Override
	public void decreaseHeight(int moveVal) {
		this.setY(this.getY() + moveVal);
		this.setD(this.getD() + moveVal);
		
		//the obstacle gets bigger
		this.setY(this.getY()-2);
		this.setH(this.getY()+4);
		this.setX(this.getY()-2);
		this.setW(this.getY()+4);
	}
	*/
	/** Moves the obstacle to the bottom from a given value
	 * @param moveVal the value that we want to move the obstacle
	 **/
	@Override
	public void decreaseHeight(int moveVal) {
		int y = this.getY() + moveVal*speed;
		int d = this.getD() + moveVal*speed;
		
		//the obstacle gets bigger
		y -=2*speed;
		int h = this.getH() + 2*speed;
		int x = this.getX() - 2*speed;
		int w = this.getW() + 2*speed;

		this.setD(d);
		this.setX(x);
		this.setY(y);
		this.setW(w);
		this.setH(h);
	}
	
	/** Lets the opponent change the movement of the obstacle randomly
	 * Has a chance to change the value of its movement on the plane of the screen, the direction of its movement, and its speed
	 **/
	public void move() {
		Random ran = new Random();
		
		//The opponent changes the way they move
		int n = ran.nextInt(100);
		if(n<=1) {
			int mv = ran.nextInt(5);
			
			if(mv<1) {
				this.mvStat = "LEFT";
			}else if(mv<2) {
				this.mvStat = "RIGHT";
			}else if(mv<3) {
				this.mvStat = "UP";
			}else if(mv<4) {
				this.mvStat = "DOWN";
			}else if(mv<5) {
				this.mvStat = "NEUTRAL";
			}
		}
		
		n = ran.nextInt(100);
		if(n<=5) {
			this.mvVal = ran.nextInt(3)*10;
		}
		
		n = ran.nextInt(100);
		if(n<=5) {
			int s = ran.nextInt(20);
			this.speed = (110-s)/100;
		}
		
		//The opponent moves
		switch (mvStat) {
			case "LEFT":
				if(this.getX()  <= this.getW()) {
					this.setX(this.getW());
					//x = hitWidth;
					break;
				} 
				this.setX(this.getX()-mvVal);
				//x -= coef;
				break;
			case "RIGHT":
				if(this.getX()  >= screenSize.width - 2*this.getW()) {
					//x = screenSize.width - 2*hitWidth;
					this.setX(screenSize.width - 2*this.getW());
					break;
				}
				this.setX(this.getX() + mvVal);
				//x += coef;
				break;
			case "UP":
				/*
				if(this.getY()  <= (int)(screenSize.height*0.2)) {
					//y = (int)(screenSize.height*0.2);
					
					this.setY((int)(screenSize.height*0.2));
					this.setD((int)(screenSize.height*0.2));
					
					
					break;
				}
				*/
				//System.out.println("mvVal : " + mvVal);
				//System.out.println("w1 : " + this.getW());
				//this.vMoveUp(mvVal);
				//System.out.println("w2 : " + this.getW());
				//y -= coef; 
				if(!(this.h<30) && !(this.w<30)) {
					int y = this.getY()-mvVal;
					int w = this.getW()-mvVal/5;
					int h = this.getH()-mvVal/5;
					
					
					//this.setY(this.getY()-mvVal);
					this.setY(y);
					//this.setW(n);
					this.setW(w);
					this.setH(h);
					this.setD(this.getD()-mvVal);
				}
				
				
				break;
			case "DOWN":
				/*
				if(this.getY()  >= screenSize.height - 4*this.getH()) {
					//y = screenSize.height - 4*hitHeight;
					
					this.setY(screenSize.height - 4*this.getH());
					this.setD(screenSize.height - 4*this.getH());
					
					
					break;
				}
				*/
				if(!(this.h>300) && !(this.w>300)) {
					this.setY(this.getY()+mvVal);
					this.setD(this.getD()+mvVal);
				}
				
				//y += coef;
				//this.vMoveDown(mvVal);
				break;
			case "NEUTRAL":
				break;
		}
	}
	
	/** Gives the status of the movement : tells if the vehicle is going up, down, left, right or if it doesn't move
	 * @return a String, the status of the movement
	 **/
	public String getMoveStatus() {
		return mvStat;
	}
	
}
