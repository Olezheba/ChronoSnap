package com.example.chronosnap.ui.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chronosnap.data.persistentstorage.AppDatabase;
import com.example.chronosnap.data.remotedata.ActivityEntryRemoteDataSource;
import com.example.chronosnap.data.repository.ActivityEntriesRepository;
import com.example.chronosnap.domain.entities.ActivityEntry;
import com.example.chronosnap.domain.usecases.AddActivityEntryUseCase;
import com.example.chronosnap.domain.usecases.GetAllCategoriesUseCase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class StopwatchVM extends ViewModel {

    private MutableLiveData<Boolean> running = new MutableLiveData<>(false);
    private MutableLiveData<TreeMap<String, Integer>> categories;
    private long start = 0L;
    private final String uid;
    private final AppDatabase db;

    public StopwatchVM(AppDatabase db){
        uid = FirebaseAuth.getInstance().getUid();
        categories = GetAllCategoriesUseCase.execute(uid);
        this.db = db;
    }

    public MutableLiveData<Boolean> isRunning() { return running; }
    public void setRunning(boolean r) { running.setValue(r); }
    public void setStart(long s) { start = s; }
    public long getStart() { return start; }

    public List<Map.Entry<String, Integer>> getAllCategoriesList() {
        List<Map.Entry<String, Integer>> categoriesList = new ArrayList<>();
        Set<Map.Entry<String, Integer>> entries = getAllCategories().getValue().entrySet();
        for (Map.Entry<String, Integer> x : entries){
            categoriesList.add(x);
        }
        return categoriesList;
    }

    public MutableLiveData<TreeMap<String, Integer>> getAllCategories() {
        return categories;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveActivityEntry(String selectedCategory, int priority, long durationMillis) {
        Map<String, Integer> map = getAllCategories().getValue();
        AddActivityEntryUseCase addActivityEntryUseCase = new AddActivityEntryUseCase
                (new ActivityEntriesRepository(db, new ActivityEntryRemoteDataSource()));
        ActivityEntry ae = new ActivityEntry(uid, selectedCategory, map.get(selectedCategory),
                start, durationMillis, priority);
        addActivityEntryUseCase.execute(ae);
    }
}
