package tests;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	@Before

	@Test
	public void testAdjacency0_0()
	{
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdjacency3_3(){
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdjacency1_3(){

	}

	@Test
	public void testAdjacency3_0(){

	}

	@Test
	public void testAdjacency1_1(){

	}

	@Test
	public void testAdjacency2_2(){

	}
	
		
	
	@Test
	public void testAdjacency(){}
	
	@Test
	public void testTarget0_1(){
		BoardCell cell = new BoardCell();
		IntBoard board = new IntBoard();
		board.getCell(0,0);
		board.calcTargets(board, 2);
		Set<BoardCell> t = board.getTargets();
		assertEquals(t.contains(board.getCell()))
	}
	@Test
	public void testTarget0_2(){
		BoardCell board = new BoardCell();
		board.getCell(0,0);
		board.calcTargets(board, 2);
	}
	@Test
	public void testTarget0_3(){
		BoardCell board = new BoardCell();
		board.getCell(0,0);
		board.calcTargets(board, 2);
	}
	@Test
	public void testTarget1_0(){
		BoardCell board = new BoardCell();
		board.getCell(0,0);
		board.calcTargets(board, 2);
	}
	@Test
	public void testTarget1_1(){
		BoardCell board = new BoardCell();
		board.getCell(0,0);
		board.calcTargets(board, 2);
	}
	@Test
	public void testTarget1_2(){
		BoardCell board = new BoardCell();
		board.getCell(0,0);
		board.calcTargets(board, 2);
	}
	@Test
	public void testTarget1_3(){
		BoardCell board = new BoardCell();
		board.getCell(0,0);
		board.calcTargets(board, 2);
	}
	

}

