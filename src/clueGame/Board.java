package clueGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.FileReader;



public class Board{

	private Map<BoardCell, HashSet<BoardCell>> adjMtx = new HashMap<BoardCell, HashSet<BoardCell>>();
	private Set<BoardCell> visited = new HashSet<BoardCell>(); // this is just the interface remember to initialize a new set when using
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> adjSet = new HashSet<BoardCell>();
	private Map<Character, String> legendMap = new HashMap<Character, String>();
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
	
		
	}

	public void initialize() {
	
	}
	
	public int getNumRows() {
		
		return 0;
	}
	
	public int getNumColumns(){
		
		return 0;
	}
	
	public Map<Character,String> getLegend(){
		makeLegend();
		return legendMap;
	}
	
	public void makeLegend() {
		legendMap.clear();
		Character mapKey = null;
		String roomName = "";
		String temp = "";
		FileReader reader = null;
		
		try {
			reader = new FileReader("ourData/Legend.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		Scanner in = new Scanner(reader);
		while(in.hasNextLine()){
			temp = in.nextLine();
			try{
				mapKey = temp.charAt(0);
			} catch (NumberFormatException e1) {
				System.err.println("\nIncorrect format for " + mapKey + "Not a char");
			}
			roomName = temp.substring(3);
			System.out.println("roomName is: " + roomName);
			legendMap.put(mapKey, roomName);
			System.out.println(legendMap);
		}
		in.close();
	}

	public clueGame.BoardCell getCellAt(int i, int j) {
		
		return grid[i][j];
	}

	public void loadRoomConfig() {
	
		
	}

	public void loadBoardConfig() {
	
		
	}

}
