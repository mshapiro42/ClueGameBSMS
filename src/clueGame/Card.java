package clueGame;

public class Card {
	String name;
	enum cardType{PERSON,WEAPON,ROOM};
	cardType type;
	
	public Card() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean equals(Card c){
		if ((this.name == c.name)&&(this.type==c.type)){
			return true;
		}
		else {
			return false;
		}
	}

}
