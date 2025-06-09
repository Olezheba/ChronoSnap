package com.example.chronosnap.domain.usecases;

import com.example.chronosnap.data.repository.UserRepository;
import com.example.chronosnap.domain.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;

public class UpdateUserUseCase {
    private final UserRepository repo;
    public UpdateUserUseCase(UserRepository repo){
        this.repo = repo;
    }
    public void execute(User user, OnCompleteListener<Void> listener) {
        repo.updateUser(user, listener);
    }
}
