package com.example.chronosnap.data.repository;

import com.example.chronosnap.domain.entities.User;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseRepository {

    private final FirebaseAuth firebaseAuth;

    public FirebaseRepository(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

}
