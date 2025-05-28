package com.example.chronosnap.Domain.Entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private String id;

    private String email;
    private String password;
    private String username;

    @Embedded
    private NotificationParameters settings;

    public User(String un, String email, String password) {
        username = un;
        this.email = email;
        this.password = password;
        settings = new NotificationParameters();
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
}
