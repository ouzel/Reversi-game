package reversi;

import reversi.game.Game;

public class MainApplication {
    /**
     * The starting point of the app.
     *
     * @param args Args.
     */
    public static void main(String[] args) {
        try {
            (new Game()).start();
        } catch (Exception ex) {
            System.out.println("There was an error when playing the game.");
            System.out.println("If you want to play the game, restart the app.");
        }
    }
}
