package com.example.chronosnap.UI.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.chronosnap.Domain.Entities.User;

public class SettingsVM {
    private MutableLiveData<User> user = new MutableLiveData<>();

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void setUser(MutableLiveData<User> user) {
        this.user = user;
    }
}
