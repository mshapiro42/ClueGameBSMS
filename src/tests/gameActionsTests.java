package tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.Card.cardType;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class gameActionsTests {
	private static Board board;
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 21;

	@BeforeClass
	public static void setUpBeforeClass() throws BadConfigFormatException {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "Legend.txt", "Players.txt","Weapons.txt");
		board.initialize();
	}

	//Test computer player's ability to select target location
	//If no rooms in list, select randomly--------------------------------------------------
	@Test
	public void testTargetsNoRooms() {
		ComputerPlayer player = new ComputerPlayer();
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(3, 3, 2);
		boolean loc_2_4 = false;
		boolean loc_4_4 = false;
		boolean loc_3_5 = false;
		// Run the test a large number of times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(2, 4))
				loc_2_4 = true;
			else if (selected == board.getCellAt(4, 4))
				loc_4_4 = true;
			else if (selected == board.getCellAt(3, 5))
				loc_3_5 = true;
			else
				fail("Invalid target selected");
		}
		// Ensure each target was selected at least once
		assertTrue(loc_2_4);
		assertTrue(loc_4_4);
		assertTrue(loc_3_5);
	}

	//If room in list that was not just visited, must select it---------------------------
	@Test
	public void testTargetsNewRoom() {
		ComputerPlayer player = new ComputerPlayer();
		board.calcTargets(18, 15, 3);

		// Run the test a large number of times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			//There are two doors reachable from here, one of them better be the target
			//This works because even though lastVisitedLocation makes the most recent door
			//not a priority target, the other one gets chosen as the priority each time
			if ((selected != board.getCellAt(20, 16)) && (selected != board.getCellAt(17, 13))){
				fail("Invalid target selected");
			}
		}
	}

	//If room just visited is in list, each target (including room) selected randomly-----
	@Test
	public void testTargetsRoomVisited() {
		ComputerPlayer player = new ComputerPlayer();
		board.calcTargets(21, 15, 2);
		boolean loc_20_16 = false;
		boolean loc_20_14 = false;
		boolean loc_19_15 = false;
		// Run the test a large number of times
		for (int i=0; i<100; i++) {
			//This works because there is only one door,
			//and when it gets chosen, it updates its status to most recently chosen room
			//so next pick from same location will be random
			//but first time it runs it will always be that door
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(20, 16))
				loc_20_16 = true;
			else if (selected == board.getCellAt(20, 14))
				loc_20_14 = true;
			else if (selected == board.getCellAt(19, 15))
				loc_19_15 = true;
			else
				fail("Invalid target selected");
		}
		// Ensure each target was selected at least once
		assertTrue(loc_20_16);
		assertTrue(loc_20_14);
		assertTrue(loc_19_15);
	}


	//Next part-------------------------------------------------------------------------------
	//Test board's ability to check accusation for correctness
	@Test
	public void testMakeAccusations() {
		//load all the cards
		Set<Card> weapons = board.getWeapons();
		Set<Card> people = board.getPlayers();
		Set<Card> rooms = board.getRooms();

		Card murderWeapon = new Card();
		Card perpetrator = new Card();
		Card crimeScene = new Card();

		Card wrongWeapon = new Card();
		Card wrongPerson = new Card();
		Card wrongRoom = new Card();


		//The below loops are a bit complex, because the solution
		//consists of an actual CARD object, so you can't just go by the name alone

		//grab the ones you want
		//this is a preset solution
		for (Card w : weapons) {
			if (w.getName().equals("Bowling Ball")) {
				murderWeapon = w;
			}
		}
		for (Card p : people) {
			if (p.getName().equals("Murasaki Sensei")) {
				perpetrator = p;
			}
		}
		for (Card c : rooms) {
			if (c.getName().equals("Ping Pong Room")) {
				crimeScene = c;
			}
		}

		//the below are going to be the wrong answers
		for (Card w : weapons) {
			if (w.getName().equals("Keyboard")) {
				wrongWeapon = w;
			}
		}
		for (Card p : people) {
			if (p.getName().equals("Signora Rosso")) {
				wrongPerson = p;
			}
		}
		for (Card c : rooms) {
			if (c.getName().equals("Basement")) {
				wrongRoom = c;
			}
		}

		//Give the handpicked solution to the board
		Solution solution = new Solution();
		solution.setWeapon(murderWeapon);
		solution.setPerson(perpetrator);
		solution.setRoom(crimeScene);
		board.setSolution(solution);

		//Set accusations
		//Correct one
		Solution correctAccusation = new Solution();
		correctAccusation.setWeapon(murderWeapon);
		correctAccusation.setPerson(perpetrator);
		correctAccusation.setRoom(crimeScene);

		//Wrong weapon
		Solution wrongAccusation_w = new Solution();
		wrongAccusation_w.setWeapon(wrongWeapon);
		wrongAccusation_w.setPerson(perpetrator);
		wrongAccusation_w.setRoom(crimeScene);

		//Wrong person
		Solution wrongAccusation_p = new Solution();
		wrongAccusation_p.setWeapon(murderWeapon);
		wrongAccusation_p.setPerson(wrongPerson);
		wrongAccusation_p.setRoom(crimeScene);

		//Wrong room
		Solution wrongAccusation_c = new Solution();
		wrongAccusation_c.setWeapon(murderWeapon);
		wrongAccusation_c.setPerson(perpetrator);
		wrongAccusation_c.setRoom(wrongRoom);

		assertTrue(board.checkAccusation(correctAccusation));

		assertFalse(board.checkAccusation(wrongAccusation_w));
		assertFalse(board.checkAccusation(wrongAccusation_p));
		assertFalse(board.checkAccusation(wrongAccusation_c));
	}


	//Test computer player's ability to create a suggestion
	@Test
	public void testSuggestionCreating() {
		ComputerPlayer player = new ComputerPlayer();
		player.setLocation(board.getCellAt(6, 2));
		Solution sugg = player.makeSuggestion();
		Set<Card> cards = sugg.getCards();
		Card weapon = sugg.getWeapon();
		Card person = sugg.getPerson();
		Card room = sugg.getRoom();
		assertEquals(cards.size(),3); //suggestion contains 3 cards
		int w = 0;
		int p = 0;
		int r = 0;
		for (Card c : cards){
			if (c.getType() == cardType.WEAPON){
				w++;
			}
			else if(c.getType() == cardType.PERSON){
				p++;
			}
			else if(c.getType() == cardType.ROOM){
				r++;
			}
		}
		assertEquals(w,1); //Suggestion contains only one weapon
		assertEquals(p,1); //Suggestion contains only one person
		assertEquals(r,1); //Suggestion contains only one room

		Set<Card> seen = player.getSeenCards();
		Set<Card> unseen = player.getUnseenCards();
		for (Card c1 : seen){
			for (Card c2 : unseen){
				assertNotEquals(c1,c2); //seen and unseen cards have no overlap
			}
			for (Card c3 : cards){
				assertNotEquals(c1,c3); //all suggestion cards are not in seen
				assertTrue(unseen.contains(c3)); //all suggestion cards are unseen
			}
		}

		//Get the string of the players room location
		BoardCell loc = player.getLocation();
		char locInitial = loc.getInitial();
		Map<Character, String> legendMap = board.getLegendMap();
		String locRoom = legendMap.get(locInitial);
		//Get the string of the suggested room's Name
		String roomName = room.getName();
		assertTrue(locRoom.equals(roomName)); //Players location is suggested location

		Set<Card> unseenWeapons = new HashSet<Card>();
		Set<Card> unseenPeople = new HashSet<Card>();
		for (Card c : unseen){ //count number of seen weapons, people, rooms
			if (c.getType() == cardType.WEAPON){
				unseenWeapons.add(c);
			}
			if (c.getType() == cardType.PERSON){
				unseenPeople.add(c);
			}
		}
		if (unseenWeapons.size()==1){ //if only one weapon seen, suggest
			assertEquals(weapon, unseenWeapons);
		}
		else{
			boolean[] weapontrys = new boolean[unseenWeapons.size()]; //if more than one unseen, suggest random
			for (int i = 0; i <100; i++){
				Solution sugg2 = player.makeSuggestion();
				int j = 0;
				for (Card u : unseenWeapons){
					if (sugg2.getWeapon() == u){
						weapontrys[j] = true;
					}
					j++;
				}
			}
			for(int i =0; i < weapontrys.length; i++){
				assertTrue(weapontrys[i]);
			}

		}
		if (unseenPeople.size()==1){ //if only one person seen, suggest
			assertEquals(person, unseenPeople);
		}
		else {
			boolean[] persontrys = new boolean[unseenPeople.size()];//if more than one unseen, suggest random
			for (int i = 0; i <100; i++){
				Solution sugg2 = player.makeSuggestion();
				int j = 0;
				for (Card u : unseenPeople){
					if (sugg2.getPerson() == u){
						persontrys[j] = true;
					}
					j++;
				}
			}
			for(int i =0; i < persontrys.length; i++){
				assertTrue(persontrys[i]);
			}
		}
	}

	//Test any player's ability to disprove a suggestion
	@Test
	public void testSuggestionDisproving() {
		fail("Not yet implemented");
	}

	//Test board's ability to check suggestions and return feedback
	@Test
	public void testSuggestionHandling() {
		fail("Not yet implemented");
	}



}








