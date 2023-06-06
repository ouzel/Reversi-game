package reversi.player;

import reversi.object.Color;


/**
 * Class of a user player.
 */
public class UserPlayer extends Player {
    public UserPlayer(String name, Color color) {
        super(color);
        this.name = name;
    }
}
