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
	
	@Test
	public void testPeople() {
		Set<Player> people = board.getPeople();
		Set<String> names = new HashSet<String>();
		assertEquals(6, people.size());
		for (Player p: people) {
			names.add(p.getName());
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
		assertTrue(names.contains("Signora Rosso"));
		assertTrue(names.contains("Seniorita Amarillo"));
		assertTrue(names.contains("Murasaki Sensei"));
	}
	
	@Test
	public void testWeapons() {
		Set<Card> weapons = board.getWeapons();
		Set<String> weaponNames = new HashSet<String>();
		assertEquals(9, weapons.size());
		for(Card c: weapons){
			weaponNames.add(c.getName());
		}
		assertTrue(weaponNames.contains("Harpoon"));
		assertTrue(weaponNames.contains("Arrow"));
		assertTrue(weaponNames.contains("Bowling Ball"));
		assertTrue(weaponNames.contains("Keyboard"));
	}
	
	@Test
	public void testCards() {
		Set<Card> cards = board.getCards();
		assertEquals(26,cards.size());
		Set<String> people = new HashSet<String>();
		Set<String> weapons = new HashSet<String>();
		Set<String> rooms = new HashSet<String>();
		for(Card c: cards){
			String temp = c.getName();
			cardType temp2 = c.getType();
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
		assertEquals(9, weapons.size());
		assertEquals(6, people.size());
		assertEquals(11, rooms.size());
		assertTrue(people.contains("Signora Rosso"));
		assertTrue(weapons.contains("Axe"));
		assertTrue(rooms.contains("Entry Way"));
		
	}

	@Test
	public void testDeal(){
		Set<Player> people = board.getPeople();
		Set<Card> cards = board.getCards();
		Map<Player, Set<Card>> playersCards = new HashMap<Player, Set<Card>>();
		Set<Integer> numCards = new HashSet<Integer>();
		int numDealt = 0;
		for (Player p : people){
			Set<Card> temp = p.getMyCards();
			playersCards.put(p, temp);
			numDealt += temp.size();
			numCards.add(temp.size());
			System.out.println(temp.size());
			//			System.out.println(average - temp.size());
			//assertEquals(average, temp.size(),2);
		}
		Integer sum = 0;
		for (Integer i : numCards){
			sum += i;
		}
		Double average = (double) (sum/people.size());
		System.out.println(average);
		assertEquals(cards.size(), numDealt);
		for(Player p1 : people){
			for (Player p2 : people){
				if (p1 ==p2){
					continue;
				}
				Set<Card> p1Cards = p1.getMyCards();
				Set<Card> p2Cards = p2.getMyCards();
				Set<Card> solCards = board.getSolution().getCards();
				
				for(Card c : p1Cards){
					assertFalse(p2Cards.contains(c));
					assertFalse(solCards.contains(c));
				}
			}
		}
		
	}
	
}
