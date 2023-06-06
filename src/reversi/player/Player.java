package reversi.player;

import reversi.object.Color;


/**
 * Class of the player.
 */
public class Player {

    /**
     * The color of the player.
     */
    Color color;

    /**
     * The name of the player.
     */
    protected String name;

    public Player(Color color) {
        this.color = color;
    }


    /**
     * Get the color of the player.
     *
     * @return the color of the player.
     */
    public Color getColor() {
        return color;
    }


    /**
     * Get the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }
}
