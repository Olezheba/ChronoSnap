package com.example.chronosnap.data.remotedata;

import android.util.Log;

import com.example.chronosnap.domain.entities.MyTask;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class TaskRemoteDataSource {
    public Task<Void> insertTask(MyTask myTask) {
        return FirebaseFirestore.getInstance()
                .collection("tasks")
                .document(myTask.getId())
                .set(myTask);
    }

    public Task<Void> deleteTask(MyTask task) {
        return FirebaseFirestore.getInstance()
                .collection("tasks")
                .document(task.getId())
                .delete();
    }


}
