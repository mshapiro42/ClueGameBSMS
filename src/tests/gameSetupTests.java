package tests;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class gameSetupTests {
	private static Board board;
	public static final int LEGEND_SIZE = 11;;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 21;
	
	@BeforeClass
	public static void setUpBeforeClass() throws BadConfigFormatException {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "Legend.txt", "Players.txt","Weapons.txt");
		board.initialize();
	}
	
	

}
