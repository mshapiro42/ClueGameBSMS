package clueGame;
//Blake Sanders
//Jonny Morsicato 
public class BoardCell {
	private int row;
	private int col;
	private String doorString;
	
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
	public void setDoorString(String doorString){
		this.doorString = doorString;
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
		if(doorString.length() != 2){
			return false;
		}
		if(doorString.charAt(1) == 'R' ||doorString.charAt(1) == 'L' || doorString.charAt(1) == 'U' || doorString.charAt(1) == 'D'){
			return true;
		}
		else{
			return false;
		}
	}

	public DoorDirection getDoorDirection() {
		char c = doorString.charAt(1);
		switch (c){
		case 'R' : return DoorDirection.RIGHT;
		case 'L' : return DoorDirection.LEFT;
		case 'U' : return DoorDirection.UP;
		case 'D' : return DoorDirection.DOWN;
		}
		return null;
	}
	public char getInitial() {
		char initial = doorString.charAt(0);
		return initial;
	}

}
