package battleship;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static battleship.Main.pass;

public class Game {
    static Pattern inputPattern = Pattern.compile("[A-J]([1-9]|10)");
    static final int totalShipsLength = 17;

    static void playGame(Player[] players) {
        Scanner sc = new Scanner(System.in);
        Player player = players[0];
        Player opponent;
        while (true) {
            opponent = player == players[0] ? players[1] : players[0];
            Main.printField(opponent.battlefield, true);
            System.out.println("---------------------");
            Main.printField(player.battlefield, false);
            System.out.printf("\n%s, it's your turn:\n", player.name);
            String input = sc.nextLine().toUpperCase();
            Matcher inputMatcher = inputPattern.matcher(input);
            if (inputMatcher.matches()) {
                int[] shot = new int[2];
                shot[0] = input.charAt(0) - 'A';
                shot[1] = Integer.parseInt(input.substring(1)) - 1;
                if ("O".equals(opponent.battlefield[shot[0]][shot[1]].status)) {
                    opponent.battlefield[shot[0]][shot[1]].status = "X";
                    opponent.battlefield[shot[0]][shot[1]].ship.hits[opponent.index]++;

                    if (isFinished(opponent.index)) {
                        System.out.println("\nYou sank the last ship. You won. Congratulations!\n");
                        break;
                    } else if (opponent.battlefield[shot[0]][shot[1]].ship.hits[opponent.index] == opponent.battlefield[shot[0]][shot[1]].ship.length) {
                        System.out.println("\nYou sank a ship!");
                    } else {
                        System.out.println("\nYou hit a ship!");
                    }
                } else if ("X".equals(opponent.battlefield[shot[0]][shot[1]].status)) {
                    System.out.println("\nYou hit a ship!");
                } else {
                    opponent.battlefield[shot[0]][shot[1]].status = "M";
                    System.out.println("\nYou missed!");
                }
                player = player == players[0] ? players[1] : players[0];
                pass();
            } else {
                System.out.println("\nError! You entered the wrong coordinates! Try again:");
            }
        }
    }

    static boolean isFinished(int index) {
        int count = 0;
        for (Ship ship : Ship.values()) {
            count += ship.hits[index];
        }
        return count == totalShipsLength;
    }
}
