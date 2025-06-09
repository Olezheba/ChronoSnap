package com.example.chronosnap.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.chronosnap.data.persistentstorage.AppDatabase;
import com.example.chronosnap.data.persistentstorage.TaskDao;
import com.example.chronosnap.data.remotedata.TaskRemoteDataSource;
import com.example.chronosnap.domain.entities.MyTask;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRepository {
    private final TaskDao dao;
    private final TaskRemoteDataSource dataSource;
    private final Executor executor;
    private final String uid;

    public TaskRepository(AppDatabase db, TaskRemoteDataSource dataSource) {
        dao = db.taskDao();
        this.dataSource = dataSource;
        executor = Executors.newSingleThreadExecutor();
        uid = FirebaseAuth.getInstance().getUid();
    }

    public void insertTask(MyTask myTask) {
        dataSource.insertTask(myTask)
                .addOnSuccessListener(aVoid -> {
                    executor.execute(() -> {
                        dao.insertTask(myTask);
                    });
                })
                .addOnFailureListener(e -> {
                    Log.e("TaskRepository", "Ошибка при добавлении задачи в Firestore");
                });
    }

    public void deleteTask(MyTask myTask) {
        dataSource.deleteTask(myTask)
                .addOnSuccessListener(aVoid -> {
                    executor.execute(() -> {
                        dao.deleteTask(myTask);
                    });
                })
                .addOnFailureListener(e -> {
                    Log.e("TaskRepository", "Ошибка при удалении задачи из Firestore");
                });
    }

    public LiveData<List<MyTask>> getTasks(String date, int sectionIndex) {
        return dao.getTasks(date, uid, sectionIndex);
    }
}

