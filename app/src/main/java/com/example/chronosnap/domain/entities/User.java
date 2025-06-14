package com.example.chronosnap.domain.entities;

import android.content.Context;

import com.example.chronosnap.domain.usecases.CreateNewCategoriesMap;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@IgnoreExtraProperties
public class User {
    private String id;
    private String email;
    private String username;
    private Map<String, Integer> categories;

    public User(String un, String email, Context context) {
        id = UUID.randomUUID().toString();
        username = un;
        this.email = email;
        categories = new TreeMap<>(CreateNewCategoriesMap.execute(context));
    }

    public User(String id, String un, String email, Map<String, Integer> categories) {
        this.id = id;
        this.username = un;
        this.email = email;
        this.categories = categories;
    }

    public User() {
        this.categories = new TreeMap<>();
    }

    public static User fromFirebaseUser(FirebaseUser firebaseUser) {
        if (firebaseUser == null) return null;

        User user = new User();
        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setUsername(firebaseUser.getDisplayName());
        user.setCategories(new TreeMap<>());

        return user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Integer> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, Integer> categories) {
        this.categories = categories;
    }
}
