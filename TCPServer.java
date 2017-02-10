import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Kevin McLaughlin on 1/23/2017.
 */
public class TCPServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;

    public TCPServer(int portNum) {
        try {
            serverSocket = new ServerSocket(portNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptConnection() {
        try {
           clientSocket  = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(byte[] b) {
        try {
            clientSocket.getOutputStream().write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] receiveMessage(int size) {
        byte[] message = new byte[size];
        try {
            clientSocket.getInputStream().read(message, 0, message.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public void close() {
        try {
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
