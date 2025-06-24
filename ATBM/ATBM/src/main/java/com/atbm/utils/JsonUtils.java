package com.atbm.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtils {
    private static final Gson gson = new Gson();

    // Chuyển object thành JSON
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    // Chuyển JSON thành object (class đơn giản)
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Lỗi khi chuyển JSON thành object", e);
        }
    }

    // Chuyển JSON thành List<Object>
    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(json, type);
    }

}
