package reversi.logic;

import reversi.object.Board;
import reversi.object.Cell;
import reversi.object.Color;


import java.awt.Point;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


/**
 * Class of the controller of the board.
 */
public class BoardController {

    /**
     * The board, which id being managed.
     */
    private Board board;

    /**
     * The history of the moves.
     */
    private final Stack<Map<Point, Cell>> history;

    public BoardController() {
        this.board = new Board();
        history = new Stack<>();
    }


    /**
     * Getting the board being managed.
     *
     * @return The current board.
     */
    public Board getBoard() {
        return board;
    }


    /**
     * Determining whether the game is over.
     *
     * @return True if the game is over; false otherwise.
     */
    public boolean isGameOver() {
        return (board.isBoardFull() || board.countDisks(Color.BLACK) == 0 || board.countDisks(Color.WHITE) == 0 ||
                (!(canPutAnyDisk(Color.BLACK) || canPutAnyDisk(Color.WHITE))));
    }


    /**
     * Checks if it's possible to put any disk of the specified color on board.
     *
     * @param color The given color.
     * @return True if any disk can be put; false otherwise.
     */
    public boolean canPutAnyDisk(Color color) {
        return board.getPossibleMoves(color).size() > 0;
    }


    /**
     * Checks whether the disk can be put on a specific position.
     *
     * @param color    The color of the disk.
     * @param position The possible position of the disk.
     * @return True if the disk can be put in this position; false otherwise.
     */
    public boolean canPutDisk(Color color, Point position) {
        return board.getPossibleMoves(color).contains(position);
    }


    /**
     * Putting the disk on some position.
     *
     * @param color          The color of the disk.
     * @param positionOfDisk The position of the disk.
     */
    public void putDisk(Color color, Point positionOfDisk) {
        board.putNewDisk(color, positionOfDisk);
    }


    /**
     * Marks the possible moves on the printed board and writes the coordinates of the possible positions to put a disk.
     *
     * @param color The color of the disk.
     */
    public void showPossibleMoves(Color color) {
        Set<Point> possibleMoves = board.getPossibleMoves(color);
        for (Point position : possibleMoves) {
            board.updateCell(Cell.POSSIBLE, position);
        }
        System.out.print(board);
        System.out.println("Possible positions (row column): ");
        for (Point position : possibleMoves) {
            System.out.print(position.x + " " + position.y + ";\t");
        }
        System.out.println();
        for (Point position : possibleMoves) {
            board.updateCell(Cell.EMPTY, position);
        }
    }


    /**
     * Erases the last state of history of moves, if it's not the starting point of the game.
     */
    public void eraseLastStepFromHistory() {
        if (history.size() > 1) {
            history.pop();
            board = new Board(history.peek());
        } else {
            System.out.println("You cannot revert any more moves.");
        }
    }


    /**
     * Method for updating the history of moves.
     */
    public void updateHistory() {
        history.push(board.getCells());
    }
}
