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
import clueGame.DisplayPanel;


public class GUI extends JFrame{
	private JTextArea textArea;
	private JPanel boardPanel;
	private JPanel playerHandPanel;
	//private DisplayPanel displayPanel;
	private JMenuBar menuBar;
	private static Board board;	//used to get the human player's cards and name
	private String humanName;
	public GUI()
	{
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(800, 800);
		
		createBoardPanel();		
		createPlayerHandPanel();
		//displayPanel = new DisplayPanel();

		//Menu Bar Creation
		menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		
		add(boardPanel, BorderLayout.CENTER);
		add(playerHandPanel, BorderLayout.EAST);
		//add(displayPanel, BorderLayout.SOUTH);
		setJMenuBar(menuBar);
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
		board.setConfigFiles("ClueLayout.csv", "Legend.txt", "Players2.txt","Weapons.txt");
		board.initialize();
		board.setBorder(new TitledBorder (new EtchedBorder(), "Clue Board"));
		GUI.board = board;
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
		boolean turnCompleted;
		//update bottom panel for name and dice roll
//		displayPanel.setTurnText(currentPlayer.getName());
//		displayPanel.setDieText(Integer.toString(roll));
		board.calcTargets(currentPlayer.getLocation(), roll);
		Set<BoardCell> targets = board.getTargets();
		
		if(currentPlayer.isHuman()){
			for(BoardCell c : targets){
				c.makeTarget();
			}
			board.repaint();
			//board.displayTargets();
			//draw target options
			//listen for click, validate selection
			//display error message for invalid target
			
		}
		if(!currentPlayer.isHuman()){
			//update player location, display
		}
		
		
		//cycle playerOrder
		board.cycleTurnOrder();
	}

	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		GUI gui = new GUI();
		gui.setVisible(true);
		//splash field for start message, need to get the starting player's name still
		String startMessage = "You are " + gui.getHumanName() + ", press Next Player to begin player";
		JOptionPane.showMessageDialog(gui, startMessage, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		gui.playOneTurn();
	}

}
