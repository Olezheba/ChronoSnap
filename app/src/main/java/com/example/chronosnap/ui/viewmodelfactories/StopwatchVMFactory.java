package com.example.chronosnap.ui.viewmodelfactories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.chronosnap.AppDatabaseSingleton;
import com.example.chronosnap.data.persistentstorage.AppDatabase;
import com.example.chronosnap.ui.viewmodel.StopwatchVM;

public class StopwatchVMFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context context;
    public StopwatchVMFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if (modelClass == StopwatchVM.class) {
            AppDatabase db = AppDatabaseSingleton.getInstance(context);
            return (T) new StopwatchVM(db);
        }
        return null;
    }

}

