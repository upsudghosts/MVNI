package model;

public class Opponent extends Obstacle{
	
	private int speed;
	private int mvVal;
	private String mvStat;

	public Opponent(int x, int y) {
		super(x, y);
		this.mvVal = 0;
		this.mvStat = "LEFT";
	}
	
	public void move() {
		
	}

}
