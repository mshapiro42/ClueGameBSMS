package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class ClueGameTests {
	private static Board board;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		board = Board.getInstance();
		board.setConfigFiles("ourData/clueLayout.xlsx", "ourData/Legend.txt");		
		board.initialize();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	@Test
	public void testRooms() {
	}
	@Test
	public void testBoardDimensions() {
	}
	@Test
	public void FourDoorDirections() {
	}
	@Test
	public void testNumberOfDoorways(){ // me
		{
			int nDoors = 0;
			for (int i=0; i<board.getNumRows(); i++)
				for (int j=0; j<board.getNumColumns(); j++) {
					BoardCell cell = board.getCellAt(i, j);
					if (cell.isDoorway())
						nDoors++;
				}
			Assert.assertEquals(16, nDoors);
		}
	
	}
	@Test
	public void testRoomInitials() { // me 
	}
	
}
