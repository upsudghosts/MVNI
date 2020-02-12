package model;

import java.awt.Point;
import java.util.ArrayList;

public class Piste {
	
	private int horizonHeight;
	private int maxX;
	private ArrayList<Point> bg;
	
	public Piste() {
		this.horizonHeight = 10;
		this.bg = new ArrayList<Point>();
		
		this.genereArrierePlan();
	}
	
	private void genereArrierePlan() {
		int currX = 0;
		while(currX<maxX) {
			int x = (int) (currX + (Math.random() * (maxX-currX)));
			int y = (int) (Math.random() * this.horizonHeight);
			this.bg.add(new Point(x, y));
		}
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
	
	public void setMaxX(int n) {
		this.maxX = n;
	}
	
}
