package reversi.object;

import java.awt.Point;
import java.util.*;


/**
 * Class of the gameboard.
 */
public class Board {

    /**
     * The state of cells (position: state).
     */
    private final Map<Point, Cell> cells;

    /**
     * The directions of neighbours of a cell.
     */
    private static final Point[] directions = {new Point(1, -1), new Point(1, 0),
            new Point(1, 1), new Point(0, 1), new Point(-1, 1),
            new Point(-1, 0), new Point(-1, -1), new Point(0, -1)};


    /**
     * Constructor for creating the starting board (default).
     */
    public Board() {
        cells = new HashMap<>(64);
        fillStartBoard();
    }

    /**
     * Constructor for creating a board by the given cells.
     *
     * @param cells The cells of the board.
     */
    public Board(Map<Point, Cell> cells) {
        this.cells = new HashMap<>(cells);
    }


    /**
     * Method for filling the starting board: 60 empty cells, 2 black disks and 2 white disks in the middle.
     */
    private void fillStartBoard() {
        Point point = new Point();
        for (point.x = 0; point.x < 8; point.x++) {
            for (point.y = 0; point.y < 8; point.y++) {
                if (point.x == 3 && point.y == 3) {
                    cells.put(new Point(point), Cell.SECOND);
                } else if (point.x == 3 && point.y == 4) {
                    cells.put(new Point(point), Cell.FIRST);
                } else if (point.x == 4 && point.y == 3) {
                    cells.put(new Point(point), Cell.FIRST);
                } else if (point.x == 4 && point.y == 4) {
                    cells.put(new Point(point), Cell.SECOND);
                } else {
                    cells.put(new Point(point), Cell.EMPTY);
                }
            }
        }
    }


    /**
     * Get the cells of the board.
     *
     * @return The cells of the board.
     */
    public Map<Point, Cell> getCells() {
        return new HashMap<>(cells);
    }


    /**
     * Checks if a cell has a neighbour in the given direction with some specific colour.
     *
     * @param current   The position of the cell.
     * @param direction One of the directions.
     * @param color     The needed color of a researched neighbour.
     * @return True or false.
     */
    private boolean isExistingNeighbour(Point current, Point direction, Color color) {
        Point neighbour = new Point(current.x + direction.x, current.y + direction.y);
        if (neighbour.x >= 0 && neighbour.x < 8 && neighbour.y >= 0 && neighbour.y < 8) {
            if (cells.get(neighbour).getColor() == color) {
                return true;
            }
        }
        return false;
    }


    /**
     * Checks whether the board is full (64 disks are already on the board).
     *
     * @return True or false;
     */
    public boolean isBoardFull() {
        for (Point position : cells.keySet()) {
            if (!cells.get(position).hasDisk()) {
                return false;
            }
        }
        return true;
    }


    /**
     * Get the possible moves, which can be made by a player with a specified color.
     *
     * @param color The analysed color.
     * @return A set of possible positions of the moves.
     */
    public Set<Point> getPossibleMoves(Color color) {
        Set<Point> possibleMoves = new HashSet<>();
        Point explorer;
        int countEnemies;
        for (Point current : cells.keySet()) {
            if (cells.get(current) == Cell.EMPTY) {
                for (Point direction : directions) {
                    explorer = current;
                    countEnemies = 0;
                    while (isExistingNeighbour(explorer, direction, color.opposite())) {
                        countEnemies += 1;
                        explorer = new Point(explorer.x + direction.x, explorer.y + direction.y);
                    }
                    if (isExistingNeighbour(explorer, direction, color) && countEnemies > 0) {
                        possibleMoves.add(current);
                        break;
                    }
                }
            }
        }
        return possibleMoves;
    }


    /**
     * Method for updating the state of the cell.
     *
     * @param cell     The needed state of the cell.
     * @param position The position of the cell.
     */
    public void updateCell(Cell cell, Point position) {
        cells.put(position, cell);
    }


    /**
     * Changes the colour of the cell (when the player inverts the colour of the cell by 'capturing' it.
     *
     * @param color    The color of the cell.
     * @param position The position of the cell.
     */
    public void changeColorOfCellTo(Color color, Point position) {
        if (color == Color.BLACK) {
            updateCell(Cell.FIRST, position);
        } else {
            updateCell(Cell.SECOND, position);
        }
    }


    /**
     * Putting the disk on board and changing the color of the captured neighbours.
     *
     * @param color    The color of the disk, which is put.
     * @param position The position, where to put the disk.
     */
    public void putNewDisk(Color color, Point position) {
        changeColorOfCellTo(color, position);
        Point explorer;
        for (Point direction : directions) {
            explorer = position;
            while (isExistingNeighbour(explorer, direction, color.opposite())) {
                explorer = new Point(explorer.x + direction.x, explorer.y + direction.y);
            }
            if (isExistingNeighbour(explorer, direction, color)) {
                explorer = position;
                while (isExistingNeighbour(explorer, direction, color.opposite())) {
                    explorer = new Point(explorer.x + direction.x, explorer.y + direction.y);
                    changeColorOfCellTo(color, explorer);
                }
            }
        }
    }


    /**
     * Method for putting the disk on board with counting the value of such move.
     * The value of the moves are specified in the file of the lecturer.
     *
     * @param color    The color of the disk.
     * @param position The position of the disk.
     * @return The value of putting this specific disk on the board.
     */
    public double putNewDiskWithCounting(Color color, Point position) {
        double counter = 0;
        changeColorOfCellTo(color, position);
        if ((position.x == 0 && position.y == 0) || (position.x == 7 && position.y == 7)) {
            counter += 0.8;
        } else if (position.x == 0 || position.y == 0 || position.x == 7 || position.y == 7) {
            counter += 0.4;
        }
        Point explorer;
        for (Point direction : directions) {
            explorer = position;
            while (isExistingNeighbour(explorer, direction, color.opposite())) {
                explorer = new Point(explorer.x + direction.x, explorer.y + direction.y);
            }
            if (isExistingNeighbour(explorer, direction, color)) {
                explorer = position;
                while (isExistingNeighbour(explorer, direction, color.opposite())) {
                    explorer = new Point(explorer.x + direction.x, explorer.y + direction.y);
                    changeColorOfCellTo(color, explorer);
                    if (explorer.x == 0 || explorer.y == 0 || explorer.x == 7 || explorer.y == 7) {
                        counter += 2;
                    } else {
                        counter += 1;
                    }
                }
            }
        }
        return counter;
    }


    /**
     * Counting the number of the disks of some color.
     *
     * @param color The color of the disks.
     * @return The number of disks.
     */
    public int countDisks(Color color) {
        int numberOfDisks = 0;
        for (Point current : cells.keySet()) {
            if (cells.get(current) == Cell.FIRST && color == Color.BLACK) {
                numberOfDisks += 1;
            } else if (cells.get(current) == Cell.SECOND && color == Color.WHITE) {
                numberOfDisks += 1;
            }
        }
        return numberOfDisks;
    }


    /**
     * How the board looks when printed.
     *
     * @return The view of the board.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        sb.append("rows");
        sb.append(System.lineSeparator());
        for (int row = 0; row < 8; row++) {
            sb.append("     -------------------------------------------------");
            sb.append(System.lineSeparator());
            sb.append(row + "    ");
            for (int column = 0; column < 8; column++) {
                sb.append("|  " + cells.get(new Point(row, column)) + "  ");
            }
            sb.append("|");
            sb.append(System.lineSeparator());
        }
        sb.append("     -------------------------------------------------");
        sb.append(System.lineSeparator());
        sb.append("        0     1     2     3     4     5     6     7     columns");
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}
