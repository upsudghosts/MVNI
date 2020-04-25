package model;

import java.awt.Point;
import java.awt.event.ActionListener;
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
	private static final int DISTTOCP = 5000;
	
	private ArrayList<Point> trackL, trackR;

	//private ArrayList<CheckPoint> cpList; //the checkpoint list
	private CheckPointList cpList; //the checkpoint list (new version)
	private ArrayList<Obstacle> obsList; //the obstacle list
	private ArrayList<Opponent> oppList;//the opponent list

	public Piste() {
		this.trackL = new ArrayList<Point>();
		this.trackR= new ArrayList<Point>();
		//this.cpList = new ArrayList<CheckPoint>();
		this.obsList = new ArrayList<Obstacle>();
		this.oppList = new ArrayList<Opponent>();
		//this.cpList.add(new CheckPoint(this.horHeight));
		
		this.trackSize = 0;
		this.traveledDist = 0;
		
		this.traveledSinceCP = 0;
	}
	
	/** Creates a track with two initial points at the bottom of the screen, and adds more points generated with the addPoint() method
	 * Also creates the CheckPointList and gives it the value of the horizon
	 **/
	public void createTrack() {
		cpList = new CheckPointList(horHeight); //Creation de la liste de checkPoint
		
		//Test obstacle
		//obsList.add(new Obstacle(200, horHeight));
		
		int currX = 300;
		int currY = maxY;
		
		trackL.add(new Point(currX, currY));
		trackR.add(new Point(maxX-currX, currY));
		
		trackSize ++;
		
		for(int i = 0; i < 2; i++) { addPoint(); }
	}
	
	/** Adds a new Point at the horizon height to each part of the track.
	 * The point added to the left track has a random x and the point added to the right track is on its right
	 **/
	public void addPoint() {
		Random ran = new Random();
		
		int prevXL = trackL.get(trackSize-1).x;
		
		int currY = horHeight;
		int currX = 0;
		
		int dir = ran.nextInt(100);
		if (dir > 50 && (prevXL < maxX - 200 || prevXL <200)) {
			currX = prevXL+50;
		} else if (dir <= 50 && (prevXL > maxX - 200 || prevXL > 200)){
			currX = prevXL-50;
		}
		
		trackL.add(new Point(currX, currY));
		
		symX = currX + 50 + ZOOM/2;
		
		trackR.add(new Point(symX , currY));
		trackSize ++;
	}
	
	/** Increases the speed by 2 if the current speed is not higher than 35 
	 * 
	 **/
	public void speedUp() {
		if (MOVEVAL < 35) { MOVEVAL += 2;}
	}
	
	/** Decreases the speed by 1 if the current speed is not lower than 10 
	 * 
	 **/
	public void speedDown() {
		if(MOVEVAL > 10) { MOVEVAL --; }
	}
	
	public void moveTrack() {
		if ( trackL.get(trackSize - 2).y < maxY) {	
			for(int i = 0; i < trackL.size()-1; i++){
				Point pL = trackL.get(i);
				Point pR = trackR.get(i);
	
				pL.x -= MOVEVAL;
				pL.y += MOVEVAL;
				
				pR.x += MOVEVAL;
				pR.y += MOVEVAL;
			}
		} else {
			addPoint();
			
			trackL.remove(0);
			trackR.remove(0);
			trackSize --;
		}
		traveledDist += tDistUp * MOVEVAL;
		traveledSinceCP += tDistUp * MOVEVAL;
		//The checkpoints move and we remove it if needed
		/*
		for(int i=0; i<cpList.size(); i++) {
			CheckPoint cp = cpList.get(i);
			cp.decreaseHeight(MOVEVAL);
			if(cp.getHeight()>maxY) {
				cpList.remove(cp);
			}
		}
		*/
		cpList.moveCP(MOVEVAL, maxY);
		//We add a checkpoint if we need it
		/*
		if(traveledDist%1000 == 0) {
			cpList.add(new CheckPoint(horHeight));
		}
		*/
		if(traveledSinceCP >= DISTTOCP) {
			traveledSinceCP = 0;
			cpList.addCP(traveledDist+DISTTOCP);
		}
		
		
		//Obstacle
		this.addRandObst();
		
		//The obstacles move and we remove it if needed
		for(int i=0; i<obsList.size(); i++) {
			Obstacle o = obsList.get(i);
			o.decreaseHeight(MOVEVAL);
			if(o.getH()>maxY) {
				obsList.remove(o);
			}
		}
		for(int i=0; i<oppList.size(); i++) {
			Opponent o = oppList.get(i);
			o.decreaseHeight(MOVEVAL);
			o.move();
			if(o.getH()>maxY) {
				oppList.remove(o);
			}
		}
	}
	
	
	/** Moves the track depending on the movement of the Vehicle :
	 * Zooms in or out of the vehicle goes lower or higher and moves the track to the right (left) if the vehicle goes to the left (right) 
	 * @param mvStat a String, the direction of the vehicle, that indicates which movement the track does
	 * @param coef an integer that indicates how big the movement is
	 **/
	public void trackEffect(String mvStat, int coef) {
		Point pL, pR;

		for(int i = 0; i < trackSize; i++) {
			pL = trackL.get(i);
			pR = trackR.get(i);
			switch (mvStat) {
			case "LEFT":
				pL.x += coef/10;
				pR.x += coef/10;
				
				//the obstacles move
				for(int j=0; j<this.obsList.size(); j++) {
					this.obsList.get(j).vMoveLeft(coef/10);
				}
				for(int j=0; j<this.oppList.size(); j++) {
					this.oppList.get(j).vMoveLeft(coef/10);
				}
				
				break;
				
			case "RIGHT":
				pL.x -= coef/10;
				pR.x -= coef/10;
				
				//the obstacles move
				for(int j=0; j<this.obsList.size(); j++) {
					this.obsList.get(j).vMoveRight(coef/10);
				}
				for(int j=0; j<this.oppList.size(); j++) {
					this.oppList.get(j).vMoveRight(coef/10);
				}
				
				break;
				
			case "UP":
				if(ZOOM > 0) {
					pL.x += coef/10;
					pR.x -= coef/10;
					ZOOM --;
					
					//the obstacles move
					for(int j=0; j<this.obsList.size(); j++) {
						this.obsList.get(j).vMoveUp(coef/10);
					}
					for(int j=0; j<this.oppList.size(); j++) {
						this.oppList.get(j).vMoveUp(coef/10);
					}
					
				}
				break;
				
			case "DOWN":
				if(ZOOM < 50) {
					pL.x -= coef/10;
					pR.x += coef/10;
					ZOOM ++;
					
					//the obstacles move
					for(int j=0; j<this.obsList.size(); j++) {
						this.obsList.get(j).vMoveDown(coef/10);
					}
					for(int j=0; j<this.oppList.size(); j++) {
						this.oppList.get(j).vMoveDown(coef/10);
					}
				}
				break;
				
			case "NEUTRAL":
				break;
			}
		}
	}
	
	/** Tests if the given vehicle hits one of the obstacles on the track
	 * @param V the vehicle
	 * @return true if the vehicle is hitting an obstacle, false otherwise
	 **/
	public boolean hitObst(Vehicule V) {
		for(int i=0; i<this.obsList.size(); i++) {
			if(this.obsList.get(i).hitV(V.getCoord().x, V.getCoord().y, V.getHitWidth(), V.getHitHeight(), V.getZ())) {
				return true;
			}
		}
		for(int i=0; i<this.oppList.size(); i++) {
			if(this.oppList.get(i).hitV(V.getCoord().x, V.getCoord().y, V.getHitWidth(), V.getHitHeight(), V.getZ())) {
				return true;
			}
		}
		return false;
	}
	
	/** Adds an obstacle on the ground with a probability 2/100 or a flying obstacle with a probability of 1/100
	 * 
	 **/
	public void addRandObst() {
		Random rand = new Random();
		
		/*
		if(Math.random() >= 0.9) {
			int x = (int) Math.random()*this.maxX;
			this.obsList.add(new Obstacle(x, horHeight));
		}
		*/
		int n = rand.nextInt(100);
		if(n<3) {
			//Obstacle on the ground
			int x = rand.nextInt(maxX);
			this.obsList.add(new Obstacle(x, horHeight));
		}else if(n==3) {
			//Flying obstacle
			int x = rand.nextInt(maxX);
			int y = rand.nextInt(horHeight);
			this.obsList.add(new Obstacle(x, y));
		}else if(n==4) {
			//Opponent
			int x = rand.nextInt(maxX);
			Opponent o = new Opponent(x, horHeight/2);
			this.oppList.add(o);
		}
	}
	
	/** Gives the horizon height
	 * @return an integer, the height of the horizon
	 **/
	public int getHorizon() {
		return horHeight;
	}
	
	/** Gives the current speed at whitch the track moves
	 * @return an integer, the current speed
	 **/
	public int getSpeed() {
		return MOVEVAL;
	}
	
	/** Gives the current distance traveled by the Vehicle
	 * @return an integer,  the distance travelled
	 **/
	public int getDist() {
		return traveledDist;
	}
	
	public int toCp() {
		return DISTTOCP - traveledSinceCP;
	}

	/** Sets the height of the horizon
	 * @param n an integer, the new height of the horizon
	 **/
	public void setHorizon(int n) {
		horHeight = n;
	}
	
	/** Gives the list of point forming the left part of the track
	 * @return the list of Point forming the left part of the track
	 **/
	public ArrayList<Point> getTrackL(){
		return trackL;
	}
	
	/** Gives the list of point forming the right part of the track
	 * @return the list of Point forming the right part of the track
	 **/
	public ArrayList<Point> getTrackR(){
		return trackR;
	}
	
	/** Gives the current checkpoint list
	 * @return the checkpoint list
	 **/
	public ArrayList<CheckPoint> getCP(){
		//System.out.println(cpList.getCpList().size());
		return cpList.getCpList();
	}
	
	/** Gives the current obstacle list
	 * @return the obstacle list
	 **/
	public ArrayList<Obstacle> getOL(){
		return obsList;
	}
	
	/** Gives the current opponent list
	 * @return the opponent list
	 **/
	public ArrayList<Opponent> getOpL(){
		return oppList;
	}

	
	/** Sets the maximum x coordinate that a track Point can have
	 * @param n an integer, the maximum X
	 **/
	public void setMaxX(int n) {
		maxX = n;
	}
	
	/** Sets the maximum Y coordinate that a track Point can have
	 * @param n an integer, the maximum height
	 **/
	public void setMaxY(int n) {
		maxY = n;
	}
	
	/** Gives the acceleration point : the point where the ship accelerates the most
	 * @return the acceleration Point
	 **/
	public Point getAccelPt() {
		return accelPt;
	}
	
	/** Sets the acceleration point by creating a new point with the given coordinates
	 * @param x an integer, the x coordinate of the Point
	 * @param y an integer, the y coordinate of the Point
	 **/
	private void setAccelPt(int x, int y) {
		accelPt = new Point(x,y);
	}
}
