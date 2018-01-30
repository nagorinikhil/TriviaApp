/*
HomeWork 4
TriviaQuestions.java
Hozefa Haveliwala, Nikhil Nagori    Group 29
 */

package com.homework.triviaapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Nikhil on 07/02/2017.
 */

public class TriviaQuestions implements Parcelable {
    String question, image, id;
    int correct_answer, user_answer = -1;
    ArrayList<String> choice = new ArrayList<String>();

    public TriviaQuestions() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = "Q"+String.valueOf(Integer.parseInt(id) + 1);
    }

    public int getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(int correct_answer) {
        this.correct_answer = correct_answer;
    }

    public int getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(int user_answer) {
        this.user_answer = user_answer;
    }

    public ArrayList<String> getChoice() {
        return choice;
    }

    public void setChoice(ArrayList<String> choice) {
        this.choice = choice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(id);
        dest.writeString(question);
        dest.writeString(image);
        dest.writeInt(user_answer);
        dest.writeInt(correct_answer);
        dest.writeStringList(choice);
    }

    public static final Parcelable.Creator<TriviaQuestions> CREATOR = new Parcelable.Creator<TriviaQuestions>() {
        public TriviaQuestions createFromParcel(Parcel in) {
            return new TriviaQuestions(in);
        }

        public TriviaQuestions[] newArray(int size) {
            return new TriviaQuestions[size];
        }

    };

    private TriviaQuestions(Parcel in) {
        this.id = in.readString();
        this.question = in.readString();
        this.image = in.readString();
        this.user_answer = in.readInt();
        this.correct_answer = in.readInt();
        in.readStringList(this.choice);
    }

}
