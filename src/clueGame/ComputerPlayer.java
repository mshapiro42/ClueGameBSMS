package clueGame;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	
	private char lastRoomVisited;
	private Set<Card> seenCards;
	


	public ComputerPlayer() {
		// TODO Auto-generated constructor stub
	}

	public BoardCell pickLocation (Set<BoardCell> targets){
		//If a door is in range, it is a priority target
		//there may be more than one door in range, make it a set
		Set<BoardCell> priorityTargets = new HashSet<BoardCell>();
		
		for (BoardCell t : targets) {
			//if room not just visited, it will be a priority target
			if (t.isDoorway() && (t.getInitial() != lastRoomVisited)) {
				priorityTargets.add(t);
			}
			//other cases:
			//if room matches last one visited, leave as a normal target
			//if target is not a room, leave as normal targets
		}
		
		BoardCell newTarget;
		
		//if any priority targets, select only among those targets
		//these will all be doorways that were not visited on last turn
		if (priorityTargets.size() != 0) {
			newTarget = getRandomTarget(priorityTargets);	
			
			//update last room visited
			setLastRoomVisited(newTarget.getInitial());
		}
		//otherwise, select from the normal targets
		else {
			newTarget = getRandomTarget(targets);
		}
		return newTarget;
	}
	
	public BoardCell getRandomTarget(Set<BoardCell> targets) {
		//get a random number equal to size of set
		int randomMember = new Random().nextInt(targets.size());

		int i = 0;
		for(BoardCell t : targets)
		{
		    if (i == randomMember)
		        return t;
		    i++;
		}
		
		//Should always return one of the members
		//but if empty set, it will return null
		return null;
	}
	
	private void makeAccusation(){
		
	}
	
	private void makeSuggestion(){
		
	}
	
	public char getLastRoomVisited() {
		return lastRoomVisited;
	}

	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}
}
