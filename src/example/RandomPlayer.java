package example;

import game.Player;
import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends Player {
    private Random rand;

    public RandomPlayer(char symbol) {
        super(symbol);
        this.rand = new Random();
    }

    @Override
    public int turn(char[][] field, int fieldSize) {
        ArrayList<Integer> emptyCells = new ArrayList<>();

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (field[i][j] == '-') {
                    emptyCells.add(i * fieldSize + j);
                }
            }
        }

        int v = rand.nextInt(emptyCells.size());

        return emptyCells.get(v);
    }

    @Override
    public String getName() {
        return "Random";
    }
}
