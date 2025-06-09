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
        GetTasksForDayUseCase getTasksForDayUseCase = new GetTasksForDayUseCase
                (new TaskRepository(db, new TaskRemoteDataSource()));
        list1.postValue(getTasksForDayUseCase.execute(date, 1).getValue());
        list2.postValue(getTasksForDayUseCase.execute(date, 2).getValue());
        list3.postValue(getTasksForDayUseCase.execute(date, 3).getValue());
        list4.postValue(getTasksForDayUseCase.execute(date, 4).getValue());
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
