package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;

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

	@Test
	public void testSelectTargets() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testMakeAccusations() {
		fail("Not yet implemented");
	}
	
		@Test
	public void testSuggestionCreating() {
		fail("Not yet implemented");
	}
		
	@Test
	public void testSuggestionDisproving() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSuggestionHandling() {
		fail("Not yet implemented");
	}
	


}
