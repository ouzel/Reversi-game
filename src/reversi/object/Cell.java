package reversi.object;


/**
 * Class of a cell.
 */
public enum Cell {
    FIRST(Color.BLACK.pixel()),
    SECOND(Color.WHITE.pixel()),
    POSSIBLE('*'),
    EMPTY(' ');
    private final char symbol;

    Cell(char symbol) {
        this.symbol = symbol;
    }


    /**
     * Method says whether there is a real disk in the cell.
     *
     * @return True or false.
     */
    public boolean hasDisk() {
        return (this == FIRST || this == SECOND);
    }


    /**
     * Get the color of the cell, if specified.
     *
     * @return The color of the disk in the cell.
     */
    public Color getColor() {
        if (this == SECOND) {
            return Color.WHITE;
        } else if (this == FIRST) {
            return Color.BLACK;
        } else {
            return null;
        }
    }


    /**
     * How the board looks when printed.
     *
     * @return The view of the cell.
     */
    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
