package com.example.chronosnap.domain.usecases;

import com.example.chronosnap.data.repository.UserRepository;
import com.example.chronosnap.domain.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;

import javax.inject.Inject;

public class UpdateUserUseCase {
    private final UserRepository userRepository;

    @Inject
    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(User user, OnCompleteListener<Void> listener) {
        userRepository.updateUser(user, listener);
    }
}
