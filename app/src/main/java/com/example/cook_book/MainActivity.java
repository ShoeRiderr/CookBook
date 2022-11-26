package com.example.cook_book;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button openRecipesListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openRecipesListButton = findViewById(R.id.open_recipes_list);

        openRecipesListButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
            startActivity(intent);
        });
    }
}