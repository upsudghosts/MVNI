package model;

import java.awt.Point;
import java.util.ArrayList;

public class Piste {
	
	private static final int MOVEVAL = 5;
	private int horizonHeight;
	private int maxX;
	private int maxY;
	private int currY;
	private ArrayList<Point> bg;
	private ArrayList<Point> track;
	
	public Piste() {
		this.horizonHeight = 10;
		this.bg = new ArrayList<Point>();
		this.track = new ArrayList<Point>();
	}
	
	public void genereArrierePlan() {
		int y = (int) (Math.random() * (this.horizonHeight-50));
		this.bg.add(new Point(0, y));
		int currX = 0;
		while(currX<=maxX) {
			//int x = (int) (currX + (Math.random() * (maxX-currX)));
			int x = (int) (50+(Math.random() * (100-50)));
			//int x = (int) (50+(Math.random() * (this.max-50)));
			//y = (int) (50+(Math.random() * (this.horizonHeight - 50)));
			y = (int) (this.horizonHeight/8+(Math.random() * (this.horizonHeight - this.horizonHeight/8)));
			this.bg.add(new Point(currX+x, y));
			currX += x;
		}
	}
	
	public void createTrack() {
		//int currY = this.maxX;
		//while(currY > this.horizonHeight+100) {
		//	
		//}
		int x = (int) ((this.maxX/2 - this.maxX/8)+(Math.random() * ((this.maxX/2 + this.maxX/8)-(this.maxX/2 - this.maxX/8))));
		//int x = this.maxX/2;
		int currY = this.horizonHeight;
		this.track.add(new Point(x, currY));
		while(currY <= this.maxY) {
			x = (int) ((this.maxX/2 - this.maxX/8)+(Math.random() * ((this.maxX/2 + this.maxX/8)-(this.maxX/2 - this.maxX/8))));
			//currY += (int) (50+(Math.random() * (100-50)));
			currY += (int) ((this.maxY-this.horizonHeight)/8+(Math.random() * ((this.maxY-this.horizonHeight)/4-(this.maxY-this.horizonHeight)/8)));
			this.track.add(new Point(x, currY));
		}
	}
	
	public void createTrack2() {
		while(this.currY >= this.horizonHeight) {
			if(this.track.size()<1) {
				this.currY = this.maxY;
				this.track.add(new Point(this.maxX/2, this.maxY));
			}else {
					int x = (int) ((this.maxX/2 - this.maxX/8)+(Math.random() * ((this.maxX/2 + this.maxX/8)-(this.maxX/2 - this.maxX/8))));
					int y = (int) ((this.maxY-this.horizonHeight)/8+(Math.random() * ((this.maxY-this.horizonHeight)/4-(this.maxY-this.horizonHeight)/8)));
					this.currY -= y;
					this.track.add(new Point(x, currY));
			}
		}
		
	}
	
	public void addLastPoint() {
		this.currY = this.horizonHeight;
		int x = (int) ((this.maxX/2 - this.maxX/8)+(Math.random() * ((this.maxX/2 + this.maxX/8)-(this.maxX/2 - this.maxX/8))));
		this.track.add(new Point(x, this.currY));
	}
	
	public void moveTrack() {
		for(int i=0; i<this.track.size(); i++){
			Point p = this.track.get(i);
			p.y += MOVEVAL;
			//Si le point n'est plus utile, on le retire
			if(i>0) {
				if(p.y > this.maxY && this.track.get(i-1).y > this.maxY) {
					this.track.remove(i-1);
				}
			}
		}
		//Si il le faut, on rajoute un point
	}
	
	public int getHorizon() {
		return this.horizonHeight;
	}
	
	public void setHorizon(int n) {
		this.horizonHeight = n;
	}
	
	public ArrayList<Point> getBG(){
		return this.bg;
	}
	
	public ArrayList<Point> getTrack(){
		return this.track;
	}
	
	public void setMaxX(int n) {
		this.maxX = n;
	}
	
	public void setMaxY(int n) {
		this.maxY = n;
	}
	
	public void setCurrY(int n) {
		this.currY = n;
	}
	
	
	public static void main(String[] args) {
		Piste p = new Piste();
		p.setHorizon(50);
		p.setMaxY(500);
		p.setMaxX(500);
		p.createTrack();
		//p.createTrack2();
		System.out.println("Taille piste : " + p.getTrack().size());
		System.out.println("currY : " + p.currY);
	}
}
