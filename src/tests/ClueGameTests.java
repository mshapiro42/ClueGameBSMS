package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

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
	public void testNumberOfDoorways(){
	}
	@Test
	public void testRoomInitials() {
	}
	
}
