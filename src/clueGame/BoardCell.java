package clueGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Map;

//Blake Sanders
//Jonny Morsicato 
public class BoardCell {
	private final int CELL_SIDE_LENGTH = 25;
	private int pixelX, pixelY;
	private int row;
	private int col;
	private String doorString;
	private boolean isTarget = false;
	
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		pixelX = (col * CELL_SIDE_LENGTH) + Board.PANEL_X_OFFSET;
		pixelY = (row * CELL_SIDE_LENGTH) + Board.PANEL_Y_OFFSET;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public void setDoorString(String doorString){
		this.doorString = doorString;
	}
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + "]";
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	
	public void makeTarget() {
		isTarget = true;
	}

	public boolean isDoorway() {
		if(doorString.length() != 2){
			return false;
		}
		if(doorString.charAt(1) == 'R' ||doorString.charAt(1) == 'L' || doorString.charAt(1) == 'U' || doorString.charAt(1) == 'D'){
			return true;
		}
		else{
			return false;
		}
	}

	public DoorDirection getDoorDirection() {
		char c = doorString.charAt(1);
		switch (c){
		case 'R' : return DoorDirection.RIGHT;
		case 'L' : return DoorDirection.LEFT;
		case 'U' : return DoorDirection.UP;
		case 'D' : return DoorDirection.DOWN;
		}
		return null;
	}
	public char getInitial() {
		char initial = doorString.charAt(0);
		return initial;
	}
	
	public void draw(Graphics g)
	{
		Board board = Board.getInstance();
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(0));
		if (doorString.charAt(0) == 'W')
		{
			if(isTarget){
				g2.setColor(Color.CYAN);
				g2.fillRect(pixelX, pixelY, CELL_SIDE_LENGTH, CELL_SIDE_LENGTH);
				isTarget = false;
			}
			g2.setColor(Color.DARK_GRAY);
			g2.drawRect(pixelX, pixelY, CELL_SIDE_LENGTH, CELL_SIDE_LENGTH);
		}
		else
		{
			g2.setColor(Color.DARK_GRAY);
			g2.fillRect(pixelX, pixelY, CELL_SIDE_LENGTH, CELL_SIDE_LENGTH);
			g2.drawRect(pixelX, pixelY, CELL_SIDE_LENGTH, CELL_SIDE_LENGTH);
			if (isDoorway())
			{
				drawDoorLine(g2);
			}
			else if (doorString.length() == 2)
			{
				g2.setColor(Color.WHITE);
				g2.drawString(board.getLegendMap().get(doorString.charAt(0)), pixelX + 1, pixelY - 5);
			}
		}
	}
	private void drawDoorLine(Graphics2D g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(3));
		switch(getDoorDirection())
		{
		case UP:
			g2.drawLine(pixelX, pixelY, pixelX + CELL_SIDE_LENGTH, pixelY);
			break;
		case DOWN:
			g2.drawLine(pixelX, pixelY + CELL_SIDE_LENGTH, pixelX + CELL_SIDE_LENGTH, pixelY + CELL_SIDE_LENGTH);
			break;
		case LEFT:
			g2.drawLine(pixelX, pixelY, pixelX, pixelY + CELL_SIDE_LENGTH);
			break;
		case RIGHT:
			g2.drawLine(pixelX + CELL_SIDE_LENGTH, pixelY, pixelX + CELL_SIDE_LENGTH, pixelY + CELL_SIDE_LENGTH);
			break;
		}
		
	}
	public void drawPlayer(Graphics g, Color color) 
	{
		g.setColor(color);
		g.fillOval(pixelX, pixelY, CELL_SIDE_LENGTH, CELL_SIDE_LENGTH);
	}

}
