package util;

import java.util.Base64;

public class Base64Utils {

    public static String encode(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static String decode(String encodedData) {
        return new String(Base64.getDecoder().decode(encodedData));
    }

    public static byte[] decodeToBytes(String encodedData) {
        return Base64.getDecoder().decode(encodedData);
    }

}
