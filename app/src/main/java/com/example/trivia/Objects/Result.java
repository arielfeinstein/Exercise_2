package com.example.trivia.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * This class will keep track of a single trivia question answer.
 */
public class Result implements Serializable, Parcelable {
    private int correctAnswer, selectedAnswer;

    // Constructors
    public Result(int correctAnswer, int selectedAnswer) {
        this.correctAnswer = correctAnswer;
        this.selectedAnswer = selectedAnswer;
    }

    public Result(int correctAnswer) {
        this(correctAnswer, 0);
    }

    // Getters
    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public boolean isCorrect() {
        return correctAnswer == selectedAnswer;
    }

    // Setter
    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    // Parcelable Implementation
    protected Result(Parcel in) {
        correctAnswer = in.readInt();
        selectedAnswer = in.readInt();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(correctAnswer);
        dest.writeInt(selectedAnswer);
    }

    @NonNull
    @Override
    public String toString() {
        return "Result{" +
                "correctAnswer=" + correctAnswer +
                ", selectedAnswer=" + selectedAnswer +
                '}';
    }
}