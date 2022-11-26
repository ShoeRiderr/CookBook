package com.example.cook_book;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeShowActivity extends AppCompatActivity {

    public static final String EXTRA_DELETE = "com.example.cook_book.EXTRA_DELETE";
    TextView nameText, durationText, descriptionText;
    Intent modalIntent;
    String recipeName, recipeDuration, recipeDescription;
    Integer recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_show);

        modalIntent = getIntent();

        nameText = findViewById(R.id.name);
        durationText = findViewById(R.id.duration);
        descriptionText = findViewById(R.id.description);

        recipeId = modalIntent.getIntExtra(RecipeFormActivity.EXTRA_ID, -1);
        recipeName = modalIntent.getStringExtra(RecipeFormActivity.EXTRA_NAME);
        recipeDuration = modalIntent.getStringExtra(RecipeFormActivity.EXTRA_DURATION);
        recipeDescription = modalIntent.getStringExtra(RecipeFormActivity.EXTRA_DESCRIPTION);

        nameText.setText(recipeName);
        durationText.setText(recipeDuration);
        descriptionText.setText(recipeDescription);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void onEditElement(MenuItem item) {
        Intent intent = new Intent(this, RecipeFormActivity.class);

        prepareIntent(intent);

        startActivityForResult(intent, getIntent().getIntExtra("requestCode", -1));
    }

    public void onDeleteElement(MenuItem item) {
        Intent data = new Intent();

        prepareIntent(data);

        data.putExtra(EXTRA_DELETE, 1);

        setResult(RESULT_OK, data);

        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RecipesActivity.EDIT_REQUEST && resultCode == RESULT_OK) {
            Intent intent = new Intent();

            assert data != null;
            String name = data.getStringExtra(RecipeFormActivity.EXTRA_NAME);
            String duration = data.getStringExtra(RecipeFormActivity.EXTRA_DURATION);
            String description = data.getStringExtra(RecipeFormActivity.EXTRA_DESCRIPTION);

            intent.putExtra(RecipeFormActivity.EXTRA_ID, recipeId);
            intent.putExtra(RecipeFormActivity.EXTRA_NAME, name);
            intent.putExtra(RecipeFormActivity.EXTRA_DURATION, duration);
            intent.putExtra(RecipeFormActivity.EXTRA_DESCRIPTION, description);

            setResult(RESULT_OK, intent);
        }
    }

    private void prepareIntent(Intent intent) {
        intent.putExtra(RecipeFormActivity.EXTRA_ID, recipeId);
        intent.putExtra(RecipeFormActivity.EXTRA_NAME, recipeName);
        intent.putExtra(RecipeFormActivity.EXTRA_DURATION, recipeDuration);
        intent.putExtra(RecipeFormActivity.EXTRA_DESCRIPTION, recipeDescription);
    }
}