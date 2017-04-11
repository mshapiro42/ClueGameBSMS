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
	private static JPanel boardPanel;
	private static JPanel playerHandPanel;
	private static JPanel displayPanel;
	private static JPanel turnPanel;
	private static JPanel diePanel;
	private static JPanel guessPanel;
	private static JPanel resultPanel;
	private String turn = null;
	private String die = null;
	private String guess = null;
	private String result = null;
	private static JMenuBar menuBar;
	private Board board;	//used to get the human player's cards and name
	private String humanName;
	public GUI()
	{
		// Create a layout with 2 rows
		
		createBoardPanel();		
		createPlayerHandPanel();
		createDisplayPanel();

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
				DetectiveNotesDialog notes = new DetectiveNotesDialog(board);
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

	private void createDisplayPanel(){
		JPanel dp = new JPanel();
		dp.setLayout(new GridLayout(0,3));
		createTurnPanel();
		dp.add(turnPanel);
		JPanel next = createNextPanel();
		dp.add(next);
		JPanel accusation = createAccusationPanel();
		dp.add(accusation);
		createDiePanel();
		dp.add(diePanel);
		createGuessPanel();
		dp.add(guessPanel);
		createResultPanel();
		dp.add(resultPanel);	
		displayPanel = dp;
	}
	
	private void createTurnPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(2,1));
		JLabel label = new JLabel("Whose Turn");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		textField = new JTextField(turn);
		textField.setColumns(40);
		textField.setEditable(false);
		panel.add(label);
		panel.add(textField);

		turnPanel = panel;
	}

	private void createDiePanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(1,2));
		JLabel turnLabel = new JLabel("Roll");
		textField = new JTextField(die);
		textField.setColumns(15);
		textField.setEditable(false);
		panel.add(turnLabel);
		panel.add(textField);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));

		diePanel = panel;
	}

	private void createGuessPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(2,1));
		textField = new JTextField(guess);
		textField.setColumns(100);
		textField.setEditable(false);
		panel.add(textField);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));

		guessPanel = panel;
	}

	private void createResultPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(1,2));
		JLabel turnLabel = new JLabel("Response");
		textField = new JTextField(result);
		textField.setColumns(50);
		textField.setEditable(false);
		panel.add(turnLabel);
		panel.add(textField);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess result"));

		resultPanel = panel;
	}

	private JPanel createNextPanel() {
		JPanel panel = new JPanel();
		JButton nextPlayer = new JButton("Next player");
		panel.add(nextPlayer);
		return panel;
	}
	
	private JPanel createAccusationPanel() {
		JPanel panel = new JPanel();
		JButton accusation = new JButton("Make an accusation");
		panel.add(accusation);
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
		frame.add(displayPanel, BorderLayout.SOUTH);
		// Now let's view it
		frame.setSize(800, 800);
		frame.setJMenuBar(menuBar);
		frame.setVisible(true);
		//splash field for start message, need to get the starting player's name still
		String startMessage = "You are " + gui.getHumanName() + ", press Next Player to begin player";
		JOptionPane.showMessageDialog(frame, startMessage, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}

}
