package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.Card.cardType;
import clueGame.DoorDirection;
import clueGame.Player;

public class gameSetupTests {
	private static Board board;
	public static final int LEGEND_SIZE = 11;;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 21;
	
	@BeforeClass
	public static void setUpBeforeClass() throws BadConfigFormatException {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "Legend.txt", "Players.txt","Weapons.txt");
		board.initialize();
	}
	
	//Test loading of players onto the board
	@Test
	public void testPeople() {
		Set<Player> people = board.getPeople();
		Set<String> names = new HashSet<String>();
		//Make sure array of people is proper size
		assertEquals(6, people.size());
		//Loop through people
		for (Player p: people) {
			names.add(p.getName());
			//Make sure name matches color
			if (p.getName().equals("Signora Rosso")){
				assertEquals(Color.red, p.getColor());
			}
			if (p.getName().equals("Seniorita Amarillo")){
				assertEquals(Color.yellow, p.getColor());
			}
			if (p.getName().equals("Murasaki Sensei")){
				assertEquals(Color.magenta, p.getColor());
			}
		}
		//Make sure names themselves were transcribed correctly
		assertTrue(names.contains("Signora Rosso"));
		assertTrue(names.contains("Seniorita Amarillo"));
		assertTrue(names.contains("Murasaki Sensei"));
		
		//Loop through people
		for (Player p: people) {
			//Make sure location is on a walkway
			BoardCell cell = p.getLocation();
			char initial = cell.getInitial();
			assertEquals(initial, 'W');
			
			//Make sure location is in bounds
			assertTrue(cell.getRow() >= 0);
			assertTrue(cell.getCol() >= 0);
			assertTrue(cell.getRow() < NUM_ROWS);
			assertTrue(cell.getCol() < NUM_COLUMNS);
			
			//Loop through every pair of people
			for (Player p2: people) {
				//except when it pairs with itself
				if (p == p2){
					continue;
				}
				
				//get current location
				BoardCell cell2 = p2.getLocation();
				
				//check to make sure those two people
				//are not on the same location
				assertTrue((cell.getRow() != cell2.getRow()) || (cell.getCol() != cell2.getCol()));
			}
		}
	}
	
	//Test loading of weapons
	@Test
	public void testWeapons() {
		Set<Card> weapons = board.getWeapons();
		Set<String> weaponNames = new HashSet<String>();
		//Make sure array is of proper size
		assertEquals(9, weapons.size());
		//Loop through weapons and make a set out of the names 
		for(Card c: weapons){
			weaponNames.add(c.getName());
		}
		//Make sure known names are in set
		assertTrue(weaponNames.contains("Harpoon"));
		assertTrue(weaponNames.contains("Arrow"));
		assertTrue(weaponNames.contains("Bowling Ball"));
		assertTrue(weaponNames.contains("Keyboard"));
	}
	
	//Test the loading of cards
	@Test
	public void testCards() {
		Set<Card> cards = board.getCards();
		//Test that the set of cards is of proper size
		assertEquals(26,cards.size());
		Set<String> people = new HashSet<String>();
		Set<String> weapons = new HashSet<String>();
		Set<String> rooms = new HashSet<String>();
		//Loop through cards
		for(Card c: cards){
			String temp = c.getName();
			cardType temp2 = c.getType();
			//Sort based on category for the below tests
			switch(temp2){
			case PERSON:
				people.add(temp);
				break;
			case WEAPON:
				weapons.add(temp);
				break;
			case ROOM:
				rooms.add(temp);
			}
		}
		//Make sure the categories have the right distribution of members
		assertEquals(9, weapons.size());
		assertEquals(6, people.size());
		assertEquals(11, rooms.size());
		//Check one of each time, make sure it contains a known entry
		assertTrue(people.contains("Signora Rosso"));
		assertTrue(weapons.contains("Axe"));
		assertTrue(rooms.contains("Entry Way"));
	}

	//Test dealing of cards to players
	@Test
	public void testDeal(){
		Set<Player> people = board.getPeople();
		Set<Card> cards = board.getCards();
		Map<Player, Set<Card>> playersCards = new HashMap<Player, Set<Card>>();
		Set<Integer> numCards = new HashSet<Integer>();
		int numDealt = 0;
		//Loop through players
		for (Player p : people){
			//Count number of cards they have, and print results
			Set<Card> temp = p.getMyCards();
			playersCards.put(p, temp);
			numDealt += temp.size();
			numCards.add(temp.size());
			System.out.println(p.getName() + " has " + temp.size() + " cards.");
		}
		//Make sure the number dealt is correct
		assertEquals(cards.size(), numDealt);
		//Compute expected average
		Double average = (double) (numDealt/people.size());
		System.out.println("The average number of cards is " + average);
		Set<Card> solCards = board.getSolution().getCards();
		//Make sure the solutions dealt to the board (not to a player) is the correct size
		assertEquals(3,solCards.size());
		//Loop through players
		for(Player p1 : people){
			Set<Card> p1Cards = p1.getMyCards();
			//Pair each player against all the others
			for (Player p2 : people){
				//Exclude self-matching
				if (p1 ==p2){
					continue;
				}
				Set<Card> p2Cards = p2.getMyCards();
				//Loop through all the first player's cards
				for(Card c : p1Cards){
					//Make sure it doesn't match any of the other player's cards
					assertFalse(p2Cards.contains(c));
					//Make sure the solution wasn't dealt to any player
					assertFalse(solCards.contains(c));
				}
			}
			//Be sure the number of cards dealt to any player does not deviate
			//from the average
			assertEquals(average, p1Cards.size(),1);
		}
	}
}
