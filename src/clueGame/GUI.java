package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.cardType;


public class GUI extends JPanel{
	private JTextField textField;
	private JTextArea textArea;
	private GridBagConstraints c;
	private static JPanel boardPanel;
	private static JPanel playerHandPanel;
	private static JMenuBar menuBar;
	private Board board;	//used to get the human player's cards and name
	private String humanName;
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
		createPlayerHandPanel();


		//Menu Bar Creation
		menuBar = new JMenuBar();
		menuBar.add(createFileMenu());

	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createDetectiveItem());
		menu.add(createFileExitItem());
		return menu;
	}

	private JMenuItem createDetectiveItem() {
		JMenuItem item = new JMenuItem("Show Detective Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e){
				DetectiveNotesDialog notes = new DetectiveNotesDialog();
				notes.setVisible(true);
			}
		}

		item.addActionListener(new MenuItemListener());

		return item;
	}

	private JMenuItem createFileExitItem(){
		JMenuItem item = new JMenuItem("Exit");

		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		}

		item.addActionListener(new MenuItemListener());

		return item;
	}

	private void createBoardPanel() {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "Legend.txt", "Players.txt","Weapons.txt");
		board.initialize();
		board.setBorder(new TitledBorder (new EtchedBorder(), "Clue Board"));
		this.board = board;
		boardPanel = board;
	}

	private void createPlayerHandPanel(){
		//passes the human player's hand into the panel function
		Set<Card> hand = new HashSet<Card>();
		for(Player p: board.getPeople()){
			if(p.isHuman){
				humanName = p.getName();
				hand = p.getMyCards();
			}
		}
		playerHandPanel = createPlayerCardsPanel(hand);
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
		c.gridx = 1;
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
		c.gridwidth = 2;
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess result"));

		return panel;
	}



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

	public JPanel createPlayerCardsPanel(Set<Card> hand){
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		panel.setLayout(new GridLayout(5,1));
		String text = "";
		
		JPanel peoplePanel = new JPanel();
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		textArea = new JTextArea();
		for(Card card: hand){
			if(card.getType() == cardType.PERSON){
				text += card.getName() + "\n";
			}
		}
		textArea.setText(text);
		textArea.setColumns(10);
		textArea.setEditable(false);
		peoplePanel.add(textArea);
		panel.add(peoplePanel);
		text = "";

		JPanel roomPanel = new JPanel();
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		textArea = new JTextArea();
		for(Card card: hand){
			if(card.getType() == cardType.ROOM){
				text += card.getName() + "\n";
			}
		}
		textArea.setColumns(10);
		textArea.setEditable(false);
		textArea.setText(text);
		roomPanel.add(textArea);
		panel.add(roomPanel);
		text = "";

		JPanel weaponPanel = new JPanel();
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		textArea = new JTextArea();
		for(Card card: hand){
			if(card.getType() == cardType.WEAPON){
				text += card.getName() + "\n";
			}
		}
		textArea.setText(text);
		textArea.setColumns(10);
		textArea.setEditable(false);
		weaponPanel.add(textArea);
		panel.add(weaponPanel);
		text = "";

		return panel;
	}
	

	public String getHumanName() {
		return humanName;
	}

	public void playOneTurn() {
		//recursive, needs to know whose turn, location,
		Player currentPlayer = board.getTurnOrder().getFirst();
		int roll = board.rollDie();
		
		if(currentPlayer.isHuman()){
			//display dice roll
			//draw target options
			//listen for click, validate selection
			//display error message for invalid target
			
		}
		if(!currentPlayer.isHuman){
			//update bottom panel for name and dice roll
			//update player location, display
		}
		
		
		//cycle playerOrder
		board.cycleTurnOrder();
	}
	
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		// Create the JPanel and add it to the JFrame
		GUI gui = new GUI();
		frame.add(boardPanel, BorderLayout.CENTER);
		frame.add(playerHandPanel, BorderLayout.EAST);
		frame.add(gui, BorderLayout.SOUTH);
		// Now let's view it
		frame.setSize(800, 800);
		frame.setJMenuBar(menuBar);
		frame.setVisible(true);
		//splash field for start message, need to get the starting player's name still
		String startMessage = "You are " + gui.getHumanName() + ", press Next Player to begin player";
		JOptionPane.showMessageDialog(frame, startMessage, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}

}
