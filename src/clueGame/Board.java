package clueGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import experiment.BoardCell;


public class Board{

	private Map<BoardCell, HashSet<BoardCell>> adjMtx = new HashMap<BoardCell, HashSet<BoardCell>>();
	private Set<BoardCell> visited = new HashSet<BoardCell>(); // this is just the interface remember to initialize a new set when using
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> adjSet = new HashSet<BoardCell>();
	private BoardCell[][] grid;
	
	public Board(BoardCell[][] grid) {
		super();
		this.grid = grid;
	}
	
	public static Board getInstance() {
		Board board = new Board(new BoardCell[100][100]);
		return board;
	}



	public void setConfigFiles(String string, String string2) {
		// TODO Auto-generated method stub
		
	}

	public void initialize() {
		// TODO Auto-generated method stub
	}
	
	public int getNumRows() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getNumColumns(){
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Map<Character,String> getLegend(){
		return null;
	}

	public BoardCell getCellAt(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

}
