package com.example.cook_book;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes_table")
public class RecipeModal {

    @PrimaryKey(autoGenerate = true)

    private int id;

    private String name;

    private String description;

    private String duration;

    public RecipeModal(String name, String description, String duration) {
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
