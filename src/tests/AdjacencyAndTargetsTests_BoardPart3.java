package tests;

import org.junit.BeforeClass;

import clueGame.Board;

public class AdjacencyAndTargetsTests_BoardPart3 {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "Legend.txt");		
		board.initialize();
	}
	
	public void testAdjacenciesInsideRooms()
	{
		
	}
	public void testAdjacencyRoomExit(){
		
	}
	
}
