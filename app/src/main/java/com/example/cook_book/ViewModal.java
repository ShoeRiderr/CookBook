package com.example.cook_book;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cook_book.RecipeModal;
import com.example.cook_book.RecipeRepository;

import java.util.List;

public class ViewModal extends AndroidViewModel {

    // creating a new variable for course repository.
    private RecipeRepository repository;

    // below line is to create a variable for live
    // data where all the courses are present.
    private LiveData<List<RecipeModal>> allRecipes;

    // constructor for our view modal.
    public ViewModal(@NonNull Application application) {
        super(application);
        repository = new RecipeRepository(application);
        allRecipes = repository.getAllRecipes();
    }

    // below method is use to insert the data to our repository.
    public void insert(RecipeModal model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(RecipeModal model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(RecipeModal model) {
        repository.delete(model);
    }

    // below method is to delete all the courses in our list.
    public void deleteAllRecipes() {
        repository.deleteAllRecipes();
    }

    // below method is to get all the courses in our list.
    public LiveData<List<RecipeModal>> getAllRecipes() {
        return allRecipes;
    }
}
