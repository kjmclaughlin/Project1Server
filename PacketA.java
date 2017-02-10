import java.nio.ByteBuffer;

/**
 * Created by Kevin McLaughlin on 1/23/2017.
 */
public class PacketA extends GenericPacket {


    public String payloadString;

    // message must be of the right length and format + not null
    public PacketA(byte[] message) {
        super(message);
        char[] payload = new char[12];
        for (int i = 12; i < 24; i++) {
            payload[i - 12] = (char)message[i];
        }
        payloadString = new String(payload);
    }

}
