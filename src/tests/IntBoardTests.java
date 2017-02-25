package tests;

import org.junit.Before;
import org.junit.Test;

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
	public void testTarget(){
		
	}
	
	
>>>>>>> e8f2b99d3b202f26052d42fb551edc998122b17b
}

