package clueGame;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI extends JPanel{
	private JTextField textField;
	private GridBagConstraints c;
	
	public GUI()
	{
		// Create a layout with 2 rows
		GridBagLayout layout = new GridBagLayout();
		c = new GridBagConstraints();
		setLayout(layout);
		JPanel panel = createTurnPanel();
		add(panel);
		panel = createButtonPanel();
		add(panel);
		panel = createDiePanel();
		add(panel);
		panel = createGuessPanel();
		add(panel);
		panel = createResultPanel();
		add(panel);
	}

	 private JPanel createTurnPanel() {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			panel.setLayout(new GridLayout(1,2));
		 	JLabel label = new JLabel("Whose Turn");
			textField = new JTextField(20);
			textField.setEditable(false);
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
			textField = new JTextField(20);
			textField.setEditable(false);
			panel.add(turnLabel);
			panel.add(textField);
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
			return panel;
	}
	 
	 private JPanel createGuessPanel() {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			panel.setLayout(new GridLayout(1,2));
		 	JLabel turnLabel = new JLabel("Guess");
			textField = new JTextField(20);
			textField.setEditable(false);
			panel.add(turnLabel);
			panel.add(textField);
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
			return panel;
	}
	 
	 private JPanel createResultPanel() {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			panel.setLayout(new GridLayout(1,2));
		 	JLabel turnLabel = new JLabel("Response");
			textField = new JTextField(20);
			textField.setEditable(false);
			panel.add(turnLabel);
			panel.add(textField);
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess result"));
			return panel;
	}
	 
	 
	 //-------------------
	 
	private JPanel createButtonPanel() {
		// no layout specified, so this is flow
		JButton agree = new JButton("Next player");
		JButton disagree = new JButton("Make an accusation");
		JPanel panel = new JPanel();
		panel.add(agree);
		panel.add(disagree);
		return panel;
	}
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		frame.setSize(800, 500);	
		// Create the JPanel and add it to the JFrame
		GUI gui = new GUI();
		frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
	}

}
