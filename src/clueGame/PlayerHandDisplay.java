package clueGame;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PlayerHandDisplay extends JPanel{
	private JTextField textField;
	private GridBagConstraints c;
	Set <Card> cards = new HashSet<Card>();
	
	
	public PlayerHandDisplay(GridBagConstraints c, Set<Card> playerHand) {
		super();
		this.c = c;
		this.cards = playerHand;
	}



	public JPanel createMyCardsPanel(){
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		panel.setLayout(new GridLayout(1,3));
		c.gridx = 10;
		c.gridy = 0;
		c.gridheight = 10;
		c.gridwidth = 1;
		
		JPanel peoplePanel = new JPanel();
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		textField = new JTextField();
		textField.setText("People text");
		textField.setColumns(100);
		textField.setEditable(false);
		peoplePanel.add(textField);
		panel.add(peoplePanel);
		
		JPanel roomPanel = new JPanel();
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		textField.setText("Rooms text");
		roomPanel.add(textField);
		panel.add(roomPanel);

		JPanel weaponPanel = new JPanel();
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		textField.setText("Weapons text");
		weaponPanel.add(textField);
		panel.add(weaponPanel);
		
		return panel;
		
	}
}
