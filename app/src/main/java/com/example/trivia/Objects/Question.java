package com.example.trivia.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/*
This class holds information about a single question with 4 possible answers.
It is up to the user to know which answer is correct.
*/

public class Question implements Serializable, Parcelable {
    private String questionText; // The trivia question itself
    private List<String> answers; // 4 answers. First one is correct

    /**
     * Constructor
     *
     * @param questionText The question itself
     * @param answers      4 different answers.
     */
    public Question(String questionText, List<String> answers) {
        this.questionText = questionText;
        this.answers = answers;
    }

    // Getters
    public String getQuestionText() {
        return questionText;
    }

    public List<String> getAnswers() {
        return answers;
    }

    // Parcelable Implementation
    protected Question(Parcel in) {
        questionText = in.readString();
        answers = in.createStringArrayList();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionText);
        dest.writeStringList(answers);
    }
}