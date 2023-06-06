package reversi.game;

import java.util.HashMap;
import java.util.Map;

public class Game {

    /**
     * Console, which is used to interact with the user.
     */
    private final ConsoleInput console;

    /**
     * Map with statistics (name: best result).
     */
    private final Map<String, Integer> statistics;

    public Game() {
        console = new ConsoleInput();
        statistics = new HashMap<>();
    }


    /**
     * The start of the game.
     */
    public void start() {
        System.out.println();
        System.out.println("The game has started.");
        printInstruction();
        readCommand();
    }


    /**
     * Printing the main instructions of reversi.
     */
    public static void printInstruction() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("                           Simple rules:");
        System.out.println("Reversi is a board game.");
        System.out.println("Players take turns and put disks on the board.");
        System.out.println("Each player should enter the position where he wants to put a disk.");
        System.out.println("The format: {row column}, for example '2 3'.");
        System.out.println("Possible moves are shown as * and printed under the board.");
        System.out.println("Your aim is to have more disks on the field than your opponent.");
        System.out.println();
        System.out.println("To start with, choose the needed command, then start the round.");
        System.out.println("Statistics presents the best result of each player (even if they lost in the round).");
        System.out.println();
        System.out.println("When playing with the robot, you can revert your move by printing '-1 -1'.");
        System.out.println("You can revert any move until the game is over.");
        System.out.println("Reverting the move is not possible in the game of 2 users (because of logic).");
        System.out.println("If you're playing with the robot, you'll always be the first player (black disks).");
        System.out.println("                            Good luck!");
        System.out.println();
        System.out.println();
        System.out.println();
    }


    /**
     * Method for printing the statistics to console (name: best result).
     */
    public void printStatistics() {
        System.out.println("The best result of each player.");
        System.out.println();
        for (String name : statistics.keySet()) {
            System.out.println(name + ": " + statistics.get(name));
        }
        System.out.println();
        System.out.println();
    }


    /**
     * Method used to update statistics.
     * Checks, whether the result is the best for each of the users.
     *
     * @param name   Name of the player.
     * @param result The new result of the player (is added to statistics if it is the highest overall).
     */
    public void updateResult(String name, Integer result) {
        if (statistics.containsKey(name)) {
            if (statistics.get(name) < result) {
                statistics.put(name, result);
            }
        } else {
            statistics.put(name, result);
        }
    }


    /**
     * Method for reading the commands:
     * starting a new round;
     * asking to show statistics or instruction;
     * exiting the game.
     */
    public void readCommand() {
        System.out.println("Input your command:");
        System.out.println("-h   - read the instruction;");
        System.out.println("-r1  - start new round for 2 users;");
        System.out.println("-r2  - start new round with a robot, level: easy;");
        System.out.println("-r3  - start new round with a robot, level: hard;");
        System.out.println("-s   - show statistics;");
        System.out.println("-q   - quit");
        System.out.println();
        System.out.print("Your input: ");
        String input = console.readLine().trim().toLowerCase();
        if (input.equals("-h")) {
            printInstruction();
        } else if (input.equals("-s")) {
            printStatistics();
        } else if (input.equals("-r1")) {
            Round round = new RoundTwoUsers(console);
            round.startRound();
            updateResult(round.getPlayerName(1), round.getResult(1));
            updateResult(round.getPlayerName(2), round.getResult(2));
        } else if (input.equals("-r2")) {
            Round round = new RoundWithRobot(console, "easy");
            round.startRound();
            updateResult(round.getPlayerName(1), round.getResult(1));
        } else if (input.equals("-r3")) {
            Round round = new RoundWithRobot(console, "hard");
            round.startRound();
            updateResult(round.getPlayerName(1), round.getResult(1));
        } else if (input.equals("-q")) {
            return;
        } else {
            System.out.println("Wrong input. Enter the command again.");
        }
        readCommand();
    }
}
