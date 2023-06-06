package reversi.player;

import reversi.object.Color;

import java.util.Objects;


/**
 * Class of the robot layer.
 */
public class RobotPlayer extends Player {

    /**
     * The mode of the game: 1 - easy, 2 - hard.
     */
    private int mode;

    public RobotPlayer(String mode, Color color) {
        super(color);
        if (Objects.equals(mode, "easy")) {
            this.mode = 1;
        } else if (Objects.equals(mode, "hard")) {
            this.mode = 2;
        }
        this.name = "Robot";
    }


    /**
     * Get the mode of the player.
     *
     * @return 1 - easy, 2 - hard.
     */
    public int getMode() {
        return mode;
    }
}
