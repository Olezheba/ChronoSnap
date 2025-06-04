package com.example.chronosnap.Domain.Entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.chronosnap.Data.PersistentStorage.Converters;

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
