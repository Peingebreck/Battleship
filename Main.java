package battleship;

import java.util.Scanner;

public class Main {
    static final int n = 10;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Player[] players = {new Player(0, "Player 1", new Battlefield[n][n]),
                new Player(1, "Player 2", new Battlefield[n][n])};
        for (Player player : players) {
            initialFill(player.battlefield);
            System.out.printf("%s, place your ships on the game field\n\n", player.name);
            printField(player.battlefield, false);
            Ship.placeShip(player.battlefield);
            pass();
        }
        Game.playGame(players);
    }

    static void initialFill(Battlefield[][] battlefield) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                battlefield[i][j] = new Battlefield(null, "~");
            }
        }
    }

    static void printField(Battlefield[][] battlefield, boolean hide) {
        System.out.print(" ");
        for (int i = 1; i <= n; i++) {
            System.out.printf(" %d", i);
        }
        System.out.println();
        char c = 'A';
        for (int i = 0; i < n; i++) {
            System.out.printf("%c ", c);
            for (int j = 0; j < n; j++) {
                if (hide) {
                    System.out.print("O".equals(battlefield[i][j].status) ? "~ " : battlefield[i][j].status + " ");
                } else {
                    System.out.print(battlefield[i][j].status + " ");
                }
            }
            System.out.println();
            c++;
        }
    }

    static void pass() {
        System.out.println("\nPress Enter and pass the move to another player");
        sc.nextLine();
    }
}
