package com.example.chronosnap.ui.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chronosnap.domain.entities.ActivityEntry;
import com.example.chronosnap.domain.usecases.AddActivityEntryUseCase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StopwatchVM extends ViewModel {

    private MutableLiveData<Boolean> running = new MutableLiveData<>(false);
    private long start = 0L;
    private final String uid = FirebaseAuth.getInstance().getUid();

    public MutableLiveData<Boolean> isRunning() { return running; }
    public void setRunning(boolean r) { running.setValue(r); }
    public void setStart(long s) { start = s; }
    public long getStart() { return start; }

    private AddActivityEntryUseCase addActivityEntryUseCase;

    public List<Map.Entry<String, Integer>> getAllCategoriesList() {
        Map<String, Integer> categories = new TreeMap<>();
        categories.put("ExampleCategory1", 0);
        categories.put("ExampleCategory2", 0);
        //TODO получение всех категорий / use case

        List<Map.Entry<String, Integer>> categoriesList = new ArrayList<>(categories.entrySet());
        return categoriesList;
    }

    public Map<String, Integer> getAllCategories() {
        Map<String, Integer> categories = new TreeMap<>();
        categories.put("ExampleCategory1", 0);
        categories.put("ExampleCategory2", 0);
        //TODO получение всех категорий / use case

        return categories;
    }

    public String getUid() {
        return uid;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveActivityEntry(String selectedCategory, byte priority, long durationMillis) {
        Map<String, Integer> map = getAllCategories();
        ActivityEntry ae = new ActivityEntry(uid, map.get(selectedCategory), start, durationMillis, priority);
        addActivityEntryUseCase.execute(ae);
    }
}
