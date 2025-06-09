package com.example.chronosnap.data.remotedata;

import com.example.chronosnap.domain.entities.ActivityEntry;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class ActivityEntryRemoteDataSource {
    public Task<Void> addEntry(ActivityEntry entry) {
        return FirebaseFirestore.getInstance()
                .collection("tasks")
                .document(entry.getId())
                .set(entry);
    }
}
