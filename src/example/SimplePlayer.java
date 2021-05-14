package example;

import game.Player;

public class SimplePlayer extends Player {

    public SimplePlayer(char symbol) {
        super(symbol);
    }

    @Override
    public int turn(char[][] field, int fieldSize) {
        for (int i = fieldSize - 1; i >= 0; i--) {
            for (int j = fieldSize - 1; j >= 0; j--) {
                if (field[i][j] == '-') {
                    return i * fieldSize + j;
                }
            }
        }

        return 0;
    }

    @Override
    public String getName() {
        return "Simple";
    }
}
