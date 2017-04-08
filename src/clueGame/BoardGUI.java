package clueGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class BoardGUI extends JFrame{
	
	
	public BoardGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
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
				DetectiveNotesGUI notes = new DetectiveNotesGUI();
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
		gui.setVisible(true);
	}
}
