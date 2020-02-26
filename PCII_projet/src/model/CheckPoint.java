package model;

public class CheckPoint {

	private int height;
	
	public CheckPoint(int hor) {
		this.height = hor;
	}
	
	public void decreaseHeight(int n) {
		this.height += n;
	}
	
	public int getHeight() {
		return this.height;
	}

}
