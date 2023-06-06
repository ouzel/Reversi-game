package reversi.game;

import reversi.logic.BoardController;
import reversi.object.Color;
import reversi.player.Player;

import java.awt.Point;


/**
 * Class for managing a specific round of the game.
 */
public class Round {

    /**
     * Console for interacting with the input of the user.
     */
    protected ConsoleInput console;

    /**
     * Contains the players of the game (user+user or user+robot).
     */
    protected Player[] players;

    /**
     * The current player at the moment.
     */
    protected int currentPlayer;

    /**
     * The controller of the board for the current round.
     */
    protected BoardController controller;

    public Round(ConsoleInput console) {
        this.console = console;
        controller = new BoardController();
        currentPlayer = 0;
    }


    /**
     * Method for starting a new round.
     */
    public void startRound() {
        System.out.println("The round has started.");
    }


    /**
     * Method for asking the controller to put the disk on board, when the position is correct.
     *
     * @param color The color of the disk.
     */
    protected void putDiskOnBoard(Color color) {
        controller.showPossibleMoves(color);
        System.out.println("The next player is: " + color.toString());
        Point positionOfDisk = console.readPosition();
        while (!controller.canPutDisk(players[currentPlayer].getColor(), positionOfDisk)) {
            System.out.println("Choose a different position");
            positionOfDisk = console.readPosition();
        }
        controller.putDisk(players[currentPlayer].getColor(), positionOfDisk);
    }


    /**
     * Method for getting the name of the user (later used in statistics).
     *
     * @return The name of the user.
     */
    public String inputName() {
        return console.readLine();
    }


    /**
     * Method for getting the name of the player.
     *
     * @param number The number of the player (first or second).
     * @return The name of the player.
     */
    public String getPlayerName(int number) {
        if (number == 1) {
            return players[0].getName();
        } else {
            return players[1].getName();
        }
    }


    /**
     * Method for getting the game result (points) of the given player.
     *
     * @param number The number of the player.
     * @return The score (in points) of the player.
     */
    public Integer getResult(int number) {
        if (number == 1) {
            return controller.getBoard().countDisks(Color.BLACK);
        }
        return controller.getBoard().countDisks(Color.WHITE);
    }


    /**
     * Method for ending the round.
     * Prints the board, prints which of the players is the winner (or tells the users it's a tie).
     */
    public void endRound() {
        System.out.println(controller.getBoard().toString());
        System.out.println("");
        System.out.println("");
        System.out.println("The round is over.");
        int result1 = getResult(1);
        int result2 = getResult(2);
        if (result1 == result2) {
            System.out.println("It's a tie!");
        } else if (result1 > result2) {
            System.out.println(getPlayerName(1) + " won this round with the score " + result1);
            System.out.println(getPlayerName(2) + " lost with the score " + result2);
        } else {
            System.out.println(getPlayerName(2) + " won this round with the score " + result2);
            System.out.println(getPlayerName(1) + " lost with the score " + result1);
        }
        System.out.println("");
        System.out.println("");
    }
}
