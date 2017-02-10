import java.net.DatagramPacket;
import java.nio.ByteBuffer;

/**
 * Created by Kevin McLaughlin on 1/22/2017.
 */
public class Main {

    public static void main(String[] args) {
        partA();
    }

    public static void partA() {
        UDPServer server = new UDPServer(12235);
        while(true) {
            DatagramPacket responsePacket = server.receiveMessage(24);
            PacketA p = new PacketA(responsePacket.getData());
            if (MessageVerifier.verifyMessageA(p)) {
                ByteBuffer outgoingMessage =  MessageBuilder.buildHeader(16, p.psecret, (short)2, p.studentNum);
                int num = (int)((Math.random() * 30) + 5);
                int len = (int)((Math.random() * 50) + 5);
                int portNum = (int)((Math.random() * 1000) + 3000);
                int secret = (int)((Math.random() * 490) + 10);
                outgoingMessage.putInt(num).putInt(len).putInt(portNum).putInt(secret);
                System.out.println("New Server on port number: " + portNum);
                UDPServer secondaryServer = new UDPServer(portNum);
                secondaryServer.setTimeout();
                server.sendMessage(outgoingMessage.array(), responsePacket.getAddress(), responsePacket.getPort());
                new ClientHandler(responsePacket, secondaryServer, p, num, len, portNum, secret).start();
            }

        }
    }

}
