package clueGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import com.sun.xml.internal.bind.v2.runtime.Location;

import clueGame.Card.cardType;

import java.awt.Color;
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
	private Set<Player> people = new HashSet<Player>();
	private Set<Card> cards = new HashSet<Card>();
	private Set<Card> dealt = new HashSet<Card>();
	private Solution solution = new Solution();

	public Set<Player> getPeople() {
		return people;
	}

	private String[][] cellStrings = new String[100][100];
	private String legendString;
	private String layoutString;
	private String playersString;
	private String weaponsString;

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

	public void setConfigFiles(String layout, String legend, String players, String weapons) {
		layoutString = layout;
		legendString = legend;
		playersString = players;
		weaponsString = weapons;
	}

	public void initialize() {
		int i = 0; int j = 0;
		BufferedReader br = null;
		String line;
		try {
			br = new BufferedReader(new FileReader(layoutString));
			while((line = br.readLine()) != null && i < 100){
				String [] thisLine = line.split(",");

				for(String s: thisLine){
					grid[i][j] = new BoardCell(i,j);
					grid[i][j].setCol(j);
					grid[i][j].setRow(i);
					grid[i][j].setDoorString(s);
					j++;
				}
				j = 0;
				i++;
				//System.out.println("i is: " + i);
			}
			br.close();
			calcAdjacency();
			if (playersString != null){
				loadPlayersConfig();
				loadWeaponsConfig();
				dealCards();
				setPlayerLocations();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadConfigFormatException e){
			e.printStackTrace();
		}

	}

	private void calcAdjacency() {
		int rows = getNumRows();
		int cols = getNumColumns();

		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){	
				if((i==0)&&(j==0)){
					if(checkCell(grid[i][j]))
					{
						if(checkCell(grid[i][j+1])) {adjSet.add(grid[i][j+1]);}
						if(checkCell(grid[i+1][j])) {adjSet.add(grid[i+1][j]);}
					}

				}
				else if((i==0)&&(j==cols-1)){
					if(checkCell(grid[i][j])){
						if(checkCell(grid[i][j-1])) {adjSet.add(grid[i][j-1]);}
						if(checkCell(grid[i+1][j])) {adjSet.add(grid[i+1][j]);}
					}


				}
				else if((i==rows-1)&&(j==0)){
					if(checkCell(grid[i][j])){
						if(checkCell(grid[i][j+1])) {adjSet.add(grid[i][j+1]);}
						if(checkCell(grid[i-1][j])) {adjSet.add(grid[i-1][j]);}
					}

				}
				else if((i==rows-1)&&(j==cols-1)){
					if(checkCell(grid[i][j])){
						if(checkCell(grid[i][j-1])) {adjSet.add(grid[i][j-1]);}
						if(checkCell(grid[i-1][j])) {adjSet.add(grid[i-1][j]);}
					}

				}
				else if((i==0)&&(j!=0)&&(j!=cols-1)){
					if(checkCell(grid[i][j])) 
					{
						if(checkCell(grid[i][j-1])) {adjSet.add(grid[i][j-1]);}
						if(checkCell(grid[i+1][j])) {adjSet.add(grid[i+1][j]);}
						if(checkCell(grid[i][j+1])) {adjSet.add(grid[i][j+1]); } 
					}

				}
				else if((i!=0)&&(i!=rows-1)&&(j==0)){
					if(checkCell(grid[i][j])){
						if(checkCell(grid[i-1][j])) {adjSet.add(grid[i-1][j]);}
						if(checkCell(grid[i+1][j])) {adjSet.add(grid[i+1][j]);}
						if(checkCell(grid[i][j+1])) {adjSet.add(grid[i][j+1]);}
					}
				}
				else if((i==rows-1)&&(j!=0)&&(j!=cols-1)){
					if(checkCell(grid[i][j]))
					{
						if(checkCell(grid[i-1][j])) {adjSet.add(grid[i-1][j]);}
						if(checkCell(grid[i][j-1])) {adjSet.add(grid[i][j-1]);}
						if(checkCell(grid[i][j+1])) {adjSet.add(grid[i][j+1]);}
					}

				}
				else if((i!=0)&&(i!=rows-1)&&(j==cols -1)){
					if(checkCell(grid[i][j])){
						if(checkCell(grid[i-1][j-1])) {adjSet.add(grid[i-1][j]);}
						if(checkCell(grid[i][j-1])) {adjSet.add(grid[i][j-1]);}
						if(checkCell(grid[i+1][j-1])) {adjSet.add(grid[i+1][j]);}
					}
				}
				else if(grid[i][j].isDoorway()){

					if(grid[i][j].getDoorDirection()==DoorDirection.RIGHT){
						adjSet.add(grid[i][j+1]);


					}
					else if(grid[i][j].getDoorDirection()==DoorDirection.LEFT){

						adjSet.add(grid[i][j-1]);



					}
					else if(grid[i][j].getDoorDirection()==DoorDirection.UP){

						adjSet.add(grid[i-1][j]);

					}
					else if(grid[i][j].getDoorDirection()==DoorDirection.DOWN){

						adjSet.add(grid[i+1][j]);

					}
				}
				else if(grid[i][j+1].isDoorway()&&grid[i][j+1].getDoorDirection()==DoorDirection.LEFT)
				{

					adjSet.add(grid[i][j+1]);
					if(checkCell(grid[i-1][j])) {adjSet.add(grid[i-1][j]);}					
					if(checkCell(grid[i][j-1])) {adjSet.add(grid[i][j-1]);}
					if(checkCell(grid[i+1][j])) {adjSet.add(grid[i+1][j]);}

				}
				else if(grid[i][j-1].isDoorway()&&grid[i][j-1].getDoorDirection()==DoorDirection.RIGHT)
				{
					adjSet.add(grid[i][j-1]);
					if(checkCell(grid[i-1][j])) {adjSet.add(grid[i-1][j]);}					
					if(checkCell(grid[i][j+1])) {adjSet.add(grid[i][j+1]);}
					if(checkCell(grid[i+1][j])) {adjSet.add(grid[i+1][j]);}

				}
				else if(grid[i+1][j].isDoorway()&&grid[i+1][j].getDoorDirection()==DoorDirection.UP)
				{

					adjSet.add(grid[i+1][j]);
					if(checkCell(grid[i-1][j])) {adjSet.add(grid[i-1][j]);}					
					if(checkCell(grid[i][j-1])) {adjSet.add(grid[i][j-1]);}
					if(checkCell(grid[i][j+1])) {adjSet.add(grid[i][j+1]);}

				}
				else if(grid[i-1][j].isDoorway()&&grid[i-1][j].getDoorDirection()==DoorDirection.DOWN)
				{

					adjSet.add(grid[i-1][j]);					
					if(checkCell(grid[i][j-1])) {adjSet.add(grid[i][j-1]);}
					if(checkCell(grid[i][j+1])) {adjSet.add(grid[i][j+1]);}
					if(checkCell(grid[i+1][j])) {adjSet.add(grid[i+1][j]);}

				}
				else{

					if(checkCell(grid[i][j])){
						if(checkCell(grid[i-1][j])) {adjSet.add(grid[i-1][j]);}					
						if(checkCell(grid[i][j-1])) {adjSet.add(grid[i][j-1]);}
						if(checkCell(grid[i][j+1])) {adjSet.add(grid[i][j+1]);}
						if(checkCell(grid[i+1][j])) {adjSet.add(grid[i+1][j]);}


					}
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
		while((temp = in.nextLine()) != null ){
			if(temp == "") break;
			try{
				mapKey = temp.charAt(0);
				System.out.println(mapKey);
			} catch (NumberFormatException e1) {
				System.err.println("\nIncorrect format for " + mapKey + "Not a char");
			}
			int endi = temp.lastIndexOf(',');
			roomName = temp.substring(3, endi);
			Card c = new Card();
			c.setType(cardType.ROOM);
			c.setName(roomName);
			Boolean contains = false;
			for (Card t : cards){
				if (t.getName().equals(roomName)){
					contains = true;
				}
			}
			if (!contains){
				cards.add(c);
			}
			//System.out.println("roomName is: " + roomName);
			legendMap.put(mapKey, roomName);
			//System.out.println(legendMap);
			if(!in.hasNextLine()){
				break;
			}
		}
		in.close();
	}

	public Set<Card> getCards() {
		return cards;
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

	public void loadPlayersConfig() throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = new FileReader(playersString);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()){
			String line = in.nextLine();
			String[] arr = line.split(",");
			Player temp = new Player();
			temp.setColor(arr[0]);
			temp.setName(arr[1].trim());
			if (arr.length > 2) {
				throw new BadConfigFormatException("Players File has incorrect formatting");
			}
			people.add(temp);
			Card c = new Card();
			c.setType(cardType.PERSON);
			c.setName(arr[1].trim());
			cards.add(c);
		}
		in.close();
	}

	public void loadWeaponsConfig() throws FileNotFoundException{
		FileReader reader = new FileReader(weaponsString);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()){
			String line = in.nextLine();
			Card temp = new Card();
			temp.setType(cardType.WEAPON);
			temp.setName(line);
			cards.add(temp);
		}
		in.close();
	}

	public Set<BoardCell> getAdjList(int i, int j) {

		return adjMtx.get(grid[i][j]);
	}

	public void calcTargets(int i, int j, int k) {
		targets.clear(); // clears old targets 
		visited.clear(); 
		visited.add(grid[i][j]);
		BoardCell startCell = grid[i][j]; 
		findAllTargets(startCell, k);

	}

	private void findAllTargets(BoardCell startCell, int k) {
		Set<BoardCell> adjacent = adjMtx.get(startCell);
		for(BoardCell cell : adjacent){
			if (visited.contains(cell)){
				continue;
			}
			visited.add(cell); 
			if(k == 1 || (cell.isDoorway())){
				targets.add(cell);
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

		if(grid[current.getRow()][current.getCol()].getInitial()=='W'){
			checkValue = true;
		}



		return checkValue;
	}

	public void selectAnswer(){
		Set<Card> weapons = getWeapons();
		Set<Card> people = getPlayers();
		Set<Card> rooms = getRooms();
		Card weapon = getRandomCard(weapons);
		Card person = getRandomCard(people);
		Card room = getRandomCard(rooms);
		solution.setWeapon(weapon);
		solution.setPerson(person);
		solution.setRoom(room);
		dealt.add(weapon);
		dealt.add(person);
		dealt.add(room);
	}

	public Solution getSolution() {
		return solution;
	}

	public Card handleSuggestion(){
		return null;
	}

	public boolean checkAccusation(Solution accusation){
		return false;
	}

	public Set<Card> getWeapons() {
		Set<Card> weapons = new HashSet<Card>();
		for (Card c : cards){
			if (c.getType() == cardType.WEAPON){
				weapons.add(c);
			}
		}
		return weapons;
	}

	public Set<Card> getRooms() {
		Set<Card> rooms = new HashSet<Card>();
		for (Card c : cards){
			if (c.getType() == cardType.ROOM){
				rooms.add(c);
			}
		}
		return rooms;
	}


	public Set<Card> getPlayers() {
		Set<Card> people = new HashSet<Card>();
		for (Card c : cards){
			if (c.getType() == cardType.PERSON){
				people.add(c);
			}
		}
		return people;
	}

	public Card getRandomCard(Set<Card> cards){
		int size = cards.size();
		int item = new Random().nextInt(size);
		int i = 0;
		for (Card c : cards){
			if (i == item){
				return c;
			}
			i++;
		}
		return null;
	}

	public void dealCards(){
		Integer numDealt = 0;
		while (numDealt < cards.size()){
			for(Player p : people){
				Set<Card> myCards = p.getMyCards();
				Card temp = new Card();
				do{
					temp = getRandomCard(cards);
				} while (dealt.contains(temp));
				dealt.add(temp);
				myCards.add(temp);
				numDealt++;
				p.setMyCards(myCards);
				if (numDealt==cards.size()){
					break;
				}
			}
		}
	}

	public void setPlayerLocations() {
		Set<BoardCell> usedLocations = new HashSet<BoardCell>();
		BoardCell location;

		for (Player p : people) {
			do {
				location = getRandomLocation();

				//if not a walkway, or already a player there, get another random location
			} while((location.getInitial() != 'W') || usedLocations.contains(location));
			
			//if everything checks out, update player location and add to used locations
			p.setLocation(location);
			usedLocations.add(location);
		}
	}

	public BoardCell getRandomLocation() {
		int cols = getNumColumns();
		int rows = getNumRows();

		int randomCol = new Random().nextInt(cols);
		int randomRow = new Random().nextInt(rows);

		return grid[randomRow][randomCol];
	}
}

