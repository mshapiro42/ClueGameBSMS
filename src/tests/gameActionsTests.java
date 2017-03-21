package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;

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


	
	
	
	/*
	//Test board's ability to check accusation for correctness
	@Test
	public void testMakeAccusations() {
		//fail("Not yet implemented");
	}

	//Test computer player's ability to create a suggestion
	@Test
	public void testSuggestionCreating() {
		//fail("Not yet implemented");
	}

	//Test any player's ability to disprove a suggestion
	@Test
	public void testSuggestionDisproving() {
		//fail("Not yet implemented");
	}

	//Test board's ability to check suggestions and return feedback
	@Test
	public void testSuggestionHandling() {
		//fail("Not yet implemented");
	}
 */

	
}








