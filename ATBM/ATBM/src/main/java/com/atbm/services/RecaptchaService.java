package com.atbm.services;

import com.atbm.utils.ConfigUtils;
import com.atbm.utils.LogUtils;

import java.io.*;
import java.net.*;

public class RecaptchaService {
    private static final String RECAPTCHA_SECRET_KEY = ConfigUtils.get("recaptcha.secret");

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
            LogUtils.debug(RecaptchaService.class, "Lỗi khi xác minh reCAPTCHA: " + e.getMessage());
            return false;
        }
    }
}
