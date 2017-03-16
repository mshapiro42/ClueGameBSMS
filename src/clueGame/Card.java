package clueGame;

public class Card {
	private String name;
	public enum cardType{PERSON,WEAPON,ROOM};
	private cardType type;
	
	public Card() {

	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public cardType getType() {
		return type;
	}

	public void setType(cardType type) {
		this.type = type;
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
