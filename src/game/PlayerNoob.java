package game;

public class PlayerNoob extends Player {

    public PlayerNoob(char symbol) {

        super(symbol);
    }

    // number = row*size + colomn
    @Override
    public int turn(char[][] field, int fieldSize) {
        for(int i = 0; i < fieldSize; i++){
            for(int j = 0; j < fieldSize; j++){
                if(field[(fieldSize-1)/2][(fieldSize-1)/2]=='-'){
                    return (fieldSize*fieldSize-1)/2 ;


                }else if (field[i][j] =='-'){


                }
                { return j*fieldSize +i;

                }
            }
        }
        return 0;
    }

    @Override
    public String getName() {
        return "My player";
    }
}
