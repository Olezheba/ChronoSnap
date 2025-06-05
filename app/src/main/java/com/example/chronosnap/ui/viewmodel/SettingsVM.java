package com.example.chronosnap.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chronosnap.data.repository.UserRepository;
import com.example.chronosnap.domain.entities.User;
import com.example.chronosnap.domain.usecases.UpdateUserUseCase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.atomic.AtomicBoolean;

public class SettingsVM extends ViewModel {
    private final UserRepository userRepository = new UserRepository();
    private final MutableLiveData<User> user = new MutableLiveData<>();

    public SettingsVM() {
        loadUser();
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void setUser(User updatedUser) {
        user.setValue(updatedUser);
        updateUser();
    }

    public boolean updateUser() {
        UpdateUserUseCase updateUserUseCase = new UpdateUserUseCase(userRepository);
        User updatedUser = user.getValue();
        if (updatedUser == null) return false;

        AtomicBoolean b = new AtomicBoolean(false);
        updateUserUseCase.execute(updatedUser, task -> {
            if (task.isSuccessful()) {
                b.set(true);
            }
        });
        return b.get();
    }

    public void loadUser() {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            user.setValue(User.fromFirebaseUser(fbUser));
        }
    }

    public void logOut() {
        FirebaseAuth.getInstance().signOut();
    }
}
