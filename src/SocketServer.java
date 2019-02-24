import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * A Threaded server.
 *
 * No locks, no synchronization, no state.
 */

public class SocketServer {

    public static void main(String[] args) throws IOException {
        try (var listener = new ServerSocket(59898)) {
            System.out.println("Server is running...");
            var pool = Executors.newFixedThreadPool(10);
            while (true) {
                pool.execute(new RichServer(listener.accept()));
            }
        }
    }

    /* class must be static to be instantiable from main */
    private static class RichServer implements Runnable {
        private Socket socket;

        RichServer(Socket socket) {
            this.socket = socket;
        }

        /* You can't use try-with-resources since the socket was created on the main thread in
         * the constructor. Also, don't do too much work in the constructor since that's run on
         * the main thread. */
        @Override
        public void run() {
            System.out.println("Connected: " + this.socket);
            try {
                var in = new Scanner(socket.getInputStream());
                var out = new PrintWriter(socket.getOutputStream(), true);
                while (in.hasNextLine()) {
                    out.println("Server sends: " + in.nextLine());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try { socket.close(); } catch (IOException e) { e.printStackTrace(); }
                System.out.println("Socket: " + socket + " is closed.");
            }
        }
    }
}