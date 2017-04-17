package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotesDialog extends JDialog{
		private Board board;
		private Set<Card> deck = new HashSet<Card>();
		private List<String> people= new ArrayList<String>();
		private List<String> weapons= new ArrayList<String>();
		private List<String> rooms= new ArrayList<String>();
//		private Board board = new Board();
		
		public DetectiveNotesDialog(Board board) {
			this.board= board;
			deck = this.board.getCards();
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
					weapons.add(c.getName());
					break;
				case PERSON:
					people.add(c.getName());
					break;
				case ROOM:
					rooms.add(c.getName());
					break;
				}
			}
			Collections.sort(weapons);
			Collections.sort(people);
			Collections.sort(rooms);
		}
		
		private JPanel PeoplePanel(){
			JPanel pp = new JPanel();
			pp.setLayout(new GridLayout(0,2));
			
			for(int i =0; i <people.size();i++){
				pp.add(new JCheckBox(people.get(i)));
			}

			pp.setBorder(new TitledBorder (new EtchedBorder(), "People"));
			
			return pp;
		}
		
		private JPanel PersonGuessPanel(){
			JPanel pp = new JPanel();
			JComboBox<String> combo = new JComboBox<String>();

			for(int i =0; i <people.size();i++){
				combo.addItem(people.get(i));
			}
			pp.add(combo);
			pp.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
			
			return pp;
		}
		
		private JPanel RoomsPanel(){
			JPanel pp = new JPanel();
			pp.setLayout(new GridLayout(0,2));
			
			for(int i =0; i <rooms.size();i++){
				pp.add(new JCheckBox(rooms.get(i)));
			}

			pp.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
			
			return pp;
		}
		
		private JPanel RoomGuessPanel(){
			JPanel pp = new JPanel();
			JComboBox<String> combo = new JComboBox<String>();
			for(int i =0; i <rooms.size();i++){
				combo.addItem(rooms.get(i));
			}

			pp.add(combo);
			pp.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
			
			return pp;
		}
		
		private JPanel WeaponsPanel(){
			JPanel pp = new JPanel();
			pp.setLayout(new GridLayout(0,2));
			
			for(int i =0; i <weapons.size();i++){
				pp.add(new JCheckBox(weapons.get(i)));
			}

			pp.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
			
			return pp;
		}
		
		private JPanel WeaponGuessPanel(){
			JPanel pp = new JPanel();
			JComboBox<String> combo = new JComboBox<String>();

			for(int i =0; i <weapons.size();i++){
				combo.addItem(weapons.get(i));
			}
			
			pp.add(combo);
			pp.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
			
			return pp;
		}
}
