package com.example.cook_book;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeFormActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.cook_book.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.cook_book.EXTRA_NAME";
    public static final String EXTRA_DESCRIPTION = "com.example.cook_book.EXTRA_DESCRIPTION";
    public static final String EXTRA_DURATION = "com.example.cook_book.EXTRA_DURATION";
    private EditText nameEdt, descriptionEdt, durationEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_form);

        nameEdt = findViewById(R.id.idEdtName);
        descriptionEdt = findViewById(R.id.idEdtDescription);
        durationEdt = findViewById(R.id.idEdtDuration);
        Button recipeBtn = findViewById(R.id.idBtnSave);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            nameEdt.setText(intent.getStringExtra(EXTRA_NAME));
            descriptionEdt.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            durationEdt.setText(intent.getStringExtra(EXTRA_DURATION));
        }

        recipeBtn.setOnClickListener(v -> {
            String name = nameEdt.getText().toString();
            String description = descriptionEdt.getText().toString();
            String duration = durationEdt.getText().toString();
            if (name.isEmpty() || description.isEmpty() || duration.isEmpty()) {
                Toast.makeText(this, "Please enter the valid course details.", Toast.LENGTH_SHORT).show();
                return;
            }

            saveCourse(name, description, duration);
            finish();
        });
    }

    private void saveCourse(String name, String description, String duration) {
        Intent data = new Intent();

        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_DURATION, duration);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
    }
}