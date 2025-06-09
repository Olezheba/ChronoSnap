package com.example.chronosnap.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.chronosnap.data.persistentstorage.ActivityEntryDao;
import com.example.chronosnap.data.persistentstorage.AppDatabase;
import com.example.chronosnap.data.remotedata.ActivityEntryRemoteDataSource;
import com.example.chronosnap.domain.entities.ActivityEntry;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ActivityEntriesRepository {
    private final ActivityEntryDao dao;
    private final ActivityEntryRemoteDataSource dataSource;
    private final Executor executor;
    private final String uid;

    public ActivityEntriesRepository(AppDatabase db, ActivityEntryRemoteDataSource dataSource){
        dao = db.activityEntryDao();
        this.dataSource = dataSource;
        executor = Executors.newSingleThreadExecutor();
        uid = FirebaseAuth.getInstance().getUid();
    }

    public void addActivityEntry(ActivityEntry entry) {
        dataSource.addEntry(entry)
                .addOnSuccessListener(aVoid -> {
                    executor.execute(() -> {
                        dao.addEntry(entry);
                    });
                })
                .addOnFailureListener(e -> {
                    Log.e("ActivityEntriesRepository", "Ошибка при добавлении записи в Firestore");
                });
    }

    public LiveData<List<ActivityEntry>> getEntries(String date){
        return dao.getEntries(date, uid);
    }

    public LiveData<List<ActivityEntry>> getEntries(String startDate, String finishDate){
        return dao.getEntries(startDate, finishDate,  uid);
    }
}
