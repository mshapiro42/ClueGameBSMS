package clueGame;
//Blake Sanders
//Jonny Morsicato 
public class BoardCell {
	private int row;
	private int col;
	private String s;
	public BoardCell(int row, int col, String s) {
		super();
		this.row = row;
		this.col = col;
		this.s = s;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public void setString(String s){
		this.s = s;
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
		if (s.length() != 1){
			return true;
		}
		else{
			return false;
		}

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
