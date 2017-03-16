package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
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
		assertEquals(6, people.size());
		for (Player p: people) {
			if (p.getName().equals("Signora Rosso")){
				assertEquals(Color.red, p.getColor());
			}
			if (p.getName().equals("Seniotita Amarillo")){
				assertEquals(Color.yellow, p.getColor());
			}
			if (p.getName().equals("Murasaki Sensei")){
				assertEquals(Color.magenta, p.getColor());
			}
		}	
	}
	

}
