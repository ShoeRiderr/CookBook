package com.example.cook_book;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insert(RecipeModal model);

    @Update
    void update(RecipeModal model);

    @Delete
    void delete(RecipeModal model);

    @Query("DELETE FROM recipes_table")
    void deleteAllRecipes();

    @Query("SELECT * FROM recipes_table ORDER BY name ASC")
    LiveData<List<RecipeModal>> getAllRecipes();
}
