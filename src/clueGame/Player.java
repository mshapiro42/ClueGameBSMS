package clueGame;

import java.awt.Color;

public class Player {
	private Card[] myCards;
	private String name;
	private BoardCell location;
	private int row;
	private int column;
	private Color color;
	
	
	public Card[] getMyCards() {
		return myCards;
	}

	public void setMyCards(Card[] list) {
		this.myCards = list;
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

	public Player() {
		// TODO Auto-generated constructor stub
	}
	
	private Card disproveSuggestion(Solution suggestion){
		return null;
	}
}
