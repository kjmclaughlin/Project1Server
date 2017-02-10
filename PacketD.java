import java.nio.ByteBuffer;

/**
 * Created by Kevin McLaughlin on 1/23/2017.
 */
public class PacketD extends GenericPacket {

    public int totalPayloadLength;
    public char[] payload;

    public PacketD(byte[] message) {
        super(message);
        totalPayloadLength = message.length - 12;
        payload = new char[this.payloadLen];
        for(int i = 0; i < payload.length; i++) {
            payload[i] = (char)message[i + 12];
        }
    }

}
