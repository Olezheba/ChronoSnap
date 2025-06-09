package com.example.chronosnap.data.system;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.room.Room;

import com.example.chronosnap.data.persistentstorage.AppDatabase;
import com.example.chronosnap.data.remotedata.ActivityEntryRemoteDataSource;
import com.example.chronosnap.data.repository.ActivityEntriesRepository;
import com.example.chronosnap.domain.entities.ActivityEntry;
import com.example.chronosnap.domain.usecases.AddActivityEntryUseCase;
import com.example.chronosnap.domain.usecases.GetAllCategoriesUseCase;
import com.example.chronosnap.utils.CategoryUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class USM {
    private Context context;
    private UsageStatsManager usm;
    CategoryUtils utils;

    public USM(Context context){
        this.context = context;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void writeDown(Context context) {
        Log.d("USM", "Метод writeDown() запущен");
        usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        long lastSync = getLastSyncTime();
        long now = System.currentTimeMillis();

        UsageEvents events = usm.queryEvents(lastSync, now);

        UsageEvents.Event event = new UsageEvents.Event();
        Map<String, Long> foregroundTimes = new HashMap<>();

        while (events.hasNextEvent()) {
            events.getNextEvent(event);

            if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                foregroundTimes.put(event.getPackageName(), event.getTimeStamp());
            }

            if (event.getEventType() == UsageEvents.Event.MOVE_TO_BACKGROUND) {
                String pkg = event.getPackageName();

                if (foregroundTimes.containsKey(pkg)) {
                    long start = foregroundTimes.get(pkg);
                    long end = event.getTimeStamp();
                    long duration = end-start;

                    utils = new CategoryUtils(context);
                    String categoryName = getAppCategory(context, pkg).getKey();
                    int categoryColor = getAppCategory(context, pkg).getValue();

                    ActivityEntry entry = new ActivityEntry(FirebaseAuth.getInstance().getUid(),
                            categoryName, categoryColor, start, duration, 2);
                    AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "db").build();
                    AddActivityEntryUseCase addActivityEntryUseCase = new AddActivityEntryUseCase
                            (new ActivityEntriesRepository(db, new ActivityEntryRemoteDataSource()));
                    addActivityEntryUseCase.execute(entry);

                    foregroundTimes.remove(pkg);
                }
            }
        }

        saveLastSyncTime(now);
    }

    private long getLastSyncTime() {
        SharedPreferences prefs = context.getSharedPreferences("sync_prefs", Context.MODE_PRIVATE);
        return prefs.getLong("last_usage_sync", System.currentTimeMillis() - 60 * 60 * 1000);
    }

    private void saveLastSyncTime(long time) {
        SharedPreferences prefs = context.getSharedPreferences("sync_prefs", Context.MODE_PRIVATE);
        prefs.edit().putLong("last_usage_sync", time).apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public AbstractMap.SimpleEntry<String, Integer> getAppCategory(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        AbstractMap.SimpleEntry<String, Integer> category = new AbstractMap.SimpleEntry<>
                (utils.getApplicationCategories()[0], CategoryUtils.CATEGORY_COLORS[0]);
        try {
            int index = pm.getApplicationInfo(packageName, 0).category;
            category = new AbstractMap.SimpleEntry<>
                    (utils.getApplicationCategories()[index], CategoryUtils.CATEGORY_COLORS[index]);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("USM", "Не удалось определить категорию");
        }
        return category;
    }
}
