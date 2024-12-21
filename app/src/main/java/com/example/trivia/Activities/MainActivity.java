/*
Author: Ariel Feinstein 212033906
 */

package com.example.trivia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trivia.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sets listener to start button to start intent to TriviaActivity
        Button startButton = findViewById(R.id.btn_start_trivia);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TriviaActivity.class);
                startActivity(intent);
            }
        });
    }
}