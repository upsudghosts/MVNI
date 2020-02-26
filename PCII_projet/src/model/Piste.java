package model;

import java.awt.Point;
import java.util.ArrayList;

public class Piste {
	
	private int MOVEVAL = 10;
	private int horHeight, trackSize;
	private int maxX, maxY, symX;
	
	private int coefProf;

	private Point accelPt; //Le point auquel on accelere le plus
	private int traveledDist;
	public final int tDistUp = 1;
	
	private ArrayList<Point> trackL, trackR;

	private ArrayList<CheckPoint> cpList;


	
	public Piste() {
		this.horHeight = 10;
		this.trackL = new ArrayList<Point>();
		this.trackR= new ArrayList<Point>();
		this.cpList = new ArrayList<CheckPoint>();
		
		this.cpList.add(new CheckPoint(this.horHeight));
		
		this.trackSize = 0;
		this.coefProf = 20;
		this.traveledDist = 0;
	}
	
	public void createTrack() {
		int currX = 300;
		int currY = this.maxY;
		
		this.trackL.add(new Point(currX, currY));
		this.trackR.add(new Point(this.maxX-currX, currY));
		
		this.trackSize ++;
		
		for(int i = 0; i < 2; i++) { this.addPoint(); }
	}
	
	public void addPoint() {
		int prevX = this.trackL.get(this.trackSize-1).x;
		int currY = this.horHeight;
		int x = 0;
		if ((int)(Math.random()*10) > 5 && (prevX < this.maxX - 200 || prevX <200)) {
			x = (int) (prevX+50);
		} else if ((int)(Math.random()*10) <= 5 && (prevX > this.maxX - 200 || prevX > 200)){
			x = (int) (prevX-50);
		}
		
		this.trackL.add(new Point(x, currY));
		this.symX = x + 10*this.coefProf;
		this.trackR.add(new Point(this.symX , currY));
		this.trackSize ++;
	}
	
	public void speedUp() {
		if (this.MOVEVAL < 20) { this.MOVEVAL += 2;}
	}
	
	public void speedDown() {
		if(this.MOVEVAL > 3) { this.MOVEVAL --; }
	}
	
	public void moveTrack() {

		if ( this.trackL.get(this.trackSize - 2).y < this.maxY) {	
			for(int i = 0; i < this.trackL.size()-1; i++){
				Point pL = this.trackL.get(i);
				Point pR = this.trackR.get(i);
	
				pL.x -= MOVEVAL;
				pL.y += MOVEVAL;
				
				pR.x += MOVEVAL;
				pR.y += MOVEVAL;
			}
		} else {
			this.addPoint();
			
			this.trackL.remove(0);
			this.trackR.remove(0);
			this.trackSize --;
		}
		this.traveledDist += tDistUp*this.MOVEVAL;
		//The checkpoints move and we remove it if needed
		for(int i=0; i<this.cpList.size(); i++) {
			CheckPoint cp = this.cpList.get(i);
			cp.decreaseHeight(this.MOVEVAL);
			if(cp.getHeight()>this.maxY) {
				this.cpList.remove(cp);
			}
		}
		//We add a checkpoint if we need it
		if(this.traveledDist%1000 == 0) {
			this.cpList.add(new CheckPoint(this.horHeight));
		}
	}
	
	public void trackEffect(String mvStat, int coef) {
		Point pL, pR;
		int lastLX = this.trackL.get(this.trackSize-1).x;
		int lastRX = this.trackR.get(this.trackSize-1).x;
		
		int firstLX = this.trackL.get(0).x;
		int firstRX = this.trackR.get(0).x;
		
		
		for(int i = 0; i < this.trackSize; i++) {
			pL = this.trackL.get(i);
			pR = this.trackR.get(i);
			switch (mvStat) {
			case "LEFT":
				pL.x += coef/10;
				pR.x += coef/10;
				break;
			case "RIGHT":
				pL.x -= coef/10;
				pR.x -= coef/10;
				break;
			case "UP":
				if(lastRX - lastLX > 5) {
					pL.x += coef/10;
					pR.x -= coef/10;
				}
				break;
			case "DOWN":
				if(firstRX - firstLX < this.maxX) {
					pL.x -= coef/10;
					pR.x += coef/10;
				}
				break;
			case "NEUTRAL":
				break;
			}
		}
	}
	
	public int getHorizon() {
		return this.horHeight;
	}
	
	public int getSpeed() {
		return this.MOVEVAL;
	}
	
	public int getDist() {
		return this.traveledDist;
	}
	
	public void setHorizon(int n) {
		this.horHeight = n;
	}
	
	public ArrayList<Point> getTrackL(){
		return this.trackL;
	}
	
	public ArrayList<Point> getTrackR(){
		return this.trackR;
	}
	
	public ArrayList<CheckPoint> getCP(){
		return this.cpList;
	}
	
	public void setMaxX(int n) {
		this.maxX = n;
	}
	
	public void setMaxY(int n) {
		this.maxY = n;
	}
	
	public Point getAccelPt() {
		return this.accelPt;
	}
	
	private void setAccelPt(int x, int y) {
		this.accelPt = new Point(x,y);
	}
}
