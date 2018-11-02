package ai;

import java.util.List;

public class Move {
	/*class contains:
	 * -coordinates of move
	 * -parent move class
	 * -array of possible move classes
	 * -reward value
	 * 
	 */
	
	private Integer[] coords = new Integer[2];
	private Move parent = null;
	private List<Move> children = null;
	private float reward = 1;
	
	public Integer[] getCoords() {
		return coords;
	}
	public void setCoords(Integer[] coords) {
		this.coords = coords;
	}
	public Move getParent() {
		return parent;
	}
	public void setParent(Move parent) {
		this.parent = parent;
	}
	public List<Move> getChildren() {
		return children;
	}
	public void setChildren(List<Move> possMoves) {
		this.children = possMoves;
	}
	public float getReward() {
		return reward;
	}
	public void setReward(float reward) {
		this.reward = reward;
	}
	
	
}