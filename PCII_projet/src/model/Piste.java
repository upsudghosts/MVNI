package model;

import java.awt.Point;
import java.util.ArrayList;

public class Piste {
	
	private int horizonHeight;
	private ArrayList<Point> bg;
	
	public Piste() {
		this.horizonHeight = 10;
		this.bg = new ArrayList<Point>();
		
		this.genereArrierePlan();
	}
	
	private void genereArrierePlan() {
		
	}
	
	public int getHorizon() {
		return this.horizonHeight;
	}
	
}
