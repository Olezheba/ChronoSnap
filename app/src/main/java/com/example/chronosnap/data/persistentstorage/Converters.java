package com.example.chronosnap.data.persistentstorage;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromMap(Map<Integer, String> map) {
        return gson.toJson(map);
    }

    @TypeConverter
    public static Map<Integer, String> toMap(String json) {
        Type type = new TypeToken<Map<Integer, String>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
