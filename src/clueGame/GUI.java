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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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


public class GUI extends JFrame{
	private int cellLength = BoardCell.getCELL_SIDE_LENGTH();
	private static GUI instance;
	private JTextArea textArea;
	private JPanel boardPanel;
	private JPanel playerHandPanel;
	private JPanel displayPanel;
	private JMenuBar menuBar;
	private static Board board;	//used to get the human player's cards and name
	private String humanName;
	JPanel turnPanel;
	JPanel nextPanel;
	JPanel accusationButtonPanel;
	JPanel diePanel;
	JPanel guessPanel;
	JPanel resultPanel;
	SuggestionDialog suggestionDialog;
	JDialog accusationPanel;
	JButton next;
	JButton accusation;
	JButton submit;
	JButton cancel;
	private JTextArea turnOrderText = new JTextArea();
	private JTextField turnText;
	private JTextField dieText;
	private JTextField guessText;
	private JTextField resultText;
	private boolean turnCompleted;
	private Player currentPlayer;
	private Set<BoardCell> targets;
	private List<String> people= new ArrayList<String>();
	private List<String> weapons= new ArrayList<String>();
	private List<String> rooms= new ArrayList<String>();

	private static GUI getInstance() {
		return instance;
	}
	public GUI()
	{

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(800, 800);

		createBoardPanel();		
		createPlayerHandPanel();
		createDisplayPanel();

		//Menu Bar Creation
		menuBar = new JMenuBar();
		menuBar.add(createFileMenu());

		add(boardPanel, BorderLayout.CENTER);
		add(playerHandPanel, BorderLayout.EAST);
		add(displayPanel, BorderLayout.SOUTH);
		setJMenuBar(menuBar);

		for(Card c : board.getWeapons()){
			weapons.add(c.getName());
		}
		for(Card c : board.getPlayers()){
			people.add(c.getName());
		}
		for(Card c : board.getRooms()){
			rooms.add(c.getName());
		}
		Collections.sort(weapons);
		Collections.sort(people);
		Collections.sort(rooms);
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
				board.repaint();
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

	private void createBoardPanel(){
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "Legend.txt", "Players.txt","Weapons.txt");
		board.initialize();
		board.setBorder(new TitledBorder (new EtchedBorder(), "Clue Board"));
		GUI.board = board;
		board.addMouseListener(new targetListener());
		boardPanel = board;
	}

	class targetListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {
			targets = board.getTargets();
			//			System.out.println("cell length is" + cellLength);
			//			System.out.println("e.getX is: " + e.getX());
			//			System.out.println("e.getY is: " + e.getY());
			BoardCell cell = board.getCellAt(0, 0); //just initialize to a far out room cell
			//			for(int i =0;i<board.getNumRows();i++){
			//				for(int j=0;j<board.getNumColumns();j++){
			//					if(board.grid[i][j].containsClick(e.getX(),e.getY())){
			//						cell = board.grid[i][j];
			//					}
			//				}
			//			}
			int gridX = 0;
			int gridY = 0;
			if(e.getX() > board.PANEL_X_OFFSET && e.getX() < 543){
				gridX = ((e.getX() - 18) / cellLength);
				//				System.out.println("gridX is" + gridX);
			}
			if(e.getY() > board.PANEL_Y_OFFSET  && e.getY() < 568){
				gridY = ((e.getY() - 18) / cellLength);
				//				System.out.println("gridY is" + gridY);
			}
			if(gridX < board.getNumColumns() && gridY < board.getNumRows()){
				cell.setCol(gridX);
				cell.setRow(gridY);
			}
			else{cell = null;}

			if(cell == null){
				JOptionPane.showMessageDialog(getInstance(),"That is not a cell!");
			}
			else if(turnCompleted == true){
				JOptionPane.showMessageDialog(getInstance(),"Your turn in over, press Next Player");
			}
			else { //if (cell != null && (turnCompleted != true)){
				cell = board.getCellAt(gridY, gridX);
				if (!targets.contains(cell)){
					JOptionPane.showMessageDialog(getInstance(),"That is not a target!");
				}
				else{
					currentPlayer.setLocation(cell);
					for(BoardCell c: targets){
						c.setTarget(false);
					}
					if(cell.getInitial()!= 'W'){
						String room = board.getLegendMap().get(cell.getInitial());
						Card currentRoom = board.findCard(room);
						currentPlayer.setCurrentRoom(currentRoom);
						suggestionDialog = new SuggestionDialog();
						suggestionDialog.setVisible(true);
					}
					else{
						currentPlayer.setCurrentRoom(null);
					}
					board.repaint();
					turnCompleted = true;
				}
			}


		}


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

		JPanel turnOrderPanel = new JPanel();
		turnOrderPanel.setBorder(new TitledBorder (new EtchedBorder(), "Turn Order"));
		textArea = turnOrderText;
		textArea.setColumns(10);
		textArea.setEditable(false);
		turnOrderPanel.add(textArea);
		panel.add(turnOrderPanel);

		return panel;
	}

	public void createDisplayPanel() {
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(0,3));
		createTurnPanel();
		nextPanel = createNextPanel();
		accusationButtonPanel = createAccusationPanel();
		createDiePanel();
		createGuessPanel();
		createResultPanel();

		displayPanel.add(turnPanel);
		displayPanel.add(accusationButtonPanel);
		displayPanel.add(nextPanel);
		displayPanel.add(diePanel);
		displayPanel.add(guessPanel);
		displayPanel.add(resultPanel);	
	}

	private void createTurnPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(2,1));
		JLabel label = new JLabel("Whose Turn");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		turnText = new JTextField("");
		turnText.setColumns(40);
		turnText.setEditable(false);
		panel.add(label);
		panel.add(turnText);

		turnPanel = panel;
	}

	public void setTurnText(String turn){
		turnText.setText(turn);
	}

	public void setDieText(String roll){
		dieText.setText(roll);
	}

	public void setGuessText(String guess){
		guessText.setText(guess);
	}

	public void setResultText(String result){
		resultText.setText(result);
	}

	private void createDiePanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(1,2));
		JLabel turnLabel = new JLabel("Roll");
		dieText = new JTextField("");
		dieText.setColumns(15);
		dieText.setEditable(false);
		panel.add(turnLabel);
		panel.add(dieText);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));

		diePanel = panel;
	}

	private void createGuessPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(2,1));
		guessText = new JTextField("");
		guessText.setColumns(100);
		guessText.setEditable(false);
		panel.add(guessText);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));

		guessPanel = panel;
	}

	private void createResultPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(1,2));
		JLabel turnLabel = new JLabel("Response");
		resultText = new JTextField("");
		resultText.setColumns(50);
		resultText.setEditable(false);
		panel.add(turnLabel);
		panel.add(resultText);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess result"));

		resultPanel = panel;
	}

	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == next){
				if(!turnCompleted){
					JOptionPane.showMessageDialog(getInstance(), "You must complete your turn");
				}
				else{
					board.cycleTurnOrder();
					playOneTurn();
				}
			}
			else if (e.getSource() == accusation){
				if(!currentPlayer.isHuman()){
					JOptionPane.showMessageDialog(getInstance(), "It is not your turn");
				}
				else {
					if(turnCompleted){
						JOptionPane.showMessageDialog(getInstance(), "Your turn is already complete");
					}
					else{
						//create accusation panel
					}
				}
			}
		}
	}

	private JPanel createNextPanel() {
		JPanel panel = new JPanel();
		next = new JButton("Next player");
		next.addActionListener(new ButtonListener());
		panel.add(next);
		return panel;
	}

	private JPanel createAccusationPanel() {
		JPanel panel = new JPanel();
		accusation = new JButton("Make an accusation");
		accusation.addActionListener(new ButtonListener());
		panel.add(accusation);
		return panel;
	}

	public String getHumanName() {
		return humanName;
	}

	class SuggestionDialog extends JDialog{
		public SuggestionDialog(){

			JTextArea room = new JTextArea();
			room.setEditable(false);
			JComboBox<String> person = new JComboBox<String>();
			JComboBox<String> weapon = new JComboBox<String>();
			JLabel roomLabel = new JLabel("Your Room");
			JLabel personLabel = new JLabel("Person");
			JLabel weaponLabel = new JLabel("Weapon");

			class suggestionListener implements ActionListener {
				public void actionPerformed(ActionEvent e){
					if(e.getSource() == submit){
						Card sugRoom = board.findCard(room.getText());
						Card sugPerson = board.findCard(person.getSelectedItem().toString());
						Card sugWeapon = board.findCard(weapon.getSelectedItem().toString());
						Solution suggestion = new Solution(sugRoom,sugPerson,sugWeapon);
						Card result = board.handleSuggestion(suggestion,currentPlayer);
						resultText.setText(result.getName());
						String guess = currentPlayer.getName() + " guessed " + sugRoom.getName() + ", " + sugPerson.getName()
							+ ", " + sugWeapon.getName();
						guessText.setText(guess);
						setVisible(false);
					}
					else if(e.getSource() == cancel){
						setVisible(false);
					}
				}
			}


			submit = new JButton("Submit");
			cancel = new JButton("Cancel");
			submit.addActionListener(new suggestionListener());
			cancel.addActionListener(new suggestionListener());

			setTitle("Make a Guess");
			setSize(300,200);
			setLayout(new GridLayout(0,2));

			room.setText(currentPlayer.getCurrentRoom().getName());
			for(int i = 0; i <people.size();i++){
				person.addItem(people.get(i));
			}
			for(int i = 0; i<weapons.size();i++){
				weapon.addItem(weapons.get(i));
			}

			add(roomLabel);
			add(room);
			add(personLabel);
			add(person);
			add(weaponLabel);
			add(weapon);
			add(submit);
			add(cancel);
		}
	}



	public void playOneTurn() {
		if(!board.isGameSolved()){
			//recursive, needs to know whose turn, location,
			String turnOrderPanelText = "";
			for(Player p: board.getTurnOrder()){
				turnOrderPanelText += (p.getName() + "\n");
			}
			turnOrderText.setText(turnOrderPanelText);
			currentPlayer = board.getTurnOrder().getFirst();
			int roll = board.rollDie();
			turnCompleted = false;
			//update bottom panel for name and dice roll
			setTurnText(currentPlayer.getName());
			setDieText(Integer.toString(roll));
			board.calcTargets(currentPlayer.getLocation(), roll);
			Set<BoardCell> targets = board.getTargets();

			if(currentPlayer.isHuman()){
				for(BoardCell c : targets){
					c.setTarget(true);
				}
				board.repaint();

				//listen for click, validate selection
				//display error message for invalid target

			}
			if(!currentPlayer.isHuman()){
				ComputerPlayer cp = (ComputerPlayer) currentPlayer;
				cp.makeMove(roll);
				turnCompleted = true;
			}


		}
		else{
			JOptionPane.showMessageDialog(getInstance(), "You've won!");
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		GUI gui = new GUI();
		gui.setVisible(true);
		//splash field for start message, need to get the starting player's name still
		String startMessage = "You are " + gui.getHumanName() + ", press Next Player to begin player";
		JOptionPane.showMessageDialog(gui, startMessage, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		//while(!board.isGameSolved()){
		// Cycle before game for testing purposes
		//board.cycleTurnOrder();
		gui.playOneTurn();
		//}
	}

}
