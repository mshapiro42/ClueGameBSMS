package clueGame;

import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotesDialog extends JDialog{
		private static Board board;
		private Set<Card> deck = new HashSet<Card>();
		private Set<Card> people= new HashSet<Card>();
		private Set<Card> weapons= new HashSet<Card>();
		private Set<Card> rooms= new HashSet<Card>();
//		private Board board = new Board();
		
		public DetectiveNotesDialog(Board board) {
			this.board= board;
			deck = board.getCards();
			sortCards();
			setSize(600,800);
			setLayout(new GridLayout(0,2));
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
					break;
				case PERSON:
					people.add(c);
					break;
				case ROOM:
					rooms.add(c);
					break;
				}
			}
		}
		
		private JPanel PeoplePanel(){
			JPanel pp = new JPanel();
			pp.setLayout(new GridLayout(0,2));
			
			for(Card p : people){
				pp.add(new JCheckBox(p.getName()));
			}

			pp.setBorder(new TitledBorder (new EtchedBorder(), "People"));
			
			return pp;
		}
		
		private JPanel PersonGuessPanel(){
			JPanel pp = new JPanel();
			JComboBox<String> combo = new JComboBox<String>();

			for(Card p : people){
				combo.addItem(p.getName());
			}
			pp.add(combo);
			pp.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
			
			return pp;
		}
		
		private JPanel RoomsPanel(){
			JPanel pp = new JPanel();
			pp.setLayout(new GridLayout(0,2));
			
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

			pp.add(combo);
			pp.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
			
			return pp;
		}
		
		private JPanel WeaponsPanel(){
			JPanel pp = new JPanel();
			pp.setLayout(new GridLayout(0,2));
			
			for(Card w : weapons){
				pp.add(new JCheckBox(w.getName()));
			}

			pp.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
			
			return pp;
		}
		
		private JPanel WeaponGuessPanel(){
			JPanel pp = new JPanel();
			JComboBox<String> combo = new JComboBox<String>();

			for(Card w : weapons){
				combo.addItem(w.getName());
			}
			
			pp.add(combo);
			pp.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
			
			return pp;
		}
}
