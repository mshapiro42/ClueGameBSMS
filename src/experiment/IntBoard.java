package experiment;
import java.util.*;

import experiment.BoardCell;


public class IntBoard {

	public IntBoard(BoardCell[][] grid) {
		super();
		this.grid = grid;
	}


	private Map<BoardCell, HashSet<BoardCell>> adjMtx = new HashMap<BoardCell, HashSet<BoardCell>>();
	private Set<BoardCell> visited = new HashSet<BoardCell>(); // this is just the interface remember to initialize a new set when using
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> adjSet = new HashSet<BoardCell>();
	private BoardCell[][] grid;

	public void calcAdjacencies(){
		int rows = grid.length;
		int cols = grid[0].length;


		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){	
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
				adjMtx.put(grid[i][j], new HashSet<>(adjSet));
				adjSet.clear();
			}

		}

	}



/*
	public void calcTargets(BoardCell startCell, int pathLength){
		targets.clear(); // clears old targets 
		int rows = grid.length;
		int cols = grid[0].length;
		int startRows = startCell.getRow();
		//Set<BoardCell> visited = new HashSet<BoardCell>();

		int startCols = startCell.getCol();
		System.out.println(pathLength);




		for(int k = 0; k<=pathLength; ++k){
			for(int i=startRows;i<rows;++i){
				for(int j=startCols;j<cols;j++){

					

						if((startRows==0)&&(startCols==0)){ 
							targets.add(grid[startRows+pathLength-k][startCols+k]);

						}
						else if((startRows==rows-1)&&(startCols==0)){ // bottom left 3_0
							targets.add(grid[startRows+pathLength-k][startCols+k]);

						}
						else if((startRows==rows-1)&&(startCols==cols-1)){ // bottom right 
							targets.add(grid[startRows+pathLength-k][startCols+k]);

						}
						else if((startRows==0)&&(startCols!=0)&&(startCols!=cols-1)){
							targets.add(grid[startRows+pathLength-k][startCols+k]);
						}
						else if((startRows!=0)&&(startRows!=rows-1)&&(startCols==0)){
							targets.add(grid[startRows+pathLength-k][startCols+k]);
						}
						else if((startRows==rows-1)&&(startCols!=0)&&(startCols!=cols-1)){
							targets.add(grid[startRows+pathLength-k][startCols+k]);

						}
						else if((startRows!=0)&&(startRows!=rows-1)&&(startCols==cols -1)){
							targets.add(grid[startRows+pathLength-k][startCols+k]);
						}
						else{
							targets.add(grid[startRows+pathLength-k][startCols+k]);
							targets.add(grid[startRows-pathLength-k][startCols-k]);
						}


						}
					}
				}
			}
			*/
	public void calcTargets(BoardCell startCell, int pathLength){
		targets.clear(); // clears old targets 
		visited.clear();
		visited.add(startCell);
		System.out.println(pathLength);
		findAllTargets(startCell, pathLength);
		
	}
	
	public void findAllTargets(BoardCell startCell, int pathLength){
		Set<BoardCell> adjacent = adjMtx.get(startCell);
		for(BoardCell cell : adjacent){
			if (visited.contains(cell)){
				continue;
			}
			visited.add(cell);
			if(pathLength == 1){
				targets.add(cell);
			}
			else{
				findAllTargets(cell, pathLength - 1);
			}
			visited.remove(cell);
		}
		
		
	}

		public Set<BoardCell> getTargets(){
			return targets;
		}

		public Set<BoardCell> getAdjList(BoardCell cell){

			return adjMtx.get(cell);
		}

		public BoardCell getCell(int i, int j) {

			return grid[i][j];
		}
	}
