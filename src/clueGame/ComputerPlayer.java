package clueGame;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	
	private char lastRoomVisited;
	private Set<Card> seenCards;
	private Set<Card> unseenCards;
	


	public Set<Card> getSeenCards() {
		return seenCards;
	}

	public void setSeenCards(Set<Card> seenCards) {
		this.seenCards = seenCards;
	}

	public Set<Card> getUnseenCards() {
		return unseenCards;
	}

	public void setUnseenCards(Set<Card> unseenCards) {
		this.unseenCards = unseenCards;
	}

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
	
	public Solution makeSuggestion(){
		/*
	    (2) Room matches current location
	    (2) If only one weapon not seen, it's selected
	    (2) If only one person not seen, it's selected (can be same test as weapon)
	    (2) If multiple weapons not seen, one of them is randomly selected
	    (2) If multiple persons not seen, one of them is randomly selected*/
		
		BoardCell location = getLocation();
		return null;
		
	}
	
	public char getLastRoomVisited() {
		return lastRoomVisited;
	}

	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}
}
