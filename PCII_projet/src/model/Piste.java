package model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import view.Affichage;

public class Piste {
	
	private int MOVEVAL = 5;
	private int horHeight, trackSize;
	private int maxX, maxY, distX;

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
		
		this.distX = 300;
		this.trackSize = 0;
		this.traveledDist = 0;
	}
	
	public void createTrack() {
		int i = 0;
		
		int x = (int) ((this.maxX/4 - this.maxX/8)+(Math.random() * ((this.maxX/2 + this.maxX/8)-(this.maxX/2 - this.maxX/8))));
		int currY = this.maxY;
		this.trackL.add(new Point(0, currY));
		this.trackR.add(new Point(this.maxX, currY));
		this.trackSize ++;
		
		while(currY >= this.horHeight) {
			i++;
			x = (int) ((this.maxX/4 - this.maxX/8)+(Math.random() * ((this.maxX/2 + this.maxX/8)-(this.maxX/2 - this.maxX/8))));
			currY -= (int) ((this.maxY-this.horHeight)/8+(Math.random() * ((this.maxY-this.horHeight)/4-(this.maxY-this.horHeight)/8)));
			this.trackL.add(new Point(x, currY));
			this.distX = this.distX - 10*i;
			this.trackR.add(new Point(this.maxX/2 + (this.maxX/2-x), currY));
			this.trackSize ++;
		}
	}
	
	public void addPoint() {
		int currY = this.horHeight;
		int x = (int) ((this.maxX/2 - this.maxX/8)+(Math.random() * ((this.maxX/2 + this.maxX/8)-(this.maxX/2 - this.maxX/8))));
		this.trackL.add(new Point(x, currY));
		this.trackR.add(new Point(x+this.distX, currY));
	}
	
	public void speedUp() {
		if (this.MOVEVAL < 15) { this.MOVEVAL += 2;}
	}
	
	public void speedDown() {
		if(this.MOVEVAL > 0) { this.MOVEVAL --; }
	}
	
	public void moveTrack() {
		for(int i = 0; i < this.trackL.size() ; i++){
			Point pL = this.trackL.get(i);
			Point pR = this.trackR.get(i);
			
			pL.x -= MOVEVAL;
			pL.y += MOVEVAL;
			
			pR.x += MOVEVAL;
			pR.y += MOVEVAL;
			
			//Si le point n'est plus utile, on le retire
			if(i>0) {
				if(pL.y > this.maxY && this.trackL.get(i-1).y > this.maxY) {
					this.addPoint();
					
					this.trackL.remove(i-1);
					this.trackR.remove(i-1);
				}
			}
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

	public static void main(String[] args) {
		Piste p = new Piste();
		p.setHorizon(50);
		p.setMaxY(500);
		p.setMaxX(500);
		p.createTrack();
		//p.createTrack2();
		System.out.println("Taille piste : " + p.trackSize);
	}
}
