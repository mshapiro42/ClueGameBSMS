package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import clueGame.Card.cardType;

public class ComputerPlayer extends Player{
	
	private char [] roomsVisited = new char[2];
	private char lastRoomVisited;
	private Set<Card> seenCards = new HashSet<Card>();
	private Set<Card> unseenCards = new HashSet<Card>();


	//Constructors
	public ComputerPlayer(Board board) {
		super(board);
		isHuman = false;
		
		Set<Card> allCards = board.getCards();
		
		//Loop through all cards
		for (Card c : allCards) {
			//If player has it in their hand, it counts as seen
			if (myCards.contains(c)) {
				seenCards.add(c);
			}
			//If player does not have it in their hand, they have not seen it
			else {
				unseenCards.add(c);
			}
		}
	}

	//Show a card to computer player to update their seen/unseen cards
	public void showCard(Card c) {
		seenCards.add(c);
		unseenCards.remove(c);
	}
	
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

	public BoardCell pickLocation (Set<BoardCell> targets){
		//If a door is in range, it is a priority target
		//there may be more than one door in range, make it a set
		Set<BoardCell> priorityTargets = new HashSet<BoardCell>();
		
		for (BoardCell t : targets) {
			//if room not just visited, it will be a priority target
			if (t.isDoorway() && (t.getInitial() != roomsVisited[0] || t.getInitial() != roomsVisited[1])) {
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
	    (2) If only one weapon not seen, it's selected
	    (2) If only one person not seen, it's selected (can be same test as weapon)
	    (2) If multiple weapons not seen, one of them is randomly selected
	    (2) If multiple persons not seen, one of them is randomly selected*/
		
		//get player location
		BoardCell location = getLocation();
		
		//get the instance of board in order to extract the legend
		Map<Character, String> legend = board.getLegendMap();
		
		//Player must suggest room they are currently in
		String currentRoomString = legend.get(location.getInitial());
		Card currentRoom = board.findCard(currentRoomString);
		
		//Add current room to suggestion
		Solution suggestion = new Solution();
		suggestion.setRoom(currentRoom);
		
		//count number of unseen cards of each type
		Set<Card> unseenWeapons = new HashSet<Card>();
		Set<Card> unseenPeople = new HashSet<Card>();
		
		for (Card c : unseenCards) {
			if(c.getType() == cardType.WEAPON) {
				unseenWeapons.add(c);
			}
			else if (c.getType() == cardType.PERSON) {
				unseenPeople.add(c);
			}
		}

		//choose at random from the set of unseen cards
		//if there is only one, it will be chosen as it is the only option
		Card suspectedWeapon = board.getRandomCard(unseenWeapons);
		Card suspectedPerson = board.getRandomCard(unseenPeople);
		
		//Add to suggestion
		suggestion.setWeapon(suspectedWeapon);
		suggestion.setPerson(suspectedPerson);
		
		return suggestion;
	}
	
	public char getLastRoomVisited() {
		return lastRoomVisited;
	}

	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
		roomsVisited[1] = roomsVisited[0];
		roomsVisited[0] = lastRoomVisited;
	}
	
	public void makeMove(int die){
		BoardCell newLocation = pickLocation(board.getTargets());
		this.setLocation(newLocation);
		if (newLocation.isDoorway()){
			//makeSuggestion();
		}
		board.repaint();
		//makeAccusation();
	}
}
