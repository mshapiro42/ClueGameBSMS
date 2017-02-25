package experiment;
import java.util.*;

import experiment.BoardCell;


public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	//constructor
	private void calcAdjacencies(){
		
	}
	
	private void calcTargets(BoardCell startCell, int pathLength){
		
	}
	
	private Set<BoardCell> getTargets(){
		return null;
	}
	
	private Set<BoardCell> getAdjList(){
		return null;
	}
}
