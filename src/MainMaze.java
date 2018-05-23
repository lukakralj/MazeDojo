import dojo3.PlayerGrid;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * This class represents the grid field of the game. The goal is to reach the end (red square)
 * from the start (green square).
 * We move by pressing A, S, D, W on the keyboard.
 *
 * @author Luka Kralj
 * @version 15 March 2018
 * 
 * TODO: loosing condition when can't move
 */
public class MainMaze extends PlayerGrid {
    private final int DIFFICULTY = 4; // More means easier.

    private Random rand;
    private int rows;
    private int cols;

    private int currentRow;
    private int currentCol;

    /**
     * Create new maze.
     *
     * @param rows Number of rows.
     * @param columns Number of columns.
     * @param windowWidth Width of the window.
     * @param windowHeight Height of the window.
     */
    public MainMaze(int rows, int columns, int windowWidth, int windowHeight) {
        super(rows, columns, windowWidth, windowHeight);
        this.rows = rows;
        this.cols = columns;
        rand = new Random();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createMaze();
        setStartEnd();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char code = e.getKeyChar();
                if (code == 'w') {
                    keyClicked("up");
                }
                else if (code == 's') {
                    keyClicked("down");
                }
                else if (code == 'a') {
                    keyClicked("left");
                }
                else if (code == 'd') {
                    keyClicked("right");
                }
            }
        });
        setVisible(true);
    }

    /**
     * Initialise the maze with black and white squares.
     */
    private void createMaze() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (rand.nextInt(DIFFICULTY) == 1) {
                    fillSquare(row, col, Color.BLACK);
                }
                else {
                    fillSquare(row, col, Color.WHITE);
                }
            }
        }
    }

    /**
     * Determine start (green square) and end (red square) of the maze.
     */
    private void setStartEnd() {
        int row = rand.nextInt(rows);
        int col = rand.nextInt(cols);

        while (squareFilled(row, col) != Color.WHITE) {
            row = rand.nextInt(rows);
            col = rand.nextInt(cols);
        }

        fillSquare(row, col, Color.GREEN);
        currentRow = row;
        currentCol = col;

        while (squareFilled(row, col) != Color.WHITE) {
            row = rand.nextInt(rows);
            col = rand.nextInt(cols);
        }

        fillSquare(row, col, Color.RED);
    }

    /**
     * Move in the selected direction.
     *
     * @param direction String for up, down, left or right.
     */
    private void keyClicked(String direction) {
        if (direction.equals("up")) {
            makeMove(currentRow-1, currentCol);
        }
        else if (direction.equals("down")) {
            makeMove(currentRow+1, currentCol);
        }
        else if (direction.equals("left")) {
            makeMove(currentRow, currentCol-1);
        }
        else if (direction.equals("right")) {
            makeMove(currentRow, currentCol+1);
        }
    }

    /**
     * Try to make a move to the given location.
     *
     * @param row Row to move to.
     * @param col Column to move to.
     */
    private void makeMove(int row, int col) {
        if(row < 0 || col < 0 || row > rows - 1 || col > cols - 1) {
            JOptionPane.showMessageDialog(this, "Invalid direction!");
        }
        else if (squareFilled(row, col) == Color.WHITE) {
            fillSquare(row, col, Color.GREEN);
            currentCol = col;
            currentRow = row;
        }
        else if (squareFilled(row, col) == Color.RED) {
            JOptionPane.showMessageDialog(this, "Congratulations!");
            System.exit(0);
        }
        else {
            JOptionPane.showMessageDialog(this, "Invalid direction!");
        }
    }
}
