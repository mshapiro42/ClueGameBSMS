package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class ClueGameTests {
	private static Board board;
	public static final int LEGEND_SIZE = 11;;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 21;

	@BeforeClass
	public static void setUpBeforeClass() throws BadConfigFormatException {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "Legend.txt");
		board.initialize();
	}

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testRooms() {
		// Get the map of initial => room 
		Map<Character, String> legend = board.getLegendMap();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());
		// To ensure data is correctly loaded, test retrieving a few rooms 
		// from the hash, including the first and last in the file and a few others
		assertEquals("Aviary", legend.get('A'));
		assertEquals("Trophy Room", legend.get('T'));
		assertEquals("Entry Way", legend.get('E'));
		assertEquals("Red Room", legend.get('R'));
		assertEquals("Walkway", legend.get('W'));
	}
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(5, 4);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(6, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(16, 8);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(10, 15);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = board.getCellAt(14, 14);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(8, 7);
		assertFalse(cell.isDoorway());	
	}
	@Test
	public void testNumberOfDoorways(){
		int numDoors = 0;

		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		assertEquals(20, numDoors);
	}
	@Test
	public void testRoomInitials() {
		// Test first cell in room
		assertEquals('A', board.getCellAt(0, 0).getInitial());
		assertEquals('S', board.getCellAt(3, 8).getInitial());
		assertEquals('E', board.getCellAt(9, 0).getInitial());
		// Test last cell in room
		assertEquals('M', board.getCellAt(11, 19).getInitial());
		assertEquals('T', board.getCellAt(21, 0).getInitial());
		// Test a walkway
		assertEquals('W', board.getCellAt(8, 0).getInitial());
		// Test the closet
		assertEquals('X', board.getCellAt(10,10).getInitial());
	}

}
