package com.example.trivia.Activities;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trivia.Objects.Question;
import com.example.trivia.Objects.Result;
import com.example.trivia.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TriviaActivity extends AppCompatActivity {

    private List<Question> questionList;
    private List<Result> resultList;
    private Button[] buttons;
    private TextView questionTV;
    private Button finishButton;
    private int questionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        //Getting access to views
        questionTV = findViewById(R.id.tv_question);
        finishButton = findViewById(R.id.btn_finished);
        buttons = new Button[] {
                findViewById(R.id.btn_option1),
                findViewById(R.id.btn_option2),
                findViewById(R.id.btn_option3),
                findViewById(R.id.btn_option4)
        };

        /*
        logic for if the user is switching between horizontal and vertical. if extras are not
        null it means the activity has started from a start over button in results activity.
        */
        if (savedInstanceState != null && getIntent().getExtras() == null) {
            // Restore previous state only if there's no "Start Over" intent
            questionIndex = savedInstanceState.getInt("questionIndex");
            resultList = savedInstanceState.getParcelableArrayList("resultList");
            questionList = savedInstanceState.getParcelableArrayList("questionList");
            //can't actually be null - used to prevent warning later
            if (questionList == null) {
               System.exit(1);
            }
            //still more questions
            if (questionIndex < questionList.size()) {
                //loads question from previous state
                loadQuestion(false);
                ////logs answers and starts an intent when there are no more questions.
                logAnswers();
            //no more questions
            } else {
                loadFinished();
            }

        //new trivia game
        } else {
            //initializes a new resultList
            resultList = new ArrayList<>();

            //loads question from strings.xml and shuffles the order.loadQuestions();
            loadQuestions();
            Collections.shuffle(questionList); //Order of questions will be different each time you play

            //loading first question
            questionIndex = 0;

            //logs answers and starts an intent when there are no more questions.
            loadQuestion(true);
            logAnswers();
        }
    }

    /*
    Will be used to save state when user is switching from horizontal to vertical mode.
    */
    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save current question index and result list
        outState.putInt("questionIndex", questionIndex);
        outState.putParcelableArrayList("resultList", new ArrayList<>(resultList));
        outState.putParcelableArrayList("questionList", new ArrayList<>(questionList));
    }

    /**
     * Extracting questions and answers from strings.xml file and loading them into questionsList
     * field.
     */
    private void loadQuestions() {
        questionList = new ArrayList<>();
        String[] triviaData = getResources().getStringArray(R.array.trivia_data);

        for (String item : triviaData) {
            String[] parts = item.split("\\{");
            String questionText = parts[0].trim();
            String[] answers = parts[1].replace("}", "").split("\\|");
            questionList.add(new Question(questionText, Arrays.asList(answers)));
        }
    }

    /**
     * Loads question from questionList and creates a new Result in resultList[questionIndex] with
     * the correct answer number (from 1 to 4)
     */
    private void loadQuestion(boolean isNewQuestion) {
        //Extracting question fields
        String questionTxt = questionList.get(questionIndex).getQuestionText();
        List<String> answers = questionList.get(questionIndex).getAnswers();

        questionTV.setText(questionTxt);
        //shuffling answers and storing the correct answer index to Results.
        String correctAnswerTxt = answers.get(0); //The correct answer is always the first one
        if (isNewQuestion) {
            Collections.shuffle(answers);
        }
        int correctAnswerIndex = -1;
        for (int j = 0 ; j < answers.size(); j++) {
            buttons[j].setText(answers.get(j));
            if (answers.get(j).equals(correctAnswerTxt)) {
                correctAnswerIndex = j;
            }
        }
        if (isNewQuestion) {
            resultList.add(new Result(correctAnswerIndex + 1));
        }
    }

    /**
     * This function sets click listeners on each answer button in the trivia game.
     * When a button is clicked, it records the selected answer, updates the current question index,
     * and loads the next question or finishes the game if there are no more questions.
     */
    private void logAnswers() {
        for (int i = 0; i < buttons.length; i++) {
            final int index = i; // Store the index for use in the listener
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resultList.get(questionIndex).setSelectedAnswer(index + 1);
                    if (questionIndex < questionList.size() - 1) {
                        questionIndex++;
                        loadQuestion(true);  // Load the next question after logging the answer
                    } else {
                        questionIndex++;
                        loadFinished();  // If no more questions, finish the game
                    }
                }
            });
        }
    }

    /**
     * sets the buttons to be invisible and tells the user there are no more question.
     * make the finishButton to be visible.
     * Sets a listener for the button and once clicked creates an intent for result activity
     */
    private void loadFinished() {
        String triviaFinishedMessage = getString(R.string.tv_finished);
        questionTV.setText(triviaFinishedMessage);
        finishButton.setVisibility(View.VISIBLE);

        for (Button b: buttons) {
            b.setVisibility(View.GONE);
        }

        //creates a listener for finishButton which start an intent with resultList and questionList
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TriviaActivity.this, ResultActivity.class);
                intent.putExtra("resultList", new ArrayList<>(resultList));
                intent.putExtra("questionList", new ArrayList<>(questionList));
                startActivity(intent);
            }
        });
    }
}