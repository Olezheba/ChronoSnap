package com.example.chronosnap.Domain.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private String id;

    private String userId;
    private String name;
    private byte colorIndex;

    public Category(String userId, String name, byte ci) {
        this.userId = userId;
        this.name = name;
        colorIndex = ci;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(byte colorIndex) {
        this.colorIndex = colorIndex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
