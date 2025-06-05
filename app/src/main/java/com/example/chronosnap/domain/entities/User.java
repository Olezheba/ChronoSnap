package com.example.chronosnap.domain.entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.chronosnap.data.persistentstorage.Converters;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private String id;

    private String email;
    private String password;
    private String username;
    @TypeConverters(Converters.class)
    private Map<String, Integer> categories;

    @Embedded
    private NotificationParameters settings;

    public User(String un, String email, String password) {
        username = un;
        this.email = email;
        this.password = password;
        settings = new NotificationParameters();
        categories = new HashMap<>();
    }

    public User(String id, String un, String email, String password, int startHour, int startMinute,
                int finishHour, int finishMinute, int interval, boolean enabled,
                Map<String, Integer> categories) {
        this.id = id;
        username = un;
        this.email = email;
        this.password = password;
        settings = new NotificationParameters(enabled, startHour, startMinute, finishHour,
                finishMinute, interval);
        this.categories = categories;
    }

    public User() {
        this.categories = new HashMap<>();
    }

    public static User fromFirebaseUser(FirebaseUser firebaseUser) {
        if (firebaseUser == null) return null;

        User user = new User();
        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setUsername(firebaseUser.getDisplayName());

        user.setPassword("");
        user.setCategories(new HashMap<>());
        user.setSettings(new NotificationParameters());

        return user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public NotificationParameters getSettings() {
        return settings;
    }

    public void setSettings(NotificationParameters settings) {
        this.settings = settings;
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
