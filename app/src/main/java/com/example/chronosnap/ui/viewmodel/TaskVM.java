package com.example.chronosnap.ui.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chronosnap.data.persistentstorage.AppDatabase;
import com.example.chronosnap.data.remotedata.TaskRemoteDataSource;
import com.example.chronosnap.data.repository.TaskRepository;
import com.example.chronosnap.domain.entities.MyTask;
import com.example.chronosnap.domain.usecases.AddNewTaskUseCase;
import com.example.chronosnap.domain.usecases.GetAllCategoriesUseCase;
import com.example.chronosnap.domain.usecases.GetTasksForDayUseCase;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

public class TaskVM extends ViewModel {
    private final AppDatabase db;
    private MutableLiveData<List<MyTask>> list1 = new MutableLiveData<>();
    private MutableLiveData<List<MyTask>> list2 = new MutableLiveData<>();
    private MutableLiveData<List<MyTask>> list3 = new MutableLiveData<>();
    private MutableLiveData<List<MyTask>> list4 = new MutableLiveData<>();

    public TaskVM(AppDatabase db) {
        this.db = db;
    }

    public void updateLists(String date){
        TaskRepository repo = new TaskRepository(db, new TaskRemoteDataSource());
        repo.getTasks(date, 1).observeForever(tasks -> list1.postValue(tasks));
        repo.getTasks(date, 2).observeForever(tasks -> list2.postValue(tasks));
        repo.getTasks(date, 3).observeForever(tasks -> list3.postValue(tasks));
        repo.getTasks(date, 4).observeForever(tasks -> list4.postValue(tasks));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTask(String uid, String name, int color, LocalDate date, int sectionIndex) {
        MyTask myTask = new MyTask(null, uid, name, color, date, sectionIndex);
        AddNewTaskUseCase addNewTaskUseCase = new AddNewTaskUseCase
                (new TaskRepository(db, new TaskRemoteDataSource()));
        addNewTaskUseCase.execute(myTask);
    }

    public LiveData<TreeMap<String, Integer>> getCategoriesLiveData() {
        String uid = FirebaseAuth.getInstance().getUid();
        return GetAllCategoriesUseCase.execute(uid);
    }
}
