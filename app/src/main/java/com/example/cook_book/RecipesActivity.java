package com.example.cook_book;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecipesActivity extends AppCompatActivity {

    private static final int ADD_REQUEST = 1;
    private static final int EDIT_REQUEST = 2;
    private ViewModal viewmodal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        // initializing our variable for our recycler view and fab.
        // creating a variables for our recycler view.
        RecyclerView recipesRV = findViewById(R.id.idRVRecipes);
        FloatingActionButton fab = findViewById(R.id.idFABAdd);

        // adding on click listener for floating action button.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // starting a new activity for adding a new course 
                // and passing a constant value in it.
                Intent intent = new Intent(RecipesActivity.this, NewRecipeActivity.class);
                startActivityForResult(intent, ADD_REQUEST);
            }
        });

        // setting layout manager to our adapter class.
        recipesRV.setLayoutManager(new LinearLayoutManager(this));
        recipesRV.setHasFixedSize(true);

        // initializing adapter for recycler view.
        final RecipeRVAdapter adapter = new RecipeRVAdapter();

        // setting adapter class for recycler view.
        recipesRV.setAdapter(adapter);

        // passing a data from view modal.
        viewmodal = new ViewModelProvider(this).get(ViewModal.class);

        // below line is use to get all the courses from view modal.
        viewmodal.getAllRecipes().observe(this, new Observer<List<RecipeModal>>() {
            @Override
            public void onChanged(List<RecipeModal> models) {
                // when the data is changed in our models we are
                // adding that list to our adapter class.
                adapter.submitList(models);
            }
        });
        // below method is use to add swipe to delete method for item of recycler view.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // on recycler view item swiped then we are deleting the item of our recycler view.
                viewmodal.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(RecipesActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).
                // below line is use to attach this to recycler view.
                        attachToRecyclerView(recipesRV);
        // below line is use to set item click listener for our item of recycler view.
        adapter.setOnItemClickListener(model -> {
            // after clicking on item of recycler view
            // we are opening a new activity and passing
            // a data to our activity.
            Intent intent = new Intent(RecipesActivity.this, NewRecipeActivity.class);
            intent.putExtra(NewRecipeActivity.EXTRA_ID, model.getId());
            intent.putExtra(NewRecipeActivity.EXTRA_NAME, model.getName());
            intent.putExtra(NewRecipeActivity.EXTRA_DESCRIPTION, model.getDescription());
            intent.putExtra(NewRecipeActivity.EXTRA_DURATION, model.getDuration());

            // below line is to start a new activity and
            // adding a edit course constant.
            startActivityForResult(intent, EDIT_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(NewRecipeActivity.EXTRA_NAME);
            String description = data.getStringExtra(NewRecipeActivity.EXTRA_DESCRIPTION);
            String duration = data.getStringExtra(NewRecipeActivity.EXTRA_DURATION);
            RecipeModal model = new RecipeModal(name, description, duration);
            viewmodal.insert(model);
            Toast.makeText(this, "Recipe saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NewRecipeActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Recipe can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(NewRecipeActivity.EXTRA_NAME);
            String description = data.getStringExtra(NewRecipeActivity.EXTRA_DESCRIPTION);
            String duration = data.getStringExtra(NewRecipeActivity.EXTRA_DURATION);
            RecipeModal model = new RecipeModal(name, description, duration);
            model.setId(id);
            viewmodal.update(model);
            Toast.makeText(this, "Recipe updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Recipe not saved", Toast.LENGTH_SHORT).show();
        }
    }
}