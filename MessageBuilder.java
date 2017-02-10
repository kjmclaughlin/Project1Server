import java.nio.ByteBuffer;

/**
 * Created by Kevin McLaughlin on 1/23/2017.
 */
public class MessageBuilder {

    public static ByteBuffer buildHeader(int payloadLen, int psecret, short step, short studentNum) {
        ByteBuffer b = ByteBuffer.allocate(12 + getPayloadLength(payloadLen)).putInt(payloadLen).putInt(psecret).putShort(step).putShort(studentNum);
        return b;
    }

    public static int getPayloadLength(int payloadLength) {
        int length = payloadLength;
        if (payloadLength % 4 > 0) {
            length += 4 - (payloadLength % 4);
        }
        return length;
    }
}
