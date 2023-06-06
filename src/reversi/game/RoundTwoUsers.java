package reversi.game;

import reversi.object.Color;
import reversi.player.Player;
import reversi.player.UserPlayer;


/**
 * Class for managing a round between 2 users.
 */
public class RoundTwoUsers extends Round {

    /**
     * Constructor for starting the round. Gets the name of the users and assigns the player roles.
     *
     * @param console Console for interacting with the users.
     */
    public RoundTwoUsers(ConsoleInput console) {
        super(console);
        System.out.print("Input the name of the first player: ");
        Player player1 = new UserPlayer(inputName(), Color.BLACK);
        System.out.print("Input the name of the second player: ");
        Player player2 = new UserPlayer(inputName(), Color.WHITE);
        players = new Player[2];
        players[0] = player1;
        players[1] = player2;
    }

    /**
     * Starting the round with 2 users.
     * Until the round is over, the players are asked to take turns and put the disks on the field.
     */
    @Override
    public void startRound() {
        while (!controller.isGameOver()) {
            if (controller.canPutAnyDisk(players[currentPlayer].getColor())) {
                putDiskOnBoard(players[currentPlayer].getColor());
            } else if (!controller.canPutAnyDisk(players[(currentPlayer + 1) % 2].getColor())) {
                System.out.println(controller.getBoard());
                System.out.println("None of the players can put the disk. The game is over.");
                endRound();
                break;
            }
            currentPlayer = (currentPlayer + 1) % 2;
        }
        endRound();
    }
}
