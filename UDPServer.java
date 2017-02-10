import java.io.IOException;
import java.net.*;

/**
 * Created by Kevin McLaughlin on 1/22/2017.
 */
public class UDPServer {

    private DatagramSocket socket;

    public UDPServer(int portNum) {
        try {
            socket = new DatagramSocket(portNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTimeout() {
        try {
            //TODO: Set this to 3000
            socket.setSoTimeout(3000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public DatagramPacket receiveMessage(int size) {
        byte[] message = new byte[size];
        DatagramPacket p = new DatagramPacket(message, size);
        try {
            socket.receive(p);
            if (p.getLength() != size) {
                return null;
            }
        } catch (SocketTimeoutException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    public void sendMessage(byte[] message, InetAddress address, int portNum) {
        DatagramPacket packet = null;
        try {
            packet = new DatagramPacket(message, message.length, address, portNum);
            socket.send(packet);
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        socket.close();
    }

}
