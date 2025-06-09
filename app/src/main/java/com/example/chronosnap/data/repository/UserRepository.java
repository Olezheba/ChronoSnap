package com.example.chronosnap.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.chronosnap.domain.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.TreeMap;

public class UserRepository {
    private final String uid;
    private final DatabaseReference dbRef;
    private final MutableLiveData<TreeMap<String, Integer>> categoriesLiveData = new MutableLiveData<>();

    public UserRepository(String uid) {
        this.uid = uid;
        this.dbRef = FirebaseDatabase.getInstance().getReference("users");
        if (uid != null) {
            loadCategories();
        }
    }

    public void updateUser (User user, OnCompleteListener<Void> listener) {
        if (uid == null) return;
        dbRef.child(uid).setValue(user).addOnCompleteListener(listener);
    }

    private void loadCategories() {
        dbRef.child(uid).child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TreeMap<String, Integer> categories = new TreeMap<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    String key = child.getKey();
                    Integer value = child.getValue(Integer.class);
                    if (key != null && value != null) {
                        categories.put(key, value);
                    }
                }
                categoriesLiveData.postValue(categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("User Repository", "Ошибка загрузки категорий: " + error.getMessage());
            }
        });
    }

    public MutableLiveData<TreeMap<String, Integer>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    public void updateAllCategories(TreeMap<String, Integer> categories, OnCompleteListener<Void> listener) {
        if (uid == null) return;
        dbRef.child(uid).child("categories").setValue(categories).addOnCompleteListener(listener);
    }
}
