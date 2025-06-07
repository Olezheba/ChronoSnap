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

import com.example.chronosnap.domain.entities.ActivityEntry;
import com.example.chronosnap.domain.usecases.AddActivityEntryUseCase;
import com.example.chronosnap.utils.CategoryUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class USM {
    private Context context;
    private UsageStatsManager usm;
    CategoryUtils utils;
    private AddActivityEntryUseCase addActivityEntryUseCase;

    public USM(Context context){
        this.context = context;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void writeDown(Context context) {
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
                    String categoryName = utils.getApplicationCategories()[getAppCategory(context, pkg)];
                    int categoryColor = CategoryUtils.CATEGORY_COLORS[getAppCategory(context, pkg)];
                    ActivityEntry entry = new ActivityEntry(FirebaseAuth.getInstance().getUid(),
                            categoryName, categoryColor, start, duration, 2);
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
    public static int getAppCategory(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
            return info.category+1;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("USM", "Не удалось определить категорию");
        }
        return 0;
    }
}
