package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Piste {
	
	private int MOVEVAL = 20, ZOOM = 0;
	private int horHeight, trackSize;
	private int maxX, maxY, symX;
	
	private Point accelPt; //Le point auquel on accelere le plus
	private int traveledDist;
	public final int tDistUp = 1;
	
	private int traveledSinceCP;
	private static final int DISTTOCP = 1000;
	
	private ArrayList<Point> trackL, trackR;

	//private ArrayList<CheckPoint> cpList; //the checkpoint list
	private CheckPointList cpList; //the checkpoint list (new version)


	
	public Piste() {
		this.trackL = new ArrayList<Point>();
		this.trackR= new ArrayList<Point>();
		//this.cpList = new ArrayList<CheckPoint>();
		
		//this.cpList.add(new CheckPoint(this.horHeight));
		
		this.trackSize = 0;
		this.traveledDist = 0;
		
		this.traveledSinceCP = 0;
	}
	
	/** Creates a track with two initial points at the bottom of the screen, and adds more points generated with the addPoint() method
	 * Also creates the CheckPointList and gives it the value of the horizon
	 **/
	public void createTrack() {
		this.cpList = new CheckPointList(this.horHeight); //Creation de la liste de checkPoint
		
		int currX = 300;
		int currY = this.maxY;
		
		this.trackL.add(new Point(currX, currY));
		this.trackR.add(new Point(this.maxX-currX, currY));
		
		this.trackSize ++;
		
		for(int i = 0; i < 2; i++) { this.addPoint(); }
	}
	
	/** Adds a new Point at the horizon height to each part of the track.
	 * The point added to the left track has a random x and the point added to the right track is on its right
	 **/
	public void addPoint() {
		Random ran = new Random();
		
		int prevXL = this.trackL.get(this.trackSize-1).x;
		
		int currY = this.horHeight;
		int currX = 0;
		
		int dir = ran.nextInt(100);
		if (dir > 50 && (prevXL < this.maxX - 200 || prevXL <200)) {
			currX = prevXL+50;
		} else if (dir <= 50 && (prevXL > this.maxX - 200 || prevXL > 200)){
			currX = prevXL-50;
		}
		
		this.trackL.add(new Point(currX, currY));
		
		this.symX = currX + 50 + ZOOM/2;
		
		this.trackR.add(new Point(this.symX , currY));
		this.trackSize ++;
	}
	
	/** Increases the speed by 2 if the current speed is not higher than 35 
	 * 
	 **/
	public void speedUp() {
		if (this.MOVEVAL < 35) { this.MOVEVAL += 2;}
	}
	
	/** Decreases the speed by 1 if the current speed is not lower than 10 
	 * 
	 **/
	public void speedDown() {
		if(this.MOVEVAL > 10) { this.MOVEVAL --; }
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
		this.traveledSinceCP += tDistUp*this.MOVEVAL;
		//The checkpoints move and we remove it if needed
		/*
		for(int i=0; i<this.cpList.size(); i++) {
			CheckPoint cp = this.cpList.get(i);
			cp.decreaseHeight(this.MOVEVAL);
			if(cp.getHeight()>this.maxY) {
				this.cpList.remove(cp);
			}
		}
		*/
		this.cpList.moveCP(this.MOVEVAL, this.maxY);
		//We add a checkpoint if we need it
		/*
		if(this.traveledDist%1000 == 0) {
			this.cpList.add(new CheckPoint(this.horHeight));
		}
		*/
		if(this.traveledSinceCP >= DISTTOCP) {
			this.traveledSinceCP = 0;
			this.cpList.addCP();
		}
	}
	
	
	/** Moves the track depending on the movement of the Vehicle :
	 * Zooms in or out of the vehicle goes lower or higher and moves the track to the right (left) if the vehicle goes to the left (right) 
	 * @param mvStat a String, the direction of the vehicle, that indicates which movement the track does
	 * @param coef an integer that indicates how big the movement is
	 **/
	public void trackEffect(String mvStat, int coef) {
		Point pL, pR;

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
				if(ZOOM > 0) {
					pL.x += coef/10;
					pR.x -= coef/10;
					ZOOM --;
				}
				break;
			case "DOWN":
				if(ZOOM < 50) {
					pL.x -= coef/10;
					pR.x += coef/10;
					ZOOM ++;
				}
				break;
			case "NEUTRAL":
				break;
			}
		}
	}
	
	/** Gives the horizon height
	 * @return an integer, the height of the horizon
	 **/
	public int getHorizon() {
		return this.horHeight;
	}
	
	/** Gives the current speed at whitch the track moves
	 * @return an integer, the current speed
	 **/
	public int getSpeed() {
		return this.MOVEVAL;
	}
	
	/** Gives the current distance traveled by the Vehicle
	 * @return an integer,  the distance travelled
	 **/
	public int getDist() {
		return this.traveledDist;
	}

	/** Sets the height of the horizon
	 * @param n an integer, the new height of the horizon
	 **/
	public void setHorizon(int n) {
		this.horHeight = n;
	}
	
	/** Gives the list of point forming the left part of the track
	 * @return the list of Point forming the left part of the track
	 **/
	public ArrayList<Point> getTrackL(){
		return this.trackL;
	}
	
	/** Gives the list of point forming the right part of the track
	 * @return the list of Point forming the right part of the track
	 **/
	public ArrayList<Point> getTrackR(){
		return this.trackR;
	}
	
	/** Gives the current checkpoint list
	 * @return the checkpoint list
	 **/
	public ArrayList<CheckPoint> getCP(){
		//System.out.println(this.cpList.getCpList().size());
		return this.cpList.getCpList();
	}
	
	/** Sets the maximum x coordinate that a track Point can have
	 * @param n an integer, the maximum X
	 **/
	public void setMaxX(int n) {
		this.maxX = n;
	}
	
	/** Sets the maximum Y coordinate that a track Point can have
	 * @param n an integer, the maximum height
	 **/
	public void setMaxY(int n) {
		this.maxY = n;
	}
	
	/** Gives the acceleration point : the point where the ship accelerates the most
	 * @return the acceleration Point
	 **/
	public Point getAccelPt() {
		return this.accelPt;
	}
	
	/** Sets the acceleration point by creating a new point with the given coordinates
	 * @param x an integer, the x coordinate of the Point
	 * @param y an integer, the y coordinate of the Point
	 **/
	private void setAccelPt(int x, int y) {
		this.accelPt = new Point(x,y);
	}
}
