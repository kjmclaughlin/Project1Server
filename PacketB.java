import java.nio.ByteBuffer;

/**
 * Created by Kevin McLaughlin on 1/23/2017.
 */
public class PacketB extends GenericPacket {

    public int packet_id;

    public PacketB(byte[] message) {
        super(message);
        packet_id = ByteBuffer.wrap(message, 12, 4).getInt();
    }

}
