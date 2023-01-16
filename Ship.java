package battleship;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public enum Ship {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    final String name;
    final int length;
    final int[] hits = new int[2];

    Ship(String name, int length) {
        this.name = name;
        this.length = length;
    }

    static final Pattern inputPattern = Pattern.compile("[A-J]([1-9]|10) [A-J]([1-9]|10)");

    static void placeShip(Battlefield[][] battlefield) {
        Scanner sc = new Scanner(System.in);
        for (Ship ship : Ship.values()) {
            System.out.printf("\nEnter the coordinates of the %s (%d cells):\n", ship.name, ship.length);
            while (true) {
                String coordinates = sc.nextLine().toUpperCase();
                if (checkShip(ship, coordinates, battlefield)) {
                    break;
                }
            }
            System.out.println();
            Main.printField(battlefield, false);
        }
    }

    static boolean checkShip(Ship ship, String coordinates, Battlefield[][] battlefield) {
        boolean correct = false;
        Matcher inputMatcher = inputPattern.matcher(coordinates);
        if (!inputMatcher.matches()) {
            System.out.println("\nError! You entered the wrong coordinates! Try again:");
        } else {
            String[] input = coordinates.split(" ");
            int[] start = new int[2];
            int[] end = new int[2];
            start[0] = Math.min(input[0].charAt(0) - 'A', input[1].charAt(0) - 'A');
            start[1] = Math.min(Integer.parseInt(input[0].substring(1)) - 1, Integer.parseInt(input[1].substring(1)) - 1);
            end[0] = Math.max(input[0].charAt(0) - 'A', input[1].charAt(0) - 'A');
            end[1] = Math.max(Integer.parseInt(input[0].substring(1)) - 1, Integer.parseInt(input[1].substring(1)) - 1);
            int actualLength;
            if (start[0] != end[0] && start[1] != end[1]) {
                System.out.println("\nError! Wrong ship location! Try again:");
            } else {
                if (start[0] == end[0]) {
                    actualLength = end[1] - start[1] + 1;
                } else {
                    actualLength = end[0] - start[0] + 1;
                }
                if (actualLength != ship.length) {
                    System.out.printf("\nError! Wrong length of the %s! Try again:\n", ship.name);
                } else {
                    int[] check = new int[4];
                    check[0] = start[0] == 0 ? 0 : start[0] - 1;
                    check[1] = start[1] == 0 ? 0 : start[1] - 1;
                    check[2] = end[0] == 9 ? 9 : end[0] + 1;
                    check[3] = end[1] == 9 ? 9 : end[1] + 1;
                    boolean tooClose = false;
                    for (int i = check[0]; i <= check[2]; i++) {
                        for (int j = check[1]; j <= check[3]; j++) {
                            if (!"~".equals(battlefield[i][j].status)) {
                                System.out.println("\nError! You placed it too close to another one. Try again:");
                                tooClose = true;
                                break;
                            }
                        }
                        if (tooClose) break;
                    }
                    if (!tooClose) {
                        correct = true;
                        for (int i = start[0]; i <= end[0]; i++) {
                            for (int j = start[1]; j <= end[1]; j++) {
                                battlefield[i][j].status = "O";
                                battlefield[i][j].ship = ship;
                            }
                        }
                    }
                }
            }
        }
        return correct;
    }
}