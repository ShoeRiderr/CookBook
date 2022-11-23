package com.example.cook_book;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewmodal.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(RecipesActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recipesRV);
        adapter.setOnItemClickListener(model -> {
            Intent intent = new Intent(RecipesActivity.this, NewRecipeActivity.class);
            intent.putExtra(NewRecipeActivity.EXTRA_ID, model.getId());
            intent.putExtra(NewRecipeActivity.EXTRA_NAME, model.getName());
            intent.putExtra(NewRecipeActivity.EXTRA_DESCRIPTION, model.getDescription());
            intent.putExtra(NewRecipeActivity.EXTRA_DURATION, model.getDuration());

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

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra("requestCode", requestCode);
        super.startActivityForResult(intent, requestCode);
    }
}