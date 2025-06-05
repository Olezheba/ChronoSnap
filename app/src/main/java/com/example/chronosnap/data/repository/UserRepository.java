package com.example.chronosnap.data.repository;

import com.example.chronosnap.domain.entities.User;
import com.example.chronosnap.domain.usecases.UpdateUserUseCase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRepository {
    private final FirebaseAuth fAuth;
    private final DatabaseReference dbRef;

    public UserRepository() {
        this.fAuth = FirebaseAuth.getInstance();
        this.dbRef = FirebaseDatabase.getInstance().getReference();
    }

    public void updateUser(User user, OnCompleteListener<Void> listener) {
        String uid = fAuth.getCurrentUser().getUid();
        dbRef.child("users").child(uid).setValue(user).addOnCompleteListener(listener);
    }

}
