package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class DisplayPanel extends JPanel{
	JPanel turnPanel;
	JPanel nextPanel;
	JPanel accusationPanel;
	JPanel diePanel;
	JPanel guessPanel;
	JPanel resultPanel;
	JButton next;
	JButton accusation;
	private JTextField turnText;
	private JTextField dieText;
	private JTextField guessText;
	private JTextField resultText;
	
	public DisplayPanel() {
		setLayout(new GridLayout(0,3));
		createTurnPanel();
		nextPanel = createNextPanel();
		accusationPanel = createAccusationPanel();
		createDiePanel();
		createGuessPanel();
		createResultPanel();
		
		add(turnPanel);
		add(accusationPanel);
		add(nextPanel);
		add(diePanel);
		add(guessPanel);
		add(resultPanel);	
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
				//do something with next button
			}
			else if (e.getSource() == accusation){
				//do something with accusation button
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
}
