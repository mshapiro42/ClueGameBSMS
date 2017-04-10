package clueGame;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class BoardGUI extends JFrame{
	private JTextField textField;
	private GridBagConstraints c;
	
	
	public BoardGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ClueGame");
		
		//Menu Bar Creation
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
		//Board Creation
		JPanel boardPanel = createBoardPanel();	
		//add(boardPanel, BorderLayout.CENTER);
		
		//Create Game Play GUI
		JPanel gamePlayPanel = createGamePlayPanel();
		add(gamePlayPanel, BorderLayout.CENTER);
	}

	 private JPanel createGamePlayPanel() {
		JPanel gamePlayPanel = new JPanel(); 
		GridBagLayout layout = new GridBagLayout();
		c = new GridBagConstraints();
		c.weightx = .5;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		setLayout(layout);
		JPanel component = createTurnPanel();
		gamePlayPanel.add(component,c);
		c.insets = new Insets(10,10,10,10);
		component = createButtonPanel();
		gamePlayPanel.add(component,c);
		component = createDiePanel();
		gamePlayPanel.add(component,c);
		component = createGuessPanel();
		gamePlayPanel.add(component,c);
		component = createResultPanel();
		gamePlayPanel.add(component,c);
		return gamePlayPanel;
	}

	private JPanel createBoardPanel() {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "Legend.txt", "Players.txt","Weapons.txt");
		board.initialize();
		board.setBorder(new TitledBorder (new EtchedBorder(), "Clue Board"));
		return board;
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
	
	public static void main (String[] args){
		BoardGUI gui = new BoardGUI();
		gui.setSize(800, 800);
		gui.setVisible(true);
	}
}
