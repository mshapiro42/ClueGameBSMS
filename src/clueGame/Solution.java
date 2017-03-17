package clueGame;

import java.util.HashSet;
import java.util.Set;

public class Solution {
//	private String person;
//	private String room;
//	private String weapon;
	private Card person = new Card();
	private Card weapon = new Card();
	private Card room = new Card();

	public Solution() {}

	public Card getPerson() {
		return person;
	}

	public void setPerson(Card person) {
		this.person = person;
	}

	public Card getRoom() {
		return room;
	}

	public void setRoom(Card room) {
		this.room = room;
	}

	public Card getWeapon() {
		return weapon;
	}

	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}
	
	public Set<Card> getCards() {
		Set<Card> cards = new HashSet<Card>();
		cards.add(weapon);
		cards.add(person);
		cards.add(room);
		return cards;
	}
	
	

}
