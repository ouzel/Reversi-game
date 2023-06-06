package reversi.logic;

import reversi.object.Board;
import reversi.object.Color;

import java.awt.Point;
import java.util.Set;


/**
 * Class of the Artificial intelligence of the robot. Chooses the best positions for the robot.
 */
public class ArtificialIntelligence {

    /**
     * Static method for searching the best position to put a disk for the robot (in easy mode).
     *
     * @param board The analysed board.
     * @param color The color of the robot.
     * @return The best position.
     */
    public static Point searchBestPositionEasyMode(Board board, Color color) {
        Set<Point> possibleMoves = board.getPossibleMoves(color);
        if (possibleMoves.size() == 0) {
            return new Point(-1, -1);
        } else if (possibleMoves.size() == 1) {
            return possibleMoves.iterator().next();
        } else {
            Board fakeBoard;
            double currentEvaluation;
            double maximum = -1;
            Point bestPosition = new Point(-1, -1);
            for (Point position : possibleMoves) {
                fakeBoard = new Board(board.getCells());
                currentEvaluation = fakeBoard.putNewDiskWithCounting(color, position);
                if (currentEvaluation > maximum) {
                    maximum = currentEvaluation;
                    bestPosition = position;
                }
            }
            return bestPosition;
        }
    }


    /**
     * Static method for searching the best position to put a disk for the robot (in hard mode).
     *
     * @param board The analysed board.
     * @param color The color of the robot.
     * @return The best position (or Point(-1;-1), if there are no possible positions).
     */
    public static Point searchBestPositionHardMode(Board board, Color color) {
        Set<Point> possibleMoves = board.getPossibleMoves(color);
        if (possibleMoves.size() == 0) {
            return new Point(-1, -1);
        } else if (possibleMoves.size() == 1) {
            return possibleMoves.iterator().next();
        } else {
            Board fakeBoard;
            double currentEvaluation;
            double maximum = -1000;
            Point bestPosition = new Point(-1, -1);
            for (Point position : possibleMoves) {
                fakeBoard = new Board(board.getCells());
                currentEvaluation = fakeBoard.putNewDiskWithCounting(color, position) -
                        searchMaxGain(fakeBoard, color.opposite());
                if (currentEvaluation > maximum) {
                    maximum = currentEvaluation;
                    bestPosition = position;
                }
            }
            return bestPosition;
        }
    }


    /**
     * Method used for finding the best score, gained by putting the disks in different possible positions.
     *
     * @param board The analysed board.
     * @param color The color of the analysed disk.
     * @return The best score, gained by putting the disks in different possible positions.
     */
    static double searchMaxGain(Board board, Color color) {
        Set<Point> possibleMoves = board.getPossibleMoves(color);
        if (possibleMoves.size() == 0) {
            return 0;
        } else if (possibleMoves.size() == 1) {
            return board.putNewDiskWithCounting(color, possibleMoves.iterator().next());
        } else {
            Board fakeBoard;
            double currentEvaluation;
            double maximum = 0;
            for (Point position : possibleMoves) {
                fakeBoard = new Board(board.getCells());
                currentEvaluation = fakeBoard.putNewDiskWithCounting(color, position);
                if (currentEvaluation > maximum) {
                    maximum = currentEvaluation;
                }
            }
            return maximum;
        }
    }
}
