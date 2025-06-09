package com.example.chronosnap.ui.viewmodelfactories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.chronosnap.data.persistentstorage.AppDatabase;
import com.example.chronosnap.ui.viewmodel.TimelineVM;

public class TimelineVMFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context context;
    public TimelineVMFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if (modelClass == TimelineVM.class) {
            AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "db").build();
            return (T) new TimelineVM(db);
        }
        return null;
    }

}
