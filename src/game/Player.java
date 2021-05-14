package game;

public abstract class Player {
    protected char symbol;

    public Player(char symbol) {
        this.symbol = symbol;
    }

    public abstract int turn(char[][] field, int fieldSize);

    public abstract String getName();

    public final char getSymbol() {
        return this.symbol;
    }
}
