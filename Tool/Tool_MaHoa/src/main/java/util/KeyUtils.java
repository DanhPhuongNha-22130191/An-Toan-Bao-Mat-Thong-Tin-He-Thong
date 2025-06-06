package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtils {
    private static final String PRIVATE_KEY_HEADER = "-----BEGIN PRIVATE KEY-----";
    private static final String PRIVATE_KEY_FOOTER = "-----END PRIVATE KEY-----";
    private static final String PUBLIC_KEY_HEADER = "-----BEGIN PUBLIC KEY-----";
    private static final String PUBLIC_KEY_FOOTER = "-----END PUBLIC KEY-----";
    private static final String RSA_ALGORITHM = "RSA";
    private static final int PEM_LINE_LENGTH = 64;

    /**
     * Lưu Key vào file
     */
    public static void saveKeyToFile(Key key, String filePath) throws IOException {
        String pemContent = convertKeyToPEM(key);
        writeKeyToFile(pemContent, filePath);
    }

    /**
     * Chuyển đổi Key thành format PEM
     */
    public static String convertKeyToPEM(Key key) {
        String header, footer;
        if (key instanceof PrivateKey) {
            header = PRIVATE_KEY_HEADER;
            footer = PRIVATE_KEY_FOOTER;
        } else {
            header = PUBLIC_KEY_HEADER;
            footer = PUBLIC_KEY_FOOTER;
        }

        byte[] encoded = key.getEncoded();
        String base64Encoded = Base64.getEncoder().encodeToString(encoded);
        return formatPEM(base64Encoded, header, footer);
    }

    /**
     * Format chuỗi Base64 thành định dạng PEM
     */
    private static String formatPEM(String base64Content, String header, String footer) {
        StringBuilder pemBuilder = new StringBuilder();
        pemBuilder.append(header).append("\n");

        for (int i = 0; i < base64Content.length(); i += PEM_LINE_LENGTH) {
            int endIndex = Math.min(i + PEM_LINE_LENGTH, base64Content.length());
            pemBuilder.append(base64Content, i, endIndex).append("\n");
        }

        pemBuilder.append(footer);
        return pemBuilder.toString();
    }

    /**
     * Đọc Key từ file
     */
    public static <T extends Key> T readKeyFromFile(String filePath, boolean isPrivate) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filePath).toPath());
        return generateKeyFromBytes(keyBytes, isPrivate);
    }

    /**
     * Đọc Key từ text string
     */
    public static <T extends Key> T readKeyFromString(String keyContent, boolean isPrivate) throws Exception {
        String cleanedContent = cleanPEMContent(keyContent, isPrivate);
        byte[] decoded = Base64.getDecoder().decode(cleanedContent);
        return generateKeyFromBytes(decoded, isPrivate);

    }

    /**
     * Làm sạch nội dung PEM
     */
    private static String cleanPEMContent(String content, boolean isPrivate) {
        String header = isPrivate ? PRIVATE_KEY_HEADER : PUBLIC_KEY_HEADER;
        String footer = isPrivate ? PRIVATE_KEY_FOOTER : PUBLIC_KEY_FOOTER;

        return content
                .replace(header, "")
                .replace(footer, "")
                .replaceAll("\\s+", "");
    }

    /**
     * Tạo key từ byte array
     */
    @SuppressWarnings("unchecked")
    private static <T extends Key> T generateKeyFromBytes(byte[] keyBytes, boolean isPrivate) throws Exception {

        try {
            KeyFactory keyFactory = java.security.KeyFactory.getInstance(RSA_ALGORITHM);
            if (isPrivate) {
                PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
                return (T) keyFactory.generatePrivate(spec);
            } else {
                X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
                return (T) keyFactory.generatePublic(spec);
            }
        } catch (Exception e) {
            throw new Exception("Chưa tạo khóa hoặc khóa không hợp lệ");
        }
    }

    /**
     * Ghi nội dung key vào file
     */
    private static void writeKeyToFile(String keyContent, String filePath) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(keyContent);
        }
    }

    // Convenience methods để dễ sử dụng
    public static void savePrivateKeyToFile(PrivateKey privateKey, String filePath) throws IOException {
        saveKeyToFile(privateKey, filePath);
    }

    public static void savePublicKeyToFile(PublicKey publicKey, String filePath) throws IOException {
        saveKeyToFile(publicKey, filePath);
    }

    public static PrivateKey readPrivateKeyFromFile(String filePath) throws Exception {
        return readKeyFromFile(filePath, true);
    }

    public static PublicKey readPublicKeyFromFile(String filePath) throws Exception {
        return readKeyFromFile(filePath, false);
    }

    public static PrivateKey readPrivateKeyFromString(String privateKeyContent) throws Exception {
        return readKeyFromString(privateKeyContent, true);
    }

    public static PublicKey readPublicKeyFromString(String publicKeyContent) throws Exception {
        return readKeyFromString(publicKeyContent, false);
    }
}