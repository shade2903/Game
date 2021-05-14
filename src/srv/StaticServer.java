package srv;

import java.io.*;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;
import java.nio.file.Files;

public class StaticServer {
    private int port;
    private HttpServer server = null;

    public StaticServer(int port) throws IOException {
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(this.port), 0);
    }

    public void serve() {
        System.out.println("server started at " + this.port);
        this.server.createContext("/game", new GameHandler());
        this.server.setExecutor(null);
        this.server.start();
    }

    public void createContext(String endpoint, HttpHandler handler) {
        this.server.createContext(endpoint, handler);
    }

    private static class GameHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            File file = new File("index.html");
            he.sendResponseHeaders(200, file.length());
            try (OutputStream os = he.getResponseBody()) {
                Files.copy(file.toPath(), os);
                os.close();
            }
        }
    }

    public static String readBody(HttpExchange he) throws IOException {
        InputStreamReader isr =  new InputStreamReader(he.getRequestBody(),"utf-8");
        BufferedReader br = new BufferedReader(isr);
        int b;
        StringBuilder buf = new StringBuilder(512);
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }

        br.close();
        isr.close();

        return buf.toString();
    }
}