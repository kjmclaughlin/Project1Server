import java.nio.ByteBuffer;

/**
 * Created by Kevin McLaughlin on 1/23/2017.
 */
public class GenericPacket {

    public int payloadLen;
    public int psecret;
    public short step;
    public short studentNum;

    public GenericPacket(byte[] message) {
        payloadLen = ByteBuffer.wrap(message, 0, 4).getInt();
        psecret = ByteBuffer.wrap(message, 4, 4).getInt();
        step = ByteBuffer.wrap(message, 8, 2).getShort();
        studentNum = ByteBuffer.wrap(message, 10, 2).getShort();
    }

}
