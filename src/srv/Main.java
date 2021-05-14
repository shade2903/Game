package srv;

import game.Player;
import com.sun.net.httpserver.*;
import java.io.*;

import example.*;
import game.PlayerNoob;

public class Main {
    private static int fieldSize = 4;
    private static char[][] field;
    private static boolean isFirstPlayerTurn = true;

    private static Player Player1 = new RandomPlayer('X');
    private static Player Player2 = new PlayerNoob('O');

    public static void main(String []args) {
        StaticServer server = null;
        try {
            server = new StaticServer(9000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        server.createContext("/player/1", new GamePlayersHandler(Player1));
        server.createContext("/player/2", new GamePlayersHandler(Player2));
        server.createContext("/player/1/symbol", new GamePlayerSymbolHandler(Player1));
        server.createContext("/player/2/symbol", new GamePlayerSymbolHandler(Player2));
        server.createContext("/start", new GameStartHandler());
        server.createContext("/state", new GameStateHandler());
        server.serve();
    }

    private static void initGame(int size) {
        field = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = '-';
            }
        }

        isFirstPlayerTurn = true;
    }

    private static String fieldToString() {
        String buffer = "";
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                buffer += field[i][j] + "*";
            }
        }

        return buffer.substring(0, buffer.length() - 1);
    }

    private static char[][] copyField(char[][] field, int fieldSize) {
        char[][] copy = new char[fieldSize][fieldSize];

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                copy[i][j] = field[i][j];
            }
        } 

        return copy;
    }

    private static class GamePlayersHandler implements HttpHandler {
        private Player player;

        public GamePlayersHandler(Player player) {
            this.player = player;
        }

        @Override
        public void handle(HttpExchange he) throws IOException {
            he.sendResponseHeaders(200, player.getName().length());
            OutputStream os = he.getResponseBody();
            os.write(player.getName().getBytes());
            os.close();
        }
    }

    private static class GamePlayerSymbolHandler implements HttpHandler {
        private Player player;

        public GamePlayerSymbolHandler(Player player) {
            this.player = player;
        }

        @Override
        public void handle(HttpExchange he) throws IOException {
            he.sendResponseHeaders(200, 1);
            OutputStream os = he.getResponseBody();
            os.write(player.getSymbol());
            os.close();
        }
    }

    private static class GameStartHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            String body = "";
            try {
                body = StaticServer.readBody(he);
            } catch (Exception e) {
                e.printStackTrace();
                he.sendResponseHeaders(500, 0);
                return;
            }
            he.sendResponseHeaders(200, 0);

            try {
                fieldSize = Integer.parseInt(body);
            } catch (Exception e) {
                e.printStackTrace();
            }

            initGame(fieldSize);
        }
    }

    private static class GameStateHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            OutputStream os = he.getResponseBody();
            String resp = "";

            try {
                resp = makeTurn();

                he.sendResponseHeaders(200, resp.length());
            } catch (Exception e) {
                resp = e.getMessage();
                he.sendResponseHeaders(500, resp.length());
            }

            os.write(resp.getBytes());
            os.close();
        }

        private String makeTurn() throws Exception {
            Player p = isFirstPlayerTurn ? Player1 : Player2;
            isFirstPlayerTurn = !isFirstPlayerTurn;

            int turn = -1;
            try {
                turn = p.turn(copyField(field, fieldSize), fieldSize);
            } catch (Exception e) {
                String resp = String.format("%s%%Unexpected exception: %s", p.getName(), e.getMessage());
                throw new Exception(resp);
            }
            
            if (turn < 0 || turn > field.length * field.length - 1) {
                String resp = String.format("%s%%Invalid turn: %d", p.getName(), turn);
                throw new Exception(resp);
            }

            int row = turn / fieldSize;
            int column = turn % fieldSize;

            if (field[row][column] != '-') {
                String resp = String.format("%s%%Cell %dx%d already has another value: %c", p.getName(), row, column, field[row][column]);
                throw new Exception(resp);
            }

            field[row][column] = p.getSymbol();

            return String.format("%s%%set %c to %dx%d cell%%%s", p.getName(), p.getSymbol(), row, column, fieldToString());
        }
    }
}
