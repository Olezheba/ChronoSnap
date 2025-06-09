package com.example.chronosnap.data.persistentstorage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.chronosnap.domain.entities.ActivityEntry;
import com.example.chronosnap.domain.entities.MyTask;

@Database(entities = {ActivityEntry.class, MyTask.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract TaskDao taskDao();
    public abstract ActivityEntryDao activityEntryDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "chronosnap_database").build();
                }
            }
        }
        return INSTANCE;
    }
}