package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class Player {
	protected Set<Card> myCards = new HashSet<Card>();
	private String name;
	private BoardCell location = new BoardCell(0, 0);
	private int row;
	private int column;
	private Color color;
	protected Board board;
	protected boolean isHuman;



	public boolean isHuman() {
		return isHuman;
	}

	public Player(Board board) {
		this.board = board;
	}

	public Set<Card> getMyCards() {
		return myCards;
	}

	public void setMyCards(Set<Card> myCards) {
		this.myCards = myCards;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BoardCell getLocation() {
		return location;
	}

	public void setLocation(BoardCell location) {
		this.location = location;
		row = location.getRow();
		column = location.getCol();
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(String c) {
		switch (c){
		case "RED":
			this.color = Color.RED;
			break;
		case "YELLOW":
			this.color = Color.YELLOW;
			break;
		case "GREEN":
			this.color = Color.GREEN;
			break;
		case "BLUE":
			this.color = Color.BLUE;
			break;
		case "PINK":
			this.color = Color.PINK;
			break;
		case "MAGENTA":
			this.color = Color.MAGENTA;
			break;

		}
	}

	public Card disproveSuggestion(Solution suggestion){
		//set of cards in player's hand that match a card in the suggestion
		Set<Card> matchingCards = new HashSet<Card>();

		for (Card c : myCards) {
			//If current card in player's hand matches part of the suggestion,
			//add it to matching cards
			if ((c == suggestion.getWeapon()) || 
					(c == suggestion.getPerson()) ||
					(c == suggestion.getRoom())) {
				matchingCards.add(c);
			}
		}
		//If player has only one matching card it should be returned
		if (matchingCards.size() == 1) {
			//return the only member
			for (Card m : matchingCards) {
				return m;
			}
		}
		//If players has >1 matching card, returned card should be chosen randomly
		if (matchingCards.size() > 1) {
			//return a random member
			return board.getRandomCard(matchingCards);
		}
		//If player has no matching cards, null is returned
		else {
			return null;
		}
	}
}
