package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class GUI extends JPanel{
	private JTextField textField;
	private GridBagConstraints c;
	private static JPanel boardPanel;
	
	public GUI()
	{
		// Create a layout with 2 rows
		GridBagLayout layout = new GridBagLayout();
		c = new GridBagConstraints();
		c.weightx = .5;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		setLayout(layout);
		JPanel panel = createTurnPanel();
		add(panel,c);
		c.insets = new Insets(10,10,10,10);
		panel = createButtonPanel();
		add(panel,c);
		panel = createDiePanel();
		add(panel,c);
		panel = createGuessPanel();
		add(panel,c);
		panel = createResultPanel();
		add(panel,c);	
		createBoardPanel();		
	}

	 private void createBoardPanel() {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "Legend.txt", "Players.txt","Weapons.txt");
		board.initialize();
		board.setBorder(new TitledBorder (new EtchedBorder(), "Clue Board"));
		boardPanel = board;
	}

	private JPanel createTurnPanel() {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			panel.setLayout(new GridLayout(2,1));
		 	JLabel label = new JLabel("Whose Turn");
		 	label.setHorizontalAlignment(SwingConstants.CENTER);
			textField = new JTextField();
			textField.setColumns(40);
			textField.setEditable(false);
			c.gridx =0;
			c.gridy = 6;
			c.insets = new Insets(10,50,10,10);
			panel.add(label);
			panel.add(textField);
			
			return panel;
	}
	 
	 //-------------------
	 private JPanel createDiePanel() {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			panel.setLayout(new GridLayout(1,2));
		 	JLabel turnLabel = new JLabel("Roll");
			textField = new JTextField();
			textField.setColumns(15);
			textField.setEditable(false);
			panel.add(turnLabel);
			panel.add(textField);
			c.gridx = 0;
			c.gridy = 7;
			c.gridwidth = 1;
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
			
			return panel;
	}
	 
	 private JPanel createGuessPanel() {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			panel.setLayout(new GridLayout(2,1));
			textField = new JTextField();
			textField.setColumns(100);
			textField.setEditable(false);
			panel.add(textField);
			c.gridx=1;
			c.gridy = 7;
			c.gridwidth = 3;
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
			
			return panel;
	}
	 
	 private JPanel createResultPanel() {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			panel.setLayout(new GridLayout(1,2));
		 	JLabel turnLabel = new JLabel("Response");
			textField = new JTextField();
			textField.setColumns(50);
			textField.setEditable(false);
			panel.add(turnLabel);
			panel.add(textField);
			c.gridx = 4;
			c.gridy = 7;
			c.gridwidth =2;
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess result"));
			
			return panel;
	}
	 
	 
	 //-------------------
	 
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		JButton nextPlayer = new JButton("Next player");
		c.gridx = 1;
		c.gridy = 5;
		panel.add(nextPlayer,c);
		JButton accusation = new JButton("Make an accusation");
		c.gridx = 2;
		c.gridy = 6;
		panel.add(accusation,c);
		return panel;
	}
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		// Create the JPanel and add it to the JFrame
		GUI gui = new GUI();
		frame.add(boardPanel, BorderLayout.CENTER);
		frame.add(gui, BorderLayout.SOUTH);
		// Now let's view it
		frame.setSize(800, 800);
		frame.setVisible(true);
	}

}
