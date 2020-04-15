package model;

public class CheckPoint {

	private int height;
	private long secToAdd; //time to add to the timer once we reach the checkpoint in seconds
	
	public CheckPoint(int hor, int tta) {
		this.height = hor;
		this.secToAdd = tta;
	}
	
	/**Lowers the CheckPoint by increasing the value of the height variable**/
	public void decreaseHeight(int n) {
		this.height += n;
	}
	
	/**Gives the height of the checkpoint
	 * @return an int, the height
	 **/
	public int getHeight() {
		return this.height;
	}
	
	/**Gives the time to add of the checkpoint
	 * @return an int, the time
	 **/
	public long getSecToAdd() {
		return this.secToAdd;
	}

}
