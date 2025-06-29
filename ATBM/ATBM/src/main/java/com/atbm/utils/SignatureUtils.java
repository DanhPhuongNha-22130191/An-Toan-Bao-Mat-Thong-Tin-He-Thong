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
        if (data == null) {
            throw new IllegalArgumentException("Input to hash() cannot be null");
        }
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
        System.out.println(data.equals("Ngày đặt: 28/06/2025\n" +
                "Họ tên: Huỳnh Minh Hiển\n" +
                "SĐT: 0939346634\n" +
                "Địa chỉ: ádf, Xã Hố Mít, , \n" +
                "Sản phẩm:\n" +
                "• Casio MTP-V002L-1B3UDF x2: 1000000.0 VNĐ\n" +
                "• Casio MTP-V002L-1B3UDF x2: 1000000.0 VNĐ\n" +
                "Tổng tiền: 2000000.0 VNĐ"));
        return signature.verify(signatureBytes);
    }

    /**
     * Validate và format public key PEM
     */
    public static String validateAndFormatPEM(String pem) {
        if (pem == null || pem.trim().isEmpty()) {
            throw new IllegalArgumentException("Public key không được bỏ trống");
        }

        String trimmedPem = pem.trim();
        
        // Kiểm tra xem có phải PEM format không
        if (!trimmedPem.contains("-----BEGIN PUBLIC KEY-----") || 
            !trimmedPem.contains("-----END PUBLIC KEY-----")) {
            throw new IllegalArgumentException("Public key phải có format PEM hợp lệ");
        }

        // Nếu PEM không có xuống dòng, format lại
        if (!trimmedPem.contains("\n") && !trimmedPem.contains("\r")) {
            int beginIndex = trimmedPem.indexOf("-----BEGIN PUBLIC KEY-----");
            int endIndex = trimmedPem.indexOf("-----END PUBLIC KEY-----");
            
            if (beginIndex != -1 && endIndex != -1 && endIndex > beginIndex) {
                String header = trimmedPem.substring(0, beginIndex + 26);
                String keyContent = trimmedPem.substring(beginIndex + 26, endIndex);
                String footer = trimmedPem.substring(endIndex);
                
                // Kiểm tra key content có hợp lệ không
                if (keyContent.trim().isEmpty()) {
                    throw new IllegalArgumentException("Nội dung public key không được bỏ trống");
                }
                
                return header + "\n" + keyContent + "\n" + footer;
            }
        }

        return trimmedPem;
    }

    /**
     * Convert public key từ Base64 string với validation
     */
    public static PublicKey getPublicKeyFromBase64(String base64PublicKey) throws Exception {
        if (base64PublicKey == null || base64PublicKey.trim().isEmpty()) {
            throw new IllegalArgumentException("Public key không được bỏ trống");
        }

        try {
            // Validate và format PEM
            String formattedPem = validateAndFormatPEM(base64PublicKey);
            
            // Trích xuất Base64 content
            String base64Content = extractBase64FromPEM(formattedPem);
            
            // Decode và tạo public key
            byte[] keyBytes = Base64.getDecoder().decode(base64Content);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new IllegalArgumentException("Không thể parse public key: " + e.getMessage(), e);
        }
    }

    public static String extractBase64FromPEM(String pem) {
        if (pem == null || pem.trim().isEmpty()) {
            return null;
        }

        String trimmedPem = pem.trim();
        
        // Nếu PEM không có xuống dòng, thêm xuống dòng
        if (!trimmedPem.contains("\n") && !trimmedPem.contains("\r")) {
            // Tìm vị trí bắt đầu và kết thúc của key
            int beginIndex = trimmedPem.indexOf("-----BEGIN PUBLIC KEY-----");
            int endIndex = trimmedPem.indexOf("-----END PUBLIC KEY-----");
            
            if (beginIndex != -1 && endIndex != -1) {
                // Thêm xuống dòng vào các vị trí cần thiết
                trimmedPem = trimmedPem.substring(0, beginIndex + 26) + "\n" + 
                           trimmedPem.substring(beginIndex + 26, endIndex) + "\n" + 
                           trimmedPem.substring(endIndex);
            }
        }

        StringBuilder base64Content = new StringBuilder();
        String[] lines = trimmedPem.split("\\r?\\n");
        boolean insideKey = false;

        for (String line : lines) {
            line = line.trim();
            if (line.contains("-----BEGIN PUBLIC KEY-----")) {
                insideKey = true;
            } else if (line.contains("-----END PUBLIC KEY-----")) {
                break;
            } else if (insideKey && !line.isEmpty()) {
                base64Content.append(line);
            }
        }

        String result = base64Content.toString();
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Không thể trích xuất nội dung Base64 từ PEM key");
        }
        
        return result;
    }

}

