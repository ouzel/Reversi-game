package reversi.game;

import java.awt.*;
import java.io.InputStreamReader;
import java.util.Scanner;


/**
 * Class for reading the input from console.
 */
public class ConsoleInput {

    /**
     * The reader of the input, provided by the user.
     */
    private static Scanner reader;


    public ConsoleInput() {
        reader = new Scanner(new InputStreamReader(System.in));
    }


    /**
     * Method for reading a line.
     *
     * @return The line, which was read.
     */
    public String readLine() {
        return reader.nextLine();
    }


    /**
     * Method for reading the position (2 coordinates).
     *
     * @return The Point of the read positions.
     */
    public Point readPosition() {
        System.out.println("Input position: ");
        String line = reader.nextLine().trim();
        try {
            if (line.equals("-1 -1")) {
                return new Point(-1, -1);
            }
            String[] coords = line.split(" ");
            int coordX = Integer.parseInt(coords[0]);
            int coordY = Integer.parseInt(coords[1]);
            if (coordX >= 0 && coordX < 8 && coordY >= 0 && coordY < 8) {
                return new Point(coordX, coordY);
            } else {
                System.out.println("Mistake when entering the position. Input again.");
                System.out.println("You should type two digits 0-7, separated by ' '.");
                return readPosition();
            }
        } catch (Exception e) {
            System.out.println("Mistake when entering the position. Input again.");
            System.out.println("You should type two digits 0-7, separated by ' '.");
            return readPosition();
        }
    }
}
