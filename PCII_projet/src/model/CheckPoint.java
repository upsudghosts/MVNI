package model;

public class CheckPoint {

	private int height, dist;
	
	/** Constructor of the CheckPoint class
	 * @param hor the horizon height
	 * @param tta the time to add to the timer
	 * @param dist the distance of the checkpoint
	 **/
	public CheckPoint(int hor, int dist) {
		this.height = hor;
		this.dist =  dist;
	}
	
	/**Lowers the CheckPoint by increasing the value of the height variable**/
	public void decreaseHeight(int n) {
		height += n;
	}
	
	/**Gives the height of the checkpoint
	 * @return an int, the height
	 **/
	public int getHeight() {
		return height;
	}
	
	/**Gives the distances of the checkpoint
	 * @return an int, the distance
	 **/
	public int getDist() {
		return dist;
	}
}
