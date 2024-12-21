package com.example.trivia.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.trivia.Objects.Question;
import com.example.trivia.R;
import com.example.trivia.Objects.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private List<Result> resultList;
    private List<Question> questionList;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //retrieving views
        TableLayout tableLayout = findViewById(R.id.tbl_result);
        Button startOverBtn = findViewById(R.id.btn_start_over);
        Button detailsBtn = findViewById(R.id.btn_details);

        //retrieving the data from intent
        resultList = (List<Result>) getIntent().getSerializableExtra("resultList");
        questionList = (List<Question>) getIntent().getSerializableExtra("questionList");

        //fills table from resultList and questionList data
        populateTable();

        // listener for the button start over - starts the game over
        startOverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear saved state by clearing the back stack
                Intent intent = new Intent(ResultActivity.this, TriviaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        // listener for the button detailed results - start detailedResults activity
        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, DetailedResults.class);
                intent.putExtra("resultList", new ArrayList<>(resultList));
                intent.putExtra("questionList", new ArrayList<>(questionList));
                startActivity(intent);
            }
        });
    }

    private void populateTable() {
        //retrieving view
        TableLayout tableLayout = findViewById(R.id.tbl_result);

        //looping over resultList, each iteration is a table row
        for (int i = 0; i < resultList.size(); i++) {
            //creating a new TableRow
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            //creating and formatting textView for question number (column 1)
            TextView questionNumTV = new TextView(this);
            questionNumTV.setText(String.valueOf(i + 1));
            questionNumTV.setLayoutParams(new TableRow.LayoutParams(
                    0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            questionNumTV.setGravity(android.view.Gravity.CENTER);
            questionNumTV.setPadding(16, 16, 16, 16);

            //creating and formatting textView for selected answer (column 2)
            TextView selectedAnswerTV = new TextView(this);
            char c = (char) ('a' + (resultList.get(i).getSelectedAnswer()-1));
            selectedAnswerTV.setText(String.valueOf(c));
            selectedAnswerTV.setLayoutParams(new TableRow.LayoutParams(
                    0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            selectedAnswerTV.setGravity(android.view.Gravity.CENTER);
            selectedAnswerTV.setPadding(16, 16, 16, 16);

            //creating and formatting textView for correct answer (column 3)
            TextView correctAnswerTV = new TextView(this);
            c = (char) ('a' + (resultList.get(i).getCorrectAnswer()-1));
            correctAnswerTV.setText(String.valueOf(c));
            correctAnswerTV.setLayoutParams(new TableRow.LayoutParams(
                    0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            correctAnswerTV.setGravity(android.view.Gravity.CENTER);
            correctAnswerTV.setPadding(16, 16, 16, 16);

            // Add views to the row
            tableRow.addView(questionNumTV);
            tableRow.addView(selectedAnswerTV);
            tableRow.addView(correctAnswerTV);

            // Alternate row color based on correctness
            if (resultList.get(i).isCorrect()) {
                tableRow.setBackgroundColor(ContextCompat.getColor(this, R.color.light_green)); // Light green
            } else {
                tableRow.setBackgroundColor(ContextCompat.getColor(this, R.color.light_red)); // Light red
            }

            // Create a layout wrapper to add margins
            LinearLayout rowWrapper = new LinearLayout(this);
            LinearLayout.LayoutParams wrapperParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            rowWrapper.setLayoutParams(wrapperParams);
            rowWrapper.setOrientation(LinearLayout.VERTICAL);

            // Add TableRow to the wrapper
            rowWrapper.addView(tableRow);

            // Add a horizontal line
            View separator = new View(this);
            LinearLayout.LayoutParams separatorParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 2); // Height = 1dp
            separator.setLayoutParams(separatorParams);
            separator.setBackgroundColor(Color.DKGRAY); // Dark gray line

            // Add the separator below the row
            rowWrapper.addView(separator);

            // Add wrapper to TableLayout
            tableLayout.addView(rowWrapper);
        }
    }
}