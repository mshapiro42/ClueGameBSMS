package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class ClueGameTests {
	private static Board board;
	private int LEGEND_SIZE = 11;;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 21;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		board = Board.getInstance();
		board.setConfigFiles("ourData/clueLayout.xlsx", "ourData/Legend.txt");		
		board.initialize();

	}

	//	@Test
	//	public void test() {
	//		fail("Not yet implemented");
	//	}

	@Test
	public void testRooms() {
		// Get the map of initial => room 
		Map<Character, String> legend = board.getLegend();
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
		BoardCell room = board.getCellAt(4, 5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(5, 3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(15, 9);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(14, 11);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = board.getCellAt(14, 14);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(0, 6);
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
		Assert.assertEquals(16, numDoors);
	}
	@Test
	public void testRoomInitials() {
		// Test first cell in room
		assertEquals('A', board.getCellAt(0, 0).getInitial());
		assertEquals('S', board.getCellAt(3, 8).getInitial());
		assertEquals('B', board.getCellAt(9, 0).getInitial());
		// Test last cell in room
		assertEquals('M', board.getCellAt(11, 19).getInitial());
		assertEquals('T', board.getCellAt(21, 0).getInitial());
		// Test a walkway
		assertEquals('R', board.getCellAt(0, 9).getInitial());
		// Test the closet
		assertEquals('P', board.getCellAt(21,20).getInitial());
	}

}
