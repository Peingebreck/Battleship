package battleship;

public class Player {
    int index;
    String name;
    Battlefield[][] battlefield;

    public Player(int index, String name, Battlefield[][] battlefield) {
        this.index = index;
        this.name = name;
        this.battlefield = battlefield;
    }
}
