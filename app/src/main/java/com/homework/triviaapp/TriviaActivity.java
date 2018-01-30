/*
HomeWork 4
TriviaActivity.java
Hozefa Haveliwala, Nikhil Nagori    Group 29
 */

package com.homework.triviaapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity implements GetImage.LoadImage{

    ArrayList<TriviaQuestions> questions;
    Button prev, next;
    ImageView imageQuestion;
    int count =0, len;
    TextView qNumber, question,textView_timer;
    RadioGroup radioGroupChoice;
    ProgressBar progressBar_loadingimage;
    TextView textView_loadingImage;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        prev = (Button)findViewById(R.id.button_prev);
        next = (Button)findViewById(R.id.button_next);
        imageQuestion = (ImageView)findViewById(R.id.imageView_questionImage);
        qNumber = (TextView)findViewById(R.id.textView_question_number);
        question = (TextView)findViewById(R.id.textView_question);
        radioGroupChoice = (RadioGroup)findViewById(R.id.radioGroup_choice);
        textView_timer = (TextView) findViewById(R.id.textView_timer);
        progressBar_loadingimage = (ProgressBar)findViewById(R.id.progressBar_loadingImage);
        textView_loadingImage = (TextView)findViewById(R.id.textView_loadingImage);

        prev.setEnabled(false);
        questions = new ArrayList<TriviaQuestions>();
        questions = getIntent().getParcelableArrayListExtra(getResources().getString(R.string.key_QuestionList));

        len = questions.size()-1;

        qNumber.setText(questions.get(count).getId());
        question.setText(questions.get(count).getQuestion());

        myTimer();
        imageQuestion.setImageDrawable(null);
        int csize = questions.get(count).getChoice().size();
        if(questions.get(count).getImage() != ""){
            new GetImage(TriviaActivity.this).execute(questions.get(count).getImage());
        }
        for(int i=0;i<csize;i++){
            addChoice(questions.get(count).getChoice().get(i),i+1);
        }

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questions.get(count).setUser_answer(radioGroupChoice.getCheckedRadioButtonId());
                count --;
                radioGroupChoice.removeAllViews();
                imageQuestion.setImageDrawable(null);
                if(count == 0)
                    prev.setEnabled(false);

                if(questions.get(count).getImage() != ""){
                    new GetImage(TriviaActivity.this).execute(questions.get(count).getImage());
                }
                qNumber.setText(questions.get(count).getId());
                question.setText(questions.get(count).getQuestion());
                int csize = questions.get(count).getChoice().size();
                for(int i=0;i<csize;i++){
                    addChoice(questions.get(count).getChoice().get(i),i+1);
                }
                radioGroupChoice.check(questions.get(count).getUser_answer());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questions.get(count).setUser_answer(radioGroupChoice.getCheckedRadioButtonId());
                count++;
                radioGroupChoice.removeAllViews();
                imageQuestion.setImageDrawable(null);
                prev.setEnabled(true);
                if(count == len){
                    next.setText(getResources().getString(R.string.submit));
                }
                if(count == len+1){
                    /*Intent i = new Intent(TriviaActivity.this,StatsActivity.class);
                    i.putParcelableArrayListExtra(getResources().getString(R.string.key_QuestionList),questions);
                    startActivity(i);
                    finish();*/
                    countDownTimer.cancel();
                    callStatsActivity();
                }
                else{
                    if(questions.get(count).getImage() != ""){
                        new GetImage(TriviaActivity.this).execute(questions.get(count).getImage());
                    }
                    qNumber.setText(questions.get(count).getId());
                    question.setText(questions.get(count).getQuestion());
                    int csize = questions.get(count).getChoice().size();
                    for(int i=0;i<csize;i++){
                        addChoice(questions.get(count).getChoice().get(i),i+1);
                    }
                    radioGroupChoice.check(questions.get(count).getUser_answer());
                }
            }
        });

        //Toast.makeText(this,questions.size()+"" , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setImage(Bitmap image) {
        progressBar_loadingimage.setVisibility(View.GONE);
        textView_loadingImage.setVisibility(View.GONE);
        imageQuestion.setVisibility(View.VISIBLE);
        imageQuestion.setImageBitmap(image);
    }

    @Override
    public void setProgressBar() {
        progressBar_loadingimage.setVisibility(View.VISIBLE);
        textView_loadingImage.setVisibility(View.VISIBLE);
        imageQuestion.setImageDrawable(null);
    }

    public void addChoice(String choice, int id){
        RadioButton rb = new RadioButton(TriviaActivity.this);
        rb.setText(choice);
        rb.setId(id);
        radioGroupChoice.addView(rb);
    }

    private void callStatsActivity(){
        Intent i = new Intent(TriviaActivity.this,StatsActivity.class);
        i.putParcelableArrayListExtra(getResources().getString(R.string.key_QuestionList),questions);
        startActivity(i);
        finish();
    }

    private void myTimer(){
       countDownTimer =  new CountDownTimer(120000,1000){

            @Override
            public void onTick(long l) {
                textView_timer.setText("Time Left: " +l/1000+ " seconds");
            }

            @Override
            public void onFinish() {
                callStatsActivity();
            }
        }.start();
    }
}
