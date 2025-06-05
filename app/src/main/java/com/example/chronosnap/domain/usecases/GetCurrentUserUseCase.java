package com.example.chronosnap.domain.usecases;

import com.example.chronosnap.data.repository.UserRepository;
import com.example.chronosnap.domain.entities.User;

import javax.inject.Inject;

public class GetCurrentUserUseCase {
    private final UserRepository userRepository;

    @Inject
    public GetCurrentUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
