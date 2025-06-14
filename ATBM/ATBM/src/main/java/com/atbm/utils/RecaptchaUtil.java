package com.atbm.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecaptchaUtil {
    private static final String RECAPTCHA_SECRET_KEY = "6LcooTwrAAAAADsuwNgS3T4IsD9Cu1jmmSbJ5p1Y";

    public static boolean verify(String recaptchaResponse) {
        try {
            URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            String postParams = "secret=" + RECAPTCHA_SECRET_KEY + "&response=" + recaptchaResponse;
            conn.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.writeBytes(postParams);
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString().contains("\"success\": true");
            }
        } catch (Exception e) {
            LogUtils.debug(RecaptchaUtil.class, "Lỗi khi xác minh reCAPTCHA: " + e.getMessage());
            return false;
        }
    }
}