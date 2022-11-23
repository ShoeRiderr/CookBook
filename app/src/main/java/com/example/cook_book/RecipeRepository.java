package com.example.cook_book;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipeRepository {

    private final Dao dao;
    private final LiveData<List<RecipeModal>> allRecipes;

    public RecipeRepository(Application application) {
        CookBookDatabase database = CookBookDatabase.getInstance(application);
        dao = database.Dao();
        allRecipes = dao.getAllRecipes();
    }

    public void insert(RecipeModal model) {
        new InsertRecipeAsyncTask(dao).execute(model);
    }

    public void update(RecipeModal model) {
        new UpdateRecipeAsyncTask(dao).execute(model);
    }

    public void delete(RecipeModal model) {
        new DeleteRecipeAsyncTask(dao).execute(model);
    }

    public void deleteAllRecipes() {
        new deleteAllRecipesAsyncTask(dao).execute();
    }

    public LiveData<List<RecipeModal>> getAllRecipes() {
        return allRecipes;
    }

    private static class InsertRecipeAsyncTask extends AsyncTask<RecipeModal, Void, Void> {
        private final Dao dao;

        private InsertRecipeAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(RecipeModal... model) {
            dao.insert(model[0]);
            return null;
        }
    }

    private static class UpdateRecipeAsyncTask extends AsyncTask<RecipeModal, Void, Void> {
        private final Dao dao;

        private UpdateRecipeAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(RecipeModal... models) {
            dao.update(models[0]);
            return null;
        }
    }

    private static class DeleteRecipeAsyncTask extends AsyncTask<RecipeModal, Void, Void> {
        private final Dao dao;

        private DeleteRecipeAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(RecipeModal... models) {
            dao.delete(models[0]);
            return null;
        }
    }

    private static class deleteAllRecipesAsyncTask extends AsyncTask<Void, Void, Void> {
        private final Dao dao;

        private deleteAllRecipesAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllRecipes();
            return null;
        }
    }
}
