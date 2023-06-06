package reversi.object;


public enum Color {
    BLACK('○'),
    WHITE('●');

    private final char pixel;


    Color(char pixel) {
        this.pixel = pixel;
    }


    /**
     * Returning the pixel of the color.
     *
     * @return The pixel of the color.
     */
    public char pixel() {
        return this.pixel;
    }


    /**
     * The opposite color, since there are only 2 used in the game.
     *
     * @return
     */
    public Color opposite() {
        if (this == BLACK) {
            return WHITE;
        } else if (this == WHITE) {
            return BLACK;
        }
        return null;
    }


    /**
     * The view of the Color.
     *
     * @return The view of the color.
     */
    @Override
    public String toString() {
        return String.valueOf(pixel);
    }
}
