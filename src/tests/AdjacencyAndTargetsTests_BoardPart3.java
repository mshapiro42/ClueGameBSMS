package tests;

import java.util.Set;


import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

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
	@Test
	public void testAdjacenciesOnlyWalkways(){
		//This tests the first requirement, only walkways are adjacents
		Set<BoardCell> testList = board.getAdjList(6, 12);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 13)));
		assertTrue(testList.contains(board.getCellAt(5, 12)));
		assertTrue(testList.contains(board.getCellAt(6, 11)));
		assertTrue(testList.contains(board.getCellAt(7, 12)));

	}
	@Test
	public void testAdjacenciesInsideRooms(){
		//this tests the second requirement, for a cell within a room
		Set<BoardCell> testList = board.getAdjList(1, 9);
		assertEquals(0, testList.size());

	}
	@Test
	public void testAdjacenciesAllCorners(){
		//this tests the third requirement, each edge, i hope corners are also considered edges
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());

	
		testList = board.getAdjList(0, 20);
		assertEquals(0, testList.size());

		
		testList = board.getAdjList(21, 20);
		assertEquals(0, testList.size());

		
		testList = board.getAdjList(21, 0);
		assertEquals(0, testList.size());

	}
	

	
	@Test
	//this tests the fourth requirement, being beside a room that is not a doorway
	public void testAdjacenciesForNotDoorways(){
		Set<BoardCell> testList = board.getAdjList(14, 2);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 1)));
		assertTrue(testList.contains(board.getCellAt(13, 2)));
		assertTrue(testList.contains(board.getCellAt(14, 3)));
		
		testList = board.getAdjList(4, 18);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(3, 18)));
		assertTrue(testList.contains(board.getCellAt(4, 17)));
		assertTrue(testList.contains(board.getCellAt(5, 18)));
	}
	@Test
	public void testAdjacenciesNeededDirection(){
		//this tests the fifth requirement, being next to a doorway with the needed direction
		Set<BoardCell> testList = board.getAdjList(10, 6);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(9, 6)));
		assertTrue(testList.contains(board.getCellAt(11, 6)));
		assertTrue(testList.contains(board.getCellAt(10, 5)));
		
		testList = board.getAdjList(5, 9);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(4, 9)));
		assertTrue(testList.contains(board.getCellAt(6, 9)));
		assertTrue(testList.contains(board.getCellAt(5, 8)));
		assertTrue(testList.contains(board.getCellAt(6, 9)));
		
		testList = board.getAdjList(9, 15);
		System.out.println(testList);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 15)));
		assertTrue(testList.contains(board.getCellAt(10, 15)));
		assertTrue(testList.contains(board.getCellAt(9, 14)));
		assertTrue(testList.contains(board.getCellAt(9, 16)));
		
		testList = board.getAdjList(14, 17);
		System.out.println(testList);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 17)));
		assertTrue(testList.contains(board.getCellAt(15, 17)));
		assertTrue(testList.contains(board.getCellAt(14, 18)));
		assertTrue(testList.contains(board.getCellAt(14, 16)));
	}
	@Test
	public void testAdjacenciesAreDoors(){
		//this tests the sixth requirement, being a doorway
		Set<BoardCell> testList = board.getAdjList(6, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(7, 2)));
		
		testList = board.getAdjList(17, 13);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(17, 14)));
	}
	@Test
	public void testTargetsAlongWalkways(){
		//this tests the seventh requirement, targets along walkways
		board.calcTargets(5, 6, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 6)));
		assertTrue(targets.contains(board.getCellAt(6, 6)));	
		assertTrue(targets.contains(board.getCellAt(5, 5)));
		assertTrue(targets.contains(board.getCellAt(5, 7)));
		
		board.calcTargets(19, 17, 2);
		targets= board.getTargets();
		System.out.println(targets);
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 16)));	
		assertTrue(targets.contains(board.getCellAt(19, 15)));	
		
		board.calcTargets(14, 11, 3);
		targets= board.getTargets();
		System.out.println(targets);
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 13)));	
		assertTrue(targets.contains(board.getCellAt(13, 13)));
		assertTrue(targets.contains(board.getCellAt(14, 12)));
		assertTrue(targets.contains(board.getCellAt(14, 10)));
		assertTrue(targets.contains(board.getCellAt(13, 9)));
		assertTrue(targets.contains(board.getCellAt(13, 11)));
		assertTrue(targets.contains(board.getCellAt(14, 14)));
		assertTrue(targets.contains(board.getCellAt(14, 8)));
		
		board.calcTargets(13, 0, 5);
		targets= board.getTargets();
		System.out.println(targets);
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 1)));	
		assertTrue(targets.contains(board.getCellAt(14, 0)));
		assertTrue(targets.contains(board.getCellAt(14, 2)));
		assertTrue(targets.contains(board.getCellAt(13, 3)));
		assertTrue(targets.contains(board.getCellAt(15, 3)));
		assertTrue(targets.contains(board.getCellAt(14, 4)));
	}
	@Test
	public void testTargetsAllowEnter(){
		//this tests the eighth requirement, allowing the user to enter a room
		board.calcTargets(14, 16, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 17)));
		assertTrue(targets.contains(board.getCellAt(14, 15)));
		assertTrue(targets.contains(board.getCellAt(13, 16)));
		assertTrue(targets.contains(board.getCellAt(15, 16)));
		
		board.calcTargets(17, 7, 1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 8)));
		assertTrue(targets.contains(board.getCellAt(18, 7)));	
		assertTrue(targets.contains(board.getCellAt(16, 7)));
		assertTrue(targets.contains(board.getCellAt(17, 6)));
	}
	@Test
	public void testTargetsWhenLeavingRoom(){
		//this tests the 9th requirement, calculating targets when leaving a room
		board.calcTargets(6, 2, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 2)));

		
		board.calcTargets(11, 5, 3);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 6)));
		assertTrue(targets.contains(board.getCellAt(9, 6)));	
		assertTrue(targets.contains(board.getCellAt(10, 5)));

	}
}
