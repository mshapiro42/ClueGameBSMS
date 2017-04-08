package clueGame;

import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotesGUI extends JFrame{
		private static Board board;
		private Set<Card> deck = new HashSet<Card>();
		private Set<Card> people= new HashSet<Card>();
		private Set<Card> weapons= new HashSet<Card>();
		private Set<Card> rooms= new HashSet<Card>();
//		private Board board = new Board();
		
		public DetectiveNotesGUI() {
			board = Board.getInstance();
			board.setConfigFiles("ClueLayout.csv", "Legend.txt");
			board.initialize();
			deck = board.getCards();
			sortCards();
			setSize(600,800);
			setLayout(new GridLayout(0,2));
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			JPanel pp = PeoplePanel();
			JPanel pgp = PersonGuessPanel();
			JPanel rp = RoomsPanel();
			JPanel rgp = RoomGuessPanel();
			JPanel wp = WeaponsPanel();
			JPanel wgp = WeaponGuessPanel();
			
			add(pp);
			add(pgp);
			add(rp);
			add(rgp);
			add(wp);
			add(wgp);
		}
		
		private void sortCards() {
			for(Card c : deck){
				switch (c.getType()) {
				case WEAPON:
					weapons.add(c);
				case PERSON:
					people.add(c);
				case ROOM:
					rooms.add(c);
				}
			}
		}
		
		private JPanel PeoplePanel(){
			JPanel pp = new JPanel();
			pp.setLayout(new GridLayout(0,2));
			
			pp.add(new JCheckBox("Miss Scarlet"));
			pp.add(new JCheckBox("Colonel Mustard"));
			pp.add(new JCheckBox("Mr Green"));
			pp.add(new JCheckBox("Mrs White"));
			pp.add(new JCheckBox("Mrs Peacock"));
			pp.add(new JCheckBox("Professor Plum"));

			pp.setBorder(new TitledBorder (new EtchedBorder(), "People"));
			
			return pp;
		}
		
		private JPanel PersonGuessPanel(){
			JPanel pp = new JPanel();
			JComboBox<String> combo = new JComboBox<String>();
			combo.addItem("Miss Scarlet");
			combo.addItem("Colonel Mustard");
			combo.addItem("Mr Green");
			combo.addItem("Mrs White");
			combo.addItem("Mrs Peacock");
			combo.addItem("Professor Plum");
			pp.add(combo);
			pp.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
			
			return pp;
		}
		
		private JPanel RoomsPanel(){
			JPanel pp = new JPanel();
			pp.setLayout(new GridLayout(0,2));
			
//			pp.add(new JCheckBox("Kitchen"));
//			pp.add(new JCheckBox("Lounge"));
//			pp.add(new JCheckBox("Conservatory"));
//			pp.add(new JCheckBox("Study"));
//			pp.add(new JCheckBox("Billiard Room"));
//			pp.add(new JCheckBox("Dining Room"));
			
			for(Card r : rooms){
				pp.add(new JCheckBox(r.getName()));
			}

			pp.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
			
			return pp;
		}
		
		private JPanel RoomGuessPanel(){
			JPanel pp = new JPanel();
			JComboBox<String> combo = new JComboBox<String>();
			for(Card r : rooms){
				combo.addItem(r.getName());
			}
//			combo.addItem("Kitchen");
//			combo.addItem("Lounge");
//			combo.addItem("Conservatory");
//			combo.addItem("Study");
//			combo.addItem("Billiard Room");
//			combo.addItem("Dining Room");
			pp.add(combo);
			pp.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
			
			return pp;
		}
		
		private JPanel WeaponsPanel(){
			JPanel pp = new JPanel();
			pp.setLayout(new GridLayout(0,2));
			
			pp.add(new JCheckBox("Candlestick"));
			pp.add(new JCheckBox("Knife"));
			pp.add(new JCheckBox("Lead Pipe"));
			pp.add(new JCheckBox("Revolver"));
			pp.add(new JCheckBox("Rope"));
			pp.add(new JCheckBox("Wrench"));

			pp.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
			
			return pp;
		}
		
		private JPanel WeaponGuessPanel(){
			JPanel pp = new JPanel();
			JComboBox<String> combo = new JComboBox<String>();
			combo.addItem("Candlestick");
			combo.addItem("Knife");
			combo.addItem("Lead Pipe");
			combo.addItem("Revolver");
			combo.addItem("Rope");
			combo.addItem("Wrench");
			pp.add(combo);
			pp.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
			
			return pp;
		}
		
		
		
		public static void main(String[] args){
			DetectiveNotesGUI gui = new DetectiveNotesGUI();
			gui.setVisible(true);
		}
}
