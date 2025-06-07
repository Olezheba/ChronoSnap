package com.example.chronosnap.ui.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chronosnap.domain.entities.Task;
import com.example.chronosnap.domain.usecases.AddNewTaskUseCase;
import com.example.chronosnap.domain.usecases.GetAllCategoriesUseCase;
import com.example.chronosnap.domain.usecases.GetTasksForDayUseCase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TaskVM extends ViewModel {
    private MutableLiveData<List<Task>> list1 = new MutableLiveData<>();
    private MutableLiveData<List<Task>> list2 = new MutableLiveData<>();
    private MutableLiveData<List<Task>> list3 = new MutableLiveData<>();
    private MutableLiveData<List<Task>> list4 = new MutableLiveData<>();

    public void updateLists(LocalDate date){
        list1.postValue(GetTasksForDayUseCase.execute(date, 1));
        list2.postValue(GetTasksForDayUseCase.execute(date, 2));
        list3.postValue(GetTasksForDayUseCase.execute(date, 3));
        list4.postValue(GetTasksForDayUseCase.execute(date, 4));
    }

    public List<Map.Entry<String, Integer>> getAllCategoriesList() {
        Map<String, Integer> categories = GetAllCategoriesUseCase.execute();
        List<Map.Entry<String, Integer>> categoriesList = new ArrayList<>(categories.entrySet());
        return categoriesList;
    }

    public MutableLiveData<List<Task>> getList1() {
        return list1;
    }

    public void setList1(MutableLiveData<List<Task>> list1) {
        this.list1 = list1;
    }

    public MutableLiveData<List<Task>> getList2() {
        return list2;
    }

    public void setList2(MutableLiveData<List<Task>> list2) {
        this.list2 = list2;
    }

    public MutableLiveData<List<Task>> getList3() {
        return list3;
    }

    public void setList3(MutableLiveData<List<Task>> list3) {
        this.list3 = list3;
    }

    public MutableLiveData<List<Task>> getList4() {
        return list4;
    }

    public void setList4(MutableLiveData<List<Task>> list4) {
        this.list4 = list4;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTask(String uid, String name, int color, LocalDate date, int sectionIndex) {
        Task task = new Task(uid, name, color, date, sectionIndex);
        AddNewTaskUseCase.execute(task);
    }
}
