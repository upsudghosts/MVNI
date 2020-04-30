package model;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

public class Opponent extends Obstacle{
	
	private int speed;
	private int mvVal;
	private String mvStat;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public Opponent(int x, int y) {
		super(x, y);
		this.w = 150;
		this.h = 75;
		this.speed = 1;
		this.mvVal = 0;
		this.mvStat = "NEUTRAL";
	}
	
	
	@Override
	public void decreaseHeight(int moveVal) {
		this.setY(this.getY() + moveVal*speed);
		this.setD(this.getD() + moveVal*speed);
		
		//the obstacle gets bigger
		this.setY(this.getY()-2);
		this.setH(this.getY()+4);
		this.setX(this.getY()-2);
		this.setW(this.getY()+4);
	}
	
	
	//Voir les limites pour qu'il ne quitte pas l'ecran
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
				if(this.getY()  <= (int)(screenSize.height*0.2)) {
					//y = (int)(screenSize.height*0.2);
					this.setY((int)(screenSize.height*0.2));
					this.setD((int)(screenSize.height*0.2));
					break;
				}
				//y -= coef;
				this.setY(this.getY()-mvVal);
				this.setD(this.getD()-mvVal);
				break;
			case "DOWN":
				if(this.getY()  >= screenSize.height - 4*this.getH()) {
					//y = screenSize.height - 4*hitHeight;
					this.setY(screenSize.height - 4*this.getH());
					this.setD(screenSize.height - 4*this.getH());
					break;
				}
				this.setY(this.getY()+mvVal);
				this.setD(this.getD()+mvVal);
				//y += coef;
				break;
			case "NEUTRAL":
				break;
		}
	}

}
