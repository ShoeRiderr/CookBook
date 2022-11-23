package com.example.cook_book;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModal extends AndroidViewModel {

    private final RecipeRepository repository;

    private final LiveData<List<RecipeModal>> allRecipes;

    public ViewModal(@NonNull Application application) {
        super(application);
        repository = new RecipeRepository(application);
        allRecipes = repository.getAllRecipes();
    }

    public void insert(RecipeModal model) {
        repository.insert(model);
    }

    public void update(RecipeModal model) {
        repository.update(model);
    }

    public void delete(RecipeModal model) {
        repository.delete(model);
    }

    public void deleteAllRecipes() {
        repository.deleteAllRecipes();
    }

    public LiveData<List<RecipeModal>> getAllRecipes() {
        return allRecipes;
    }
}
