package clueGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class Board{

	private Map<BoardCell, HashSet<BoardCell>> adjMtx = new HashMap<BoardCell, HashSet<BoardCell>>();
	private Set<BoardCell> visited = new HashSet<BoardCell>(); // this is just the interface remember to initialize a new set when using
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> adjSet = new HashSet<BoardCell>();
	private Map<Character, String> legendMap = new HashMap<Character, String>();
	private BoardCell[][] grid = new BoardCell[100][100];

	private String[][] cellStrings = new String[100][100];
	private String legendString;
	private String layoutString;
	
	public String[][] getCellStrings(){
		return cellStrings;
	}

	public Board() {
		super();
	}

	public static Board getInstance() {
		Board board = new Board();
		//board.loadCellStrings();
		return board;
	}

//	public String getCellString(int row, int col){
//		return cellStrings[row][col];
//	}


	public void setConfigFiles(String layout, String legend) {
		layoutString = layout;
		legendString = legend;
	}
	public void initialize() {
		int i = 0; int j = 0;
		BufferedReader br = null;
		String line;
		try {
			br = new BufferedReader(new FileReader(layoutString));
			while((line = br.readLine()) != null && i < 100){
				String [] thisLine = line.split(",");
				//System.out.println("thisLine string: " + thisLine.toString());
				for(String s: thisLine){
					grid[i][j] = new BoardCell(i,j);
					grid[i][j].setCol(i);
					grid[i][j].setRow(j);
					grid[i][j].setDoorString(s);
					j++;
				}
				j = 0;
				i++;
				//System.out.println("i is: " + i);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public int getNumRows() {
		Map<Character,String> testLegend = getLegend();
		int rowCounter = 0;
		BufferedReader br = null;
		String line = null;
		try {
			br = new BufferedReader(new FileReader(layoutString));
			while((line = br.readLine()) != null){
				if(testLegend.containsKey(line.charAt(0))){ 
					rowCounter++;
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		//System.out.println("row counter = " + rowCounter);
		return rowCounter;
	}

	public int getNumColumns(){
		Map<Character,String> testLegend = getLegend();
		int colCounter = 0;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(layoutString));
			String line = br.readLine();
			String [] numColumns = line.split(",");
			for(String s : numColumns){
				if(testLegend.containsKey((s.toCharArray()[0]))){
					colCounter++;
				}
			}
			//System.out.println("col counter = " + colCounter);
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return colCounter;
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
			reader = new FileReader(legendString);
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
			int endi = temp.lastIndexOf(',');
			roomName = temp.substring(3, endi);
			//System.out.println("roomName is: " + roomName);
			legendMap.put(mapKey, roomName);
			//System.out.println(legendMap);
		}
		in.close();
	}

	public clueGame.BoardCell getCellAt(int i, int j) {

		return grid[i][j];
	}

	public void loadRoomConfig() {


	}

	public void loadBoardConfig(){

	}

//	public void loadCellStrings() {
//		int i = 0; int j = 0;
//		BufferedReader br = null;
//		String line;
//		try {
//			br = new BufferedReader(new FileReader(layoutString));
//			while((line = br.readLine()) != null && i < 100){
//				String [] thisLine = line.split(",");
//				for(String s: thisLine){
//					cellStrings[i][j] = s;
//					//System.out.println("cellStrings is: " + cellStrings[i][j]);
//					j++;
//				}
//				j = 0;
//				i++;
//				//System.out.println("i is: " + i);
//			}
//			br.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//	}


}
