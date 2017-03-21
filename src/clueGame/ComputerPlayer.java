package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player{
	
	private BoardCell lastRoomVisited;

	public BoardCell getLastRoomVisited() {
		return lastRoomVisited;
	}

	public void setLastRoomVisited(BoardCell lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}

	public ComputerPlayer() {
		// TODO Auto-generated constructor stub
	}

	public BoardCell pickLocation (Set<BoardCell> targets){
		
		return null;
	}
	
	private void makeAccusation(){
		
	}
	
	private void makeSuggestion(){
		
	}
}
