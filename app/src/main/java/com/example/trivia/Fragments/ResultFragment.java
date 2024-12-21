package com.example.trivia.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.trivia.R;

import java.util.ArrayList;

public class ResultFragment extends Fragment {
    // Argument keys for passing data between fragments
    private static final String ARG_QUESTION_TXT = "question_txt";
    private static final String ARG_OPTIONS = "options";
    private static final String ARG_CORRECT_ANSWER = "correct_answer";
    private static final String ARG_SELECTED_ANSWER = "selected_answer";

    // Required empty constructor
    public ResultFragment() {
    }

    // Factory method to create a new instance of this fragment with arguments
    public static ResultFragment newInstance(String questionTxt, ArrayList<String> options, int correctAnswer, int selectedAnswer) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION_TXT, questionTxt);
        args.putStringArrayList(ARG_OPTIONS, options);
        args.putInt(ARG_CORRECT_ANSWER, correctAnswer);
        args.putInt(ARG_SELECTED_ANSWER, selectedAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        // Get references to UI components
        TextView questionTV = view.findViewById(R.id.tv_question_result);
        TextView[] optionChars = {
                view.findViewById(R.id.tv_option_char_a),
                view.findViewById(R.id.tv_option_char_b),
                view.findViewById(R.id.tv_option_char_c),
                view.findViewById(R.id.tv_option_char_d)
        };

        TextView[] optionTexts = {
                view.findViewById(R.id.tv_option_txt_a),
                view.findViewById(R.id.tv_option_txt_b),
                view.findViewById(R.id.tv_option_txt_c),
                view.findViewById(R.id.tv_option_txt_d)
        };

        TextView conclusionTV = view.findViewById(R.id.tv_correctness);

        // Retrieve arguments passed to this fragment
        if (getArguments() != null) {
            String questionTxt = getArguments().getString(ARG_QUESTION_TXT);
            ArrayList<String> optionList = getArguments().getStringArrayList(ARG_OPTIONS);
            int correctAnswer = getArguments().getInt(ARG_CORRECT_ANSWER);
            int selectedAnswer = getArguments().getInt(ARG_SELECTED_ANSWER);

            // Set the question text
            questionTV.setText(questionTxt);

            // Populate option texts if available
            if(optionList != null) {
                for (int i = 0; i < optionList.size(); i++) {
                    optionTexts[i].setText(optionList.get(i));
                }
            }

            // Circle the correct answer in green
            optionChars[correctAnswer - 1].setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.circle_border_correct));

            if (correctAnswer == selectedAnswer) {
                // User selected the correct answer
                conclusionTV.setText(getString(R.string.correct_msg));
            } else {
                // Circle the wrong answer in red and display incorrect message
                optionChars[selectedAnswer - 1].setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.circle_border_incorrect));
                conclusionTV.setText(getString(R.string.incorrect_msg));
            }
        }
        return view;
    }
}
