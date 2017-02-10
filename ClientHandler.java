import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * Created by Kevin McLaughlin on 1/22/2017.
 */
public class ClientHandler extends Thread {

    private InetAddress clientAddress;
    private int clientPort;
    private PacketA pA;
    private int numB;
    private int lenB;
    private int portNumB;
    private int secretA;
    private UDPServer server;

    public ClientHandler(DatagramPacket packet, UDPServer server, PacketA pA, int numB, int lenB, int portNumB, int secretA) {
        clientAddress = packet.getAddress();
        this.pA = pA;
        this.numB = numB;
        this.lenB = lenB;
        this.portNumB = portNumB;
        this.secretA = secretA;
        this.server = server;
        clientPort = packet.getPort();
    }

    public void run() {
        int i = 0;
        int payloadLength = MessageBuilder.getPayloadLength(lenB + 4);
        System.out.println(numB);
        while(i < numB) {
            DatagramPacket incomingPacket = server.receiveMessage(payloadLength + 12);
            for (int j = 0; j < incomingPacket.getData().length; j++) {
                System.out.print(incomingPacket.getData()[j] + " ");
            }
            System.out.println();
            if (incomingPacket == null) {
                server.close();
                return;
            }
            PacketB packetB = new PacketB(incomingPacket.getData());
            if (!MessageVerifier.verifyMessageB(packetB, lenB + 4, secretA, i, pA.studentNum)) {
                server.close();
                return;
            }
            boolean acknowledgePacket = (int)(Math.random() * 2) == 1 ? true : false;
            if (acknowledgePacket) {
                ByteBuffer outgoingMessage = MessageBuilder.buildHeader(4, secretA, (short)2, pA.studentNum);
                outgoingMessage.putInt(i);
                clientAddress = incomingPacket.getAddress();
                clientPort = incomingPacket.getPort();
                server.sendMessage(outgoingMessage.array(), clientAddress, clientPort);
                i++;
            }
        }
        int secretB = (int)((Math.random() * 490) + 10);
        int portNumC = (int)((Math.random() * 1000) + 4000);
        ByteBuffer outgoingMessage = MessageBuilder.buildHeader(8, secretA, (short)2, pA.studentNum);
        System.out.println(clientAddress + "  " + clientPort);
        outgoingMessage.putInt(portNumC).putInt(secretB);
        TCPServer tcpServer = new TCPServer(portNumC);
        server.sendMessage(outgoingMessage.array(), clientAddress, clientPort);
        server.close();
        tcpServer.acceptConnection();
        outgoingMessage = MessageBuilder.buildHeader(13, secretB, (short)2, pA.studentNum);
        int numC = (int)((Math.random() * 40) + 5);
        int lenC = (int)((Math.random() * 50) + 5);
        int secretC = (int)((Math.random() * 490) + 10);
        char c = (char)((Math.random() * 95) + 5);
        outgoingMessage.putInt(numC).putInt(lenC).putInt(secretC);
        byte[] outgoingMessageBytes = outgoingMessage.array();
        outgoingMessageBytes[24] = (byte)c;
        tcpServer.sendMessage(outgoingMessageBytes);

        i = 0;
        while(i < numC) {
            byte[] incomingMessage = tcpServer.receiveMessage(MessageBuilder.getPayloadLength(lenC) + 12);
            if (incomingMessage == null || incomingMessage.length != 12 + MessageBuilder.getPayloadLength(lenC)) {
                tcpServer.close();
                return;
            }
            PacketD packetD = new PacketD(incomingMessage);
            char[] payloadChars = new char[lenC];
            for (int j = 0; j < payloadChars.length; j++) {
                payloadChars[j] = c;
            }
            if (MessageVerifier.verifyMessageD(packetD, lenC, secretC, pA.studentNum, payloadChars)) {

            } else {
                tcpServer.close();
                return;
            }
            i++;
        }
        outgoingMessage = MessageBuilder.buildHeader(4, secretC, (short)2, pA.studentNum);
        outgoingMessage.putInt((int)((Math.random() * 490) + 10));
        tcpServer.sendMessage(outgoingMessage.array());
    }

}
