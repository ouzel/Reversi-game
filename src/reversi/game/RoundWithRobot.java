package reversi.game;

import reversi.logic.ArtificialIntelligence;
import reversi.object.Board;
import reversi.object.Color;
import reversi.player.Player;
import reversi.player.RobotPlayer;
import reversi.player.UserPlayer;

import java.awt.Point;


/**
 * Class for managing a round between a user and a robot.
 * In this type of round the user can revert their move, up until the game is over.
 */
public class RoundWithRobot extends Round {

    /**
     * Constructor of the round. Gets the name of the user and assigns the player roles.
     * The user is always the first player (plays with black disks).
     *
     * @param console Console for interacting with the user.
     * @param mode    Difficulty of the robot: easy or hard.
     */
    public RoundWithRobot(ConsoleInput console, String mode) {
        super(console);
        Player player2 = new RobotPlayer(mode, Color.WHITE);
        System.out.print("Input your name: ");
        Player player1 = new UserPlayer(inputName(), Color.BLACK);
        players = new Player[2];
        players[0] = player1;
        players[1] = player2;
    }


    /**
     * Method for starting a round between a user and a robot.
     * Until the round is over, the players are asked to take turns and put the disks on the field.
     */
    @Override
    public void startRound() {
        controller.updateHistory();
        while (!controller.isGameOver()) {
            if (controller.canPutAnyDisk(players[0].getColor())) {
                putDiskOnBoardByUser();
            }
            if (controller.canPutAnyDisk(players[1].getColor()) && !controller.isGameOver()) {
                putDiskOnBoardByRobot();
            }
            controller.updateHistory();
        }
        endRound();
    }


    /**
     * Method for asking the controller to put the disk on board, when the position is correct.
     * If the input was (-1 -1), the move is reverted.
     */
    private void putDiskOnBoardByUser() {
        controller.showPossibleMoves(Color.BLACK);
        System.out.println("Your turn.");
        System.out.println("To undo the last move input -1 -1");
        Point positionOfDisk = console.readPosition();
        while (positionOfDisk.getX() == -1 && positionOfDisk.getY() == -1) {
            controller.eraseLastStepFromHistory();
            controller.showPossibleMoves(Color.BLACK);
            positionOfDisk = console.readPosition();
        }
        while (!controller.canPutDisk(players[0].getColor(), positionOfDisk)) {
            System.out.println("Choose a different position");
            positionOfDisk = console.readPosition();
        }
        controller.putDisk(Color.BLACK, positionOfDisk);
    }


    /**
     * Method for asking the controller to put the disk on board, after the best position is found by the robot.
     */
    private void putDiskOnBoardByRobot() {
        Point bestPosition;
        Board copyBoard = new Board(controller.getBoard().getCells());
        if (((RobotPlayer) players[1]).getMode() != 2) {
            bestPosition = ArtificialIntelligence.searchBestPositionEasyMode(copyBoard, Color.WHITE);
        } else {
            bestPosition = ArtificialIntelligence.searchBestPositionHardMode(copyBoard, Color.WHITE);
        }
        if (bestPosition.x != -1 && bestPosition.y != -1) {
            controller.putDisk(Color.WHITE, bestPosition);
            System.out.println("Robot placed the disk on " + bestPosition.x + " " + bestPosition.y);
        } else {
            System.out.println("Robot couldn't place the disk.");
        }
    }
}
