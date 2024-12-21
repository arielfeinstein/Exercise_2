package com.example.trivia.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.trivia.Fragments.ResultFragment;
import com.example.trivia.Objects.Question;
import com.example.trivia.Objects.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultPagerAdapter extends FragmentStateAdapter {
    // Lists holding the questions and corresponding results
    private List<Question> questionList;
    private List<Result> resultList;

    // Constructor initializing the adapter with activity, questions, and results
    public ResultPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Question> questionList, List<Result> resultList) {
        super(fragmentActivity);
        this.questionList = questionList;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Get the current question and result based on position
        Question question = questionList.get(position);
        Result result = resultList.get(position);

        // Create and return a new instance of ResultFragment with relevant data
        return ResultFragment.newInstance(
                question.getQuestionText(),
                new ArrayList<>(question.getAnswers()),
                result.getCorrectAnswer(),
                result.getSelectedAnswer()
        );
    }

    @Override
    public int getItemCount() {
        // Return the number of questions (assumes questions and results are of equal size)
        return questionList.size();
    }
}
