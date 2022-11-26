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

import java.util.Objects;

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
            Intent intent = new Intent(RecipesActivity.this, RecipeFormActivity.class);
            startActivityForResult(intent, ADD_REQUEST);
        });

        recipesRV.setLayoutManager(new LinearLayoutManager(this));
        recipesRV.setHasFixedSize(true);

        final RecipeRVAdapter adapter = new RecipeRVAdapter();

        recipesRV.setAdapter(adapter);

        viewmodal = new ViewModelProvider(this).get(ViewModal.class);

        viewmodal.getAllRecipes().observe(this, adapter::submitList);

        adapter.setOnItemClickListener(modal -> {
            Intent intent = new Intent(this, RecipeShowActivity.class);
            intent.putExtra(RecipeFormActivity.EXTRA_ID, modal.getId());
            intent.putExtra(RecipeFormActivity.EXTRA_NAME, modal.getName());
            intent.putExtra(RecipeFormActivity.EXTRA_DURATION, modal.getDuration());
            intent.putExtra(RecipeFormActivity.EXTRA_DESCRIPTION, modal.getDescription());
            intent.putExtra("requestCode", EDIT_REQUEST);

            startActivityForResult(intent, EDIT_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            RecipeModal modal = prepareRecipeModalData(data);
            viewmodal.insert(modal);
            Toast.makeText(this, "Recipe saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_REQUEST && resultCode == RESULT_OK && Objects.requireNonNull(data).hasExtra(RecipeShowActivity.EXTRA_DELETE)) {
            int id = data.getIntExtra(RecipeFormActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Recipe can't be deleted", Toast.LENGTH_SHORT).show();
                return;
            }
            RecipeModal modal = prepareRecipeModalData(data);
            modal.setId(id);
            viewmodal.delete(modal);
            Toast.makeText(RecipesActivity.this, "Recipe deleted", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(RecipeFormActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Recipe can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            RecipeModal modal = prepareRecipeModalData(data);
            modal.setId(id);
            viewmodal.update(modal);
            Toast.makeText(this, "Recipe updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Recipe not saved", Toast.LENGTH_SHORT).show();
        }
    }

    private RecipeModal prepareRecipeModalData(Intent data) {
        String name = data.getStringExtra(RecipeFormActivity.EXTRA_NAME);
        String description = data.getStringExtra(RecipeFormActivity.EXTRA_DESCRIPTION);
        String duration = data.getStringExtra(RecipeFormActivity.EXTRA_DURATION);

        return new RecipeModal(name, description, duration);
    }
}