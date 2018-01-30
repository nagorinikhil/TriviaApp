/*
HomeWork 4
StatsActivity.java
Hozefa Haveliwala, Nikhil Nagori    Group 29
 */

package com.homework.triviaapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    ArrayList<TriviaQuestions> questions;
    int correct=0;
    ArrayList<String> choices;
    LinearLayout linearLayout_result;
    ProgressBar progressBar_result;
    TextView textView_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        linearLayout_result = (LinearLayout)findViewById(R.id.linearLayout_result);
        progressBar_result = (ProgressBar)findViewById(R.id.progressBar_result);
        textView_result = (TextView)findViewById(R.id.textView_result);

        questions = new ArrayList<TriviaQuestions>();
        questions = getIntent().getParcelableArrayListExtra(getResources().getString(R.string.key_QuestionList));

        int len = questions.size();
        for(int i=0; i<len; i++){
            choices = new ArrayList<String>();
            if(questions.get(i).getCorrect_answer() == questions.get(i).getUser_answer()){
                correct++;
            }
            else {
                choices = questions.get(i).getChoice();
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.result_layout,null);
                TextView tv1 = (TextView)addView.findViewById(R.id.textView_statsQuestion);
                TextView tv2 = (TextView)addView.findViewById(R.id.textView_userAnswer);
                TextView tv3 = (TextView)addView.findViewById(R.id.textView_correctAnswer);
                tv1.setText(questions.get(i).getQuestion());
                if(questions.get(i).getUser_answer() == -1){
                    tv2.setText("No Answer");
                } else {
                    tv2.setText(choices.get(questions.get(i).getUser_answer()-1).toString());
                }
                tv2.setBackgroundColor(Color.RED);
                tv3.setText(choices.get(questions.get(i).getCorrect_answer()-1).toString());
                linearLayout_result.addView(addView);
            }
        }

        double result = (correct * 100.0/len);
        int final_result  = (int) Math.round(result);
        progressBar_result.setProgress(final_result);
        textView_result.setText(String.valueOf(progressBar_result.getProgress()) + "%");

        findViewById(R.id.button_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
