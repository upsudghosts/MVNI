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
	
	public static void main(String[] args) {
		Piste piste = new Piste();
		piste.setHorizon(50);
		piste.setMaxX(500);
		piste.genereArrierePlan();
		ArrayList<Point> li = piste.getBG();
		System.out.println("Taille liste points : " + li.size());
		
		for(int i=0; i<li.size()-2; i++) {
    		Point p1 = li.get(i);
    		Point p2 = li.get(i+1);
    		System.out.println("(" + p1.x + ", " + p1.y + ") ; (" + p2.x + ", " + p2.y + ")");
    	}
		
	}
}
