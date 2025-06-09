package com.example.chronosnap.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chronosnap.data.persistentstorage.AppDatabase;
import com.example.chronosnap.data.remotedata.ActivityEntryRemoteDataSource;
import com.example.chronosnap.data.repository.ActivityEntriesRepository;
import com.example.chronosnap.domain.entities.ActivityEntry;
import com.example.chronosnap.domain.usecases.GetDayActivityEntriesUseCase;

import java.util.List;

public class TimelineVM extends ViewModel {
    private AppDatabase db;
    public TimelineVM(AppDatabase db) {
        this.db = db;
    }

    private MutableLiveData<List<ActivityEntry>> list = new MutableLiveData<>();
    public MutableLiveData<List<ActivityEntry>> getList() {
        return list;
    }
    public void updateList(String selectedDate){
        GetDayActivityEntriesUseCase getDayActivityEntriesUseCase = new GetDayActivityEntriesUseCase
                (new ActivityEntriesRepository(db, new ActivityEntryRemoteDataSource()));
        list.postValue(getDayActivityEntriesUseCase.execute(selectedDate).getValue());
    }
}
