/*
HomeWork 4
MainActivity.java
Hozefa Haveliwala, Nikhil Nagori    Group 29
 */

package com.homework.triviaapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.color.holo_blue_bright;

public class MainActivity extends AppCompatActivity implements LoadTrivia.StartTrivia{

    ProgressBar pg;
    ImageView imageViewTrivia;
    Button button_start;
    ArrayList<TriviaQuestions> questions;
    TextView textView_ready;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pg = (ProgressBar)findViewById(R.id.progressBar_loading);
        imageViewTrivia = (ImageView)findViewById(R.id.imageView_trivia);
        button_start = (Button)findViewById(R.id.button_start);
        textView_ready = (TextView)findViewById(R.id.textView_triviaready);

        questions = new ArrayList<TriviaQuestions>();
        //imageViewTrivia.setVisibility(View.GONE);
        //textView_ready.setVisibility(View.GONE);

        if(isConnected() == true){
            new LoadTrivia(this).execute("http://dev.theappsdr.com/apis/trivia_json/index.php");
        }

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,TriviaActivity.class);
                i.putParcelableArrayListExtra(getResources().getString(R.string.key_QuestionList),questions);
                startActivity(i);
            }
        });

        findViewById(R.id.button_Exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void setUI(ArrayList<TriviaQuestions> tq) {
        pg.setVisibility(View.GONE);
        //imageViewTrivia.setVisibility(View.VISIBLE);
        //textView_ready.setVisibility(View.VISIBLE);
        textView_ready.setText(getResources().getString(R.string.triviaready));
        imageViewTrivia.setImageDrawable(getResources().getDrawable(R.drawable.trivia));
        button_start.setEnabled(true);
        button_start.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(holo_blue_bright)));
        questions = tq;
    }

    private boolean isConnected() {
        ConnectivityManager cM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cM.getActiveNetworkInfo();
        if ((networkInfo != null) && (networkInfo.isConnected() == true)) {
            return true;
        }
        return false;
    }

}
