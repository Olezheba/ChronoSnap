package com.example.chronosnap;

import android.content.Context;

import androidx.room.Room;

import com.example.chronosnap.data.persistentstorage.AppDatabase;

public class AppDatabaseSingleton {
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "db")
                    .allowMainThreadQueries() // (лучше заменить на асинхронные вызовы)
                    .build();
        }
        return instance;
    }
}