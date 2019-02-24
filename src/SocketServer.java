import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Date;

/**
 * A simple socket server that prints a date to the output stream.
 *
 * Try-With-Resources will close the resource automatically.
 */

public class SocketServer {

    public static void main(String[] args) throws IOException {
        try (var listener = new ServerSocket(59050)) {
            System.out.println("The date server is running...");
            while (true) {
                try (var socket = listener.accept()) {
                    var out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(new Date().toString()); // note that println will flush while print will not.
                }
            }
        }
    }
}
