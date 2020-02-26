package model;

public class CheckPoint {

	private int height;
	
	public CheckPoint(int hor) {
		this.height = hor;
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

}
