package experiment;
import java.util.*;

import experiment.BoardCell;


public class IntBoard {

	public IntBoard(BoardCell[][] grid) {
		super();
		this.grid = grid;
	}


	private Map<BoardCell, HashSet<BoardCell>> adjMtx = new HashMap<BoardCell, HashSet<BoardCell>>();
	private Set<BoardCell> visited; // this is just the interface remember to initialize a new set when using
	private Set<BoardCell> targets;
	private HashSet<BoardCell> adjSet = new HashSet<BoardCell>();
	private BoardCell[][] grid;
	//constructor
	public void calcAdjacencies(){
		int rows = grid.length;
		int cols = grid[0].length;
		//adjSet = new HashSet<BoardCell>();

		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				//System.out.println("CURRENT CELL " +  grid[i][j]);	
				// goes through and checks all the boundaries 
				if((i==0)&&(j==0)){
					adjSet.add(grid[i][j+1]);
					adjSet.add(grid[i+1][j]);

				}
				else if((i==0)&&(j==cols-1)){
					adjSet.add(grid[i+1][j]);
					adjSet.add(grid[i][j-1]);

				}
				else if((i==rows-1)&&(j==0)){
					adjSet.add(grid[i-1][j]);
					adjSet.add(grid[i][j+1]);

				}
				else if((i==rows-1)&&(j==cols-1)){
					adjSet.add(grid[i][j-1]);
					adjSet.add(grid[i-1][j]);

				}
				else if((i==0)&&(j!=0)&&(j!=cols-1)){
					adjSet.add(grid[i][j-1]);
					adjSet.add(grid[i][j+1]);
					adjSet.add(grid[i+1][j]);
				}
				else if((i!=0)&&(i!=rows-1)&&(j==0)){
					adjSet.add(grid[i-1][j]);
					adjSet.add(grid[i+1][j]);
					adjSet.add(grid[i][j+1]);
				}
				else if((i==rows-1)&&(j!=0)&&(j!=cols-1)){
					adjSet.add(grid[i-1][j]);
					adjSet.add(grid[i][j+1]);
					adjSet.add(grid[i][j-1]);

				}
				else if((i!=0)&&(i!=rows-1)&&(j==cols -1)){
					adjSet.add(grid[i-1][j]);
					adjSet.add(grid[i+1][j]);
					adjSet.add(grid[i][j-1]);
				}
				else{
					adjSet.add(grid[i-1][j]);
					adjSet.add(grid[i+1][j]);
					adjSet.add(grid[i][j-1]);
					adjSet.add(grid[i][j+1]);

				}
				// System.out.println(adjMtx.get(grid[i][j]));
				adjMtx.put(grid[i][j], new HashSet<>(adjSet));
				adjSet.clear();
				//System.out.println(adjMtx.get(grid[0][0]));
			}
		
		}
		System.out.println(adjMtx.get(grid[0][0]));

	}

	//Calculates the adjacency list for EACH grid cell, 
	//stores in a map data structure. If you don't know what this is, go back to Preparation.


	public void calcTargets(BoardCell startCell, int pathLength){
		//		if(pathLength%2==0){
		//			//Calc  targets for even path lengths
		////			for(int i =0; i < pathLength;++i){
		////			visited.add(BoardCell[i][i]);
		////			}
		////			}
		//			
		//		if(pathLength%2!=0){
		//			//calc targets for odd path lengths
		//		}

	}

	public Set<BoardCell> getTargets(){
		return null;
	}

	public Set<BoardCell> getAdjList(BoardCell cell){

		return adjMtx.get(cell);
	}

	public BoardCell getCell(int i, int j) {

		return grid[i][j];
	}
}
