package com.example.cook_book;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipeRepository {

    // below line is the create a variable 
    // for dao and list for all courses.
    private Dao dao;
    private LiveData<List<RecipeModal>> allRecipes;

    // creating a constructor for our variables
    // and passing the variables to it.
    public RecipeRepository(Application application) {
        CookBookDatabase database = CookBookDatabase.getInstance(application);
        dao = database.Dao();
        allRecipes = dao.getAllRecipes();
    }

    // creating a method to insert the data to our database.
    public void insert(RecipeModal model) {
        new InsertRecipeAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(RecipeModal model) {
        new UpdateRecipeAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(RecipeModal model) {
        new DeleteRecipeAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the courses.
    public void deleteAllRecipes() {
        new deleteAllRecipesAsyncTask(dao).execute();
    }

    // below method is to read all the courses.
    public LiveData<List<RecipeModal>> getAllRecipes() {
        return allRecipes;
    }

    // we are creating a async task method to insert new course.
    private static class InsertRecipeAsyncTask extends AsyncTask<RecipeModal, Void, Void> {
        private Dao dao;

        private InsertRecipeAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(RecipeModal... model) {
            // below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our course.
    private static class UpdateRecipeAsyncTask extends AsyncTask<RecipeModal, Void, Void> {
        private Dao dao;

        private UpdateRecipeAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(RecipeModal... models) {
            // below line is use to update
            // our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete course.
    private static class DeleteRecipeAsyncTask extends AsyncTask<RecipeModal, Void, Void> {
        private Dao dao;

        private DeleteRecipeAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(RecipeModal... models) {
            // below line is use to delete 
            // our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all courses.
    private static class deleteAllRecipesAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;
        private deleteAllRecipesAsyncTask(Dao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all courses.
            dao.deleteAllRecipes();
            return null;
        }
    }
}
