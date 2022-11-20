package com.example.cook_book;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewRecipeActivity extends AppCompatActivity {

    // creating a variables for our button and edittext.
    private EditText nameEdt, descriptionEdt, durationEdt;

    // creating a constant string variable for our 
    // course name, description and duration.
    public static final String EXTRA_ID = "com.gtappdevelopers.gfgroomdatabase.EXTRA_ID";
    public static final String EXTRA_NAME = "com.gtappdevelopers.gfgroomdatabase.EXTRA_NAME";
    public static final String EXTRA_DESCRIPTION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_DESCRIPTION";
    public static final String EXTRA_DURATION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_DURATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        // initializing our variables for each view.
        nameEdt = findViewById(R.id.idEdtName);
        descriptionEdt = findViewById(R.id.idEdtDescription);
        durationEdt = findViewById(R.id.idEdtDuration);
        Button recipeBtn = findViewById(R.id.idBtnSave);

        // below line is to get intent as we
        // are getting data via an intent.
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            // if we get id for our data then we are
            // setting values to our edit text fields.
            nameEdt.setText(intent.getStringExtra(EXTRA_NAME));
            descriptionEdt.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            durationEdt.setText(intent.getStringExtra(EXTRA_DURATION));
        }
        // adding on click listener for our save button.
        recipeBtn.setOnClickListener(v -> {
            // getting text value from edittext and validating if
            // the text fields are empty or not.
            String name = nameEdt.getText().toString();
            String description = descriptionEdt.getText().toString();
            String duration = durationEdt.getText().toString();
            if (name.isEmpty() || description.isEmpty() || duration.isEmpty()) {
                Toast.makeText(NewRecipeActivity.this, "Please enter the valid course details.", Toast.LENGTH_SHORT).show();
                return;
            }
            // calling a method to save our course.
            saveCourse(name, description, duration);
            finishActivity(1);
        });
    }

    private void saveCourse(String name, String description, String duration) {
        // inside this method we are passing 
        // all the data via an intent.
        Intent data = new Intent();

        // in below line we are passing all our course detail.
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_DURATION, duration);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if (id != -1) {
            // in below line we are passing our id.
            data.putExtra(EXTRA_ID, id);
        }

        // at last we are setting result as data.
        setResult(RESULT_OK, data);



        // displaying a toast message after adding the data
        Toast.makeText(this, "Course has been saved to Room Database. ", Toast.LENGTH_SHORT).show();
    }
}