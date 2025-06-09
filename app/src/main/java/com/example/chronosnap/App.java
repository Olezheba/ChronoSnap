package com.example.chronosnap;

import android.app.Application;

import androidx.room.Room;

import com.example.chronosnap.data.persistentstorage.AppDatabase;
import com.google.firebase.database.FirebaseDatabase;


public class App extends Application {
    private AppDatabase appDatabase;
    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = Room.databaseBuilder(
                this,
                AppDatabase.class,
                "my-database"
        ).build();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

}
