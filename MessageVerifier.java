import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Kevin McLaughlin on 1/23/2017.
 */
public class MessageVerifier {

    public static boolean verifyMessageA(PacketA p) {
        return p.payloadString.equals("hello world\0") && p.payloadLen == 12 && p.psecret == 0 && p.step == 1;
    }

    public static boolean verifyMessageB(PacketB p, int payloadLength, int secret, int num, int studentNum) {
        return p.payloadLen == payloadLength && p.psecret == secret && p.step == 1 && p.studentNum == studentNum && p.packet_id == num;
    }

    public static boolean verifyMessageD(PacketD p, int payloadLength, int secret, int studentNum, char[] payload) {
        return p.payloadLen == payloadLength && p.psecret == secret && p.step == 1 && p.studentNum == studentNum && Arrays.equals(p.payload, payload);
    }
}
