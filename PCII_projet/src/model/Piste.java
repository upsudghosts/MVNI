package model;

import java.awt.Point;
import java.util.ArrayList;

public class Piste {
	
	private int hauteurHorizon;
	private ArrayList<Point> arrierePlan;
	
	public Piste() {
		this.hauteurHorizon = 10;
		this.arrierePlan = new ArrayList<Point>();
		
		this.genereArrierePlan();
	}
	
	private void genereArrierePlan() {
		
	}
	
	public int getHorizon() {
		return this.hauteurHorizon;
	}
	
}
