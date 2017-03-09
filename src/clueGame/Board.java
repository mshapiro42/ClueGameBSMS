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
		calcAdjacency();
	}

	private void calcAdjacency() {
		int rows = grid.length;
		int cols = grid[0].length;
		System.out.println("Rows: " + rows + "Cols: " + cols);

		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){	
				// goes through and checks all the boundaries 
				if((i==0)&&(j==0)){
					if(checkCell(grid[i][j+1])){adjSet.add(grid[i][j+1]);}
					if(checkCell(grid[i+1][j])){adjSet.add(grid[i+1][j]);}

				}
				else if((i==0)&&(j==cols-1)){
					if(checkCell(grid[i][j-1])){adjSet.add(grid[i][j-1]);}
					if(checkCell(grid[i+1][j])){adjSet.add(grid[i+1][j]);}
					

				}
				else if((i==rows-1)&&(j==0)){
					if(checkCell(grid[i][j+1])){adjSet.add(grid[i][j+1]);}
					if(checkCell(grid[i-1][j])){adjSet.add(grid[i-1][j]);}

				}
				else if((i==rows-1)&&(j==cols-1)){
					if(checkCell(grid[i][j-1])){adjSet.add(grid[i][j-1]);}
					if(checkCell(grid[i-1][j])){adjSet.add(grid[i-1][j]);}

				}
				else if((i==0)&&(j!=0)&&(j!=cols-1)){
					if(checkCell(grid[i][j-1])){adjSet.add(grid[i][j-1]);}
					if(checkCell(grid[i+1][j])){adjSet.add(grid[i+1][j]);}
					if(checkCell(grid[i][j+1])){adjSet.add(grid[i][j+1]);}
					
				}
				else if((i!=0)&&(i!=rows-1)&&(j==0)){
					if(checkCell(grid[i-1][j])){adjSet.add(grid[i-1][j]);}
					if(checkCell(grid[i+1][j])){adjSet.add(grid[i+1][j]);}
					if(checkCell(grid[i][j+1])){adjSet.add(grid[i][j+1]);}
				}
				else if((i==rows-1)&&(j!=0)&&(j!=cols-1)){
					if(checkCell(grid[i-1][j])){adjSet.add(grid[i-1][j]);}
					if(checkCell(grid[i][j-1])){adjSet.add(grid[i][j-1]);}
					if(checkCell(grid[i][j+1])){adjSet.add(grid[i][j+1]);}

				}
				else if((i!=0)&&(i!=rows-1)&&(j==cols -1)){
					if(checkCell(grid[i-1][j])){adjSet.add(grid[i-1][j]);}
					if(checkCell(grid[i][j-1])){adjSet.add(grid[i][j-1]);}
					if(checkCell(grid[i+1][j])){adjSet.add(grid[i+1][j]);}
					
				}
				else{
					if(checkCell(grid[i-1][j])){adjSet.add(grid[i-1][j]);}
					if(checkCell(grid[i][j-1])){adjSet.add(grid[i][j-1]);}
					if(checkCell(grid[i][j+1])){adjSet.add(grid[i][j+1]);}
					if(checkCell(grid[i+1][j])){adjSet.add(grid[i+1][j]);}


				}
				adjMtx.put(grid[i][j], new HashSet<>(adjSet));
				adjSet.clear();
			}

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

	public void loadRoomConfig() throws BadConfigFormatException, IOException, FileNotFoundException {
		String[] configStrings = new String[1000];
		int i = 0;
		String line;
		BufferedReader in = new BufferedReader(new FileReader(layoutString));
		while((line = in.readLine()) != null){
			String [] thisLine = line.split(",");
			for(String s:thisLine){
				configStrings[i] = s;
				if(!legendMap.containsKey(s.charAt(0))){
					throw new BadConfigFormatException(layoutString);
				}
			}
		}
		in.close();

	}

	public void loadBoardConfig() throws IOException, BadConfigFormatException{
		int i = 0; int j = 0;
		BufferedReader br = null;
		String line;
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
			br.close();
		}

	}

	public Set<BoardCell> getAdjList(int i, int j) {
	
		return adjSet;
	}

	public void calcTargets(int i, int j, int k) {
		targets.clear(); // clears old targets 
	    visited.clear(); 
		visited.add(new BoardCell(i,j));
		BoardCell startCell = new BoardCell(i,j); 
		System.out.println(k);
		findAllTargets(startCell, k);
		
	}

	private void findAllTargets(BoardCell startCell, int k) {
		Set<BoardCell> adjacent = adjMtx.get(startCell);
		for(BoardCell cell : adjacent){
			if (visited.contains(cell)){
				continue;
			}
			visited.add(cell);
			if(k == 1){
				if(checkCell(cell)){targets.add(cell);}
			}
			else{
				findAllTargets(cell, k - 1);
			}
			visited.remove(cell);
		}
		
		
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public boolean checkCell(BoardCell current){
		boolean checkValue = false;
		System.out.println("Curretn col: " + current.getCol());
//		if(grid[current.getRow()][current.getCol()].getInitial()=='w'){
//			checkValue = true;
//		}
//		if(grid[current.getRow()][current.getCol()].isDoorway()){
//			checkValue = true;
//		}
//		if(grid[current.getRow()][current.getCol()].isDoorway()&&visited.contains(current)){
//			checkValue = false;
//		}
		
		return checkValue;
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
