package com.atbm.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Utility class hỗ trợ gửi HTTP GET và POST request sử dụng HttpURLConnection và Gson.
 */
public class HttpClientUtils {
    private static final Gson gson = new Gson();

    private static final String JSON = "application/json";

    /**
     * Gửi HTTP GET request đến một URL và parse JSON response thành đối tượng kiểu T.
     *
     * @param url   URL endpoint (dưới dạng chuỗi)
     * @param clazz Class của kiểu dữ liệu muốn nhận về
     * @param <T>   Kiểu dữ liệu trả về (class đơn giản, không phải List<T>)
     * @return Đối tượng kiểu T được parse từ JSON response
     * @throws IOException Nếu có lỗi trong kết nối hoặc đọc dữ liệu
     */
    public static <T> T sendGet(String url, Class<T> clazz) throws IOException {
        //Tạo url để gửi
        URL urlRequest = URI.create(url).toURL();
        //Tạo http connection tới endpoint
        HttpURLConnection connection = (HttpURLConnection) urlRequest.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", JSON);
        if (connection.getResponseCode() == 200) {
            throw new IOException(connection.getResponseMessage());
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return gson.fromJson(br, clazz);
        }
    }

    /**
     * Gửi HTTP POST request đến một URL với body là object được chuyển thành JSON,
     * và parse JSON response thành đối tượng kiểu T.
     *
     * @param url   URL endpoint
     * @param body  Object gửi đi (sẽ được chuyển thành JSON)
     * @param clazz Class của kiểu dữ liệu muốn nhận về
     * @param <T>   Kiểu dữ liệu trả về
     * @return Đối tượng kiểu T được parse từ JSON response
     * @throws IOException Nếu có lỗi trong kết nối hoặc ghi/đọc dữ liệu
     */
    public static <T> T sendPost(String url, Object body, Class<T> clazz) throws IOException {
        //Tạo url để gửi
        URL urlRequest = URI.create(url).toURL();
        //Tạo http connection tới endpoint
        HttpURLConnection connection = (HttpURLConnection) urlRequest.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", JSON);
        connection.setRequestProperty("Content-Type", JSON);
        connection.setDoOutput(true);
        try (OutputStream outputStream = connection.getOutputStream()) {
            String json = gson.toJson(body);
            outputStream.write(json.getBytes());
            outputStream.flush();
        }
        if (connection.getResponseCode() >= 400) {
            throw new RuntimeException("POST request failed: HTTP " + connection.getResponseCode());
        }
        try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
            return gson.fromJson(reader, clazz);
        }
    }
}
