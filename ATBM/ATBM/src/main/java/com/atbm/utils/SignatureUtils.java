package com.atbm.utils;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SignatureUtils {
    /**
     * Tạo mã băm SHA-256 từ chuỗi đầu vào
     */
    public static String hash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashBytes);
    }

    /**
     * Xác minh chữ ký số bằng public key
     */
    public static boolean verify(String data, String signatureBase64, PublicKey publicKey) throws Exception {
        byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));

        return signature.verify(signatureBytes);
    }


    /**
     * Convert public key từ Base64 string
     */
    public static PublicKey getPublicKeyFromBase64(String base64PublicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(extractBase64FromPEM(base64PublicKey));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    public static String extractBase64FromPEM(String pem) {
        if (pem == null) return null;

        StringBuilder base64Content = new StringBuilder();
        String[] lines = pem.split("\\r?\\n");
        boolean insideKey = false;

        for (String line : lines) {
            if (line.contains("BEGIN PUBLIC KEY")) {
                insideKey = true;
            } else if (line.contains("END PUBLIC KEY")) {
                break;
            } else if (insideKey) {
                base64Content.append(line.trim());
            }
        }

        return base64Content.toString();
    }

}
