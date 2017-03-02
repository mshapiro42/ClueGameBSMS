package clueGame;
//Blake Sanders
//Jonny Morsicato 
public class BoardCell {
	private int row;
	private int col;
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setCol(int col) {
		this.col = col;
	}
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + "]";
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	}

	public Object[] getDoorDirection() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getInitial() {
		String initial = "";
		return initial;
	}

}
