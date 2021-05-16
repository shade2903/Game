package game;

public class PlayerNoob extends Player {

    public PlayerNoob(char symbol) {

        super(symbol);
    }


    @Override
    public int turn(char[][] field, int fieldSize) {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (field[(fieldSize - 1) / 2][(fieldSize - 1) / 2] == '-') {
                    return (fieldSize * fieldSize - 1) / 2;
                } else if (field[(fieldSize - 1) / 2][(fieldSize - 1) / 2] == this.symbol && field[fieldSize * 0][fieldSize - 1] == '-') {
                    return fieldSize - 1;
                } else if (field[(fieldSize - 1) / 2][(fieldSize - 1) / 2] == this.symbol && field[fieldSize * 0][fieldSize - 1] == this.symbol && field[fieldSize - 1][fieldSize * 0] == '-') { //выйгрыш по диоганали
                    return fieldSize * 2;
                } else if (field[fieldSize * 0][fieldSize * 0] == '-') { //ставим 0 если пусто
                    return fieldSize*0;
                } else if (field[fieldSize * 0][fieldSize * 0] == this.symbol &&field[fieldSize * 0][fieldSize - 1] == this.symbol&& field[fieldSize *0][(fieldSize - 1)/2] == '-') { //выигрывает верхняя строка
                    return (fieldSize-1)/2;
                } else if (field[(fieldSize -1)/2][fieldSize - 1]  == '-') {
                    return fieldSize*2  - 1;
                } else if (field[fieldSize * 0][fieldSize * 0] == this.symbol && field[(fieldSize - 1) / 2][fieldSize * 0] == '-') {
                    return fieldSize;
                } else if (field[(fieldSize + 1) / 2][fieldSize * 0] == '-') {
                    return fieldSize*2;
                } else if (field[fieldSize*0][(fieldSize+1)/2]=='-'){
                    return (fieldSize-1)/2;
                } else if (field[fieldSize-1][(fieldSize+1)/2]=='-'){
                    return fieldSize*2 +1;
                } else if (field[fieldSize-1][fieldSize-1]=='-'){
                    return fieldSize*fieldSize;
                } else if  (field[fieldSize * 0][fieldSize * 0] == this.symbol &&field[fieldSize * 0][(fieldSize - 1)/2] == this.symbol&& field[fieldSize *0][fieldSize - 1] == '-'){
                    return fieldSize-1;
                } else if (field[fieldSize-1][(fieldSize-1)/2]=='-') {
                    return fieldSize*fieldSize +1;

                } else if (field[fieldSize*0][(fieldSize-1)/2]=='-') {
                    return (fieldSize -1)/2;

                } else if (field[i][j]=='-'){
                    return fieldSize*i + j;
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
