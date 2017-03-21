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
import clueGame.HumanPlayer;
import clueGame.Player;
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
		ComputerPlayer player = new ComputerPlayer(board);
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
		ComputerPlayer player = new ComputerPlayer(board);
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
		ComputerPlayer player = new ComputerPlayer(board);
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
		ComputerPlayer player = new ComputerPlayer(board);
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
		ComputerPlayer player = new ComputerPlayer(board);

		//Set computer player's cards for disproving suggestion
		Set<Card> myCards = new HashSet<Card>();
		myCards.add(board.findCard("Bowling Ball"));
		myCards.add(board.findCard("Signora Rosso"));
		myCards.add(board.findCard("Aviary"));
		player.setMyCards(myCards);

		//Set suggestion cards
		Set<Card> weapons = board.getWeapons();
		Set<Card> people = board.getPlayers();
		Set<Card> rooms = board.getRooms();
		//Suggestion Cards
		Card murderWeapon = new Card();
		Card perpetrator = new Card();
		Card crimeScene = new Card();
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
		//Set suggestion
		Solution suggestion = new Solution();
		suggestion.setWeapon(murderWeapon);
		suggestion.setPerson(perpetrator);
		suggestion.setRoom(crimeScene);

		/*    	
	    (2) If player has only one matching card it should be returned
	    (2) If players has >1 matching card, returned card should be chosen randomly
	    (2) If player has no matching cards, null is returned*/

		//In this case, bowling ball is the only one that matches
		//so it should be returned
		assertEquals(player.disproveSuggestion(suggestion), board.findCard("Bowling Ball"));

		//Make another card in the suggestion match what player has in hand
		suggestion.setPerson(board.findCard("Signora Rosso"));
		//Make sure disprove suggestion chooses randomly between Bowling ball and signora rosso to return
		boolean showedBowling = false;
		boolean showedSignora = false;
		for (int i = 0; i < 100; i++) {
			if (player.disproveSuggestion(suggestion) == board.findCard("Bowling Ball")){
				showedBowling = true;
			}
			if (player.disproveSuggestion(suggestion) == board.findCard("Signora Rosso")){
				showedSignora = true;
			} 
		}

		assertTrue(showedBowling);
		assertTrue(showedSignora);

		//Make sure if player has no matching cards, null is returned
		suggestion.setPerson(board.findCard("Murasaki Sensei"));
		suggestion.setWeapon(board.findCard("Harpoon"));

		assertEquals(player.disproveSuggestion(suggestion), null);
	}

	//Test board's ability to check suggestions and return feedback
	@Test
	public void testSuggestionHandling() {
		Player p1 = new ComputerPlayer(board); //create new player with certain name and cards
		Set<Card> p1Cards = new HashSet<Card>();
		p1Cards.add(board.findCard("Harpoon"));
		p1Cards.add(board.findCard("Aviary"));
		p1Cards.add(board.findCard("Signora Rosso"));
		p1.setName("Herr Grun");
		p1.setMyCards(p1Cards);

		Player p2 = new ComputerPlayer(board); //create new player with certain name and cards
		Set<Card> p2Cards = new HashSet<Card>();
		p2Cards.add(board.findCard("Basement"));
		p2Cards.add(board.findCard("Bird"));
		p2Cards.add(board.findCard("Seniorita Amarillo"));
		p2.setName("Monsier Bleu");
		p2.setMyCards(p2Cards);

		Player h1 = new HumanPlayer(board); //create new human player with certain name and cards
		Set<Card> h1Cards = new HashSet<Card>();
		h1Cards.add(board.findCard("Red Room"));
		h1Cards.add(board.findCard("Arrow"));
		h1Cards.add(board.findCard("Herr Grun"));
		h1.setName("Senhora Rosa");
		h1.setMyCards(h1Cards);

		Set<Player> people = new HashSet<Player>();
		people.add(p1);
		people.add(p2);
		people.add(h1);
		
		board.setPeople(people);

		Solution sugg = new Solution(); //create new solution with certain values
		sugg.setWeapon(board.findCard("Axe"));
		sugg.setPerson(board.findCard("Monsier Bleu"));
		sugg.setRoom(board.findCard("Workshop"));

		//If no one can disprove, return null
		assertEquals(board.handleSuggestion(sugg,p1),null);

		//if only accusing can disprove, return null
		sugg.setWeapon(board.findCard("Harpoon"));
		assertEquals(board.handleSuggestion(sugg,p1),null);

		//if only human, not accusing, can disprove, show card
		sugg.setWeapon(board.findCard("Arrow"));
		assertEquals(board.handleSuggestion(sugg,p1),board.findCard("Arrow"));

		//if only human, accusing, can disprove, return null;
		assertEquals(board.handleSuggestion(sugg, h1),null);

		//New suggestion that both p1 and p1 can disprove
		sugg.setWeapon(board.findCard("Harpoon"));
		sugg.setPerson(board.findCard("Seniorita Amarillo"));
		Card p1Disprove  = p1.disproveSuggestion(sugg);
		Card p2Disprove = p2.disproveSuggestion(sugg);
		assertEquals(p1Disprove, board.findCard("Harpoon")); //p1 should disprove with Harpoon Card
		assertEquals(p2Disprove, board.findCard("Seniorita Amarillo"));//p2 should disprove with Seniorita Amarillo card
		assertEquals(board.handleSuggestion(sugg,p1), board.findCard("Harpoon")); //board should disprove with p1


		//New suggestion that both p2 and h1 can disprove
		sugg.setWeapon(board.findCard("Bird"));
		sugg.setPerson(board.findCard("Herr Grun"));
		Card h1Disprove = h1.disproveSuggestion(sugg);
		p2Disprove  = p2.disproveSuggestion(sugg);
		assertEquals(p2Disprove, board.findCard("Bird")); //p2 should disprove with Bird card
		assertEquals(h1Disprove, board.findCard("Herr Grun"));//h1 should disprove with Herr Grun card
		assertEquals(board.handleSuggestion(sugg,p1), board.findCard("Bird")); //board should disprove with p2







	}



}








