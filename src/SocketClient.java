import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * In IntelliJ you can pass the command-line args under the Edit Configurations menu.
 */

public class SocketClient {

    public static void main(String[] args)throws IOException {
        if (args.length != 1) {
            System.err.println("Pass the server IP (not including the port) as the sole argument");
            return;
        }
        var socket = new Socket(args[0], 59050);
        var in = new Scanner(socket.getInputStream());
        System.out.println("Server response is " + in.nextLine());
    }
}
