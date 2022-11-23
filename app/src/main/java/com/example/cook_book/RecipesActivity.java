package com.example.cook_book;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecipesActivity extends AppCompatActivity {

    private ViewModal viewmodal;
    public static final int ADD_REQUEST = 1;
    public static final int EDIT_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        RecyclerView recipesRV = findViewById(R.id.idRVRecipes);
        FloatingActionButton fab = findViewById(R.id.idFABAdd);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(RecipesActivity.this, NewRecipeActivity.class);
            startActivityForResult(intent, ADD_REQUEST);
        });

        recipesRV.setLayoutManager(new LinearLayoutManager(this));
        recipesRV.setHasFixedSize(true);

        final RecipeRVAdapter adapter = new RecipeRVAdapter();

        recipesRV.setAdapter(adapter);

        viewmodal = new ViewModelProvider(this).get(ViewModal.class);

        viewmodal.getAllRecipes().observe(this, models -> adapter.submitList(models));

        adapter.setOnItemClickListener(modal -> {
            Intent intent = new Intent(RecipesActivity.this, NewRecipeActivity.class);
            intent.putExtra(NewRecipeActivity.EXTRA_ID, modal.getId());
            intent.putExtra(NewRecipeActivity.EXTRA_NAME, modal.getName());
            intent.putExtra(NewRecipeActivity.EXTRA_DESCRIPTION, modal.getDescription());
            intent.putExtra(NewRecipeActivity.EXTRA_DURATION, modal.getDuration());

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
            RecipeModal modal = new RecipeModal(name, description, duration);
            viewmodal.insert(modal);
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
            RecipeModal modal = new RecipeModal(name, description, duration);
            modal.setId(id);
            viewmodal.update(modal);
            Toast.makeText(this, "Recipe updated", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_REQUEST && resultCode == RESULT_CANCELED) {
            int id = data.getIntExtra(NewRecipeActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Recipe can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(NewRecipeActivity.EXTRA_NAME);
            String description = data.getStringExtra(NewRecipeActivity.EXTRA_DESCRIPTION);
            String duration = data.getStringExtra(NewRecipeActivity.EXTRA_DURATION);
            RecipeModal modal = new RecipeModal(name, description, duration);
            modal.setId(id);
            viewmodal.delete(modal);
            Toast.makeText(RecipesActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Recipe not saved", Toast.LENGTH_SHORT).show();
        }
    }
}