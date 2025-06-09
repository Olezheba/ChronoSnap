package com.example.chronosnap.ui.viewmodelfactories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.chronosnap.data.persistentstorage.AppDatabase;
import com.example.chronosnap.ui.viewmodel.TaskVM;

public class TaskVMFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context context;
    public TaskVMFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if (modelClass == TaskVM.class) {
            AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "db").build();
            return (T) new TaskVM(db);
        }
        return null;
    }
}
