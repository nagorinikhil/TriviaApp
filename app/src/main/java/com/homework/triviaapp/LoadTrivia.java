/*
HomeWork 4
LoadTrivia.java
Hozefa Haveliwala, Nikhil Nagori    Group 29
 */
package com.homework.triviaapp;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Nikhil on 07/02/2017.
 */

public class LoadTrivia extends AsyncTask<String, Void, ArrayList<TriviaQuestions>>{

    StartTrivia start;

    public LoadTrivia(StartTrivia start) {
        this.start = start;
    }

    @Override
    protected ArrayList<TriviaQuestions> doInBackground(String... params) {
        try {
            ArrayList<TriviaQuestions> triviaQuestions = new ArrayList<TriviaQuestions>();
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sB = new StringBuilder();

            String line = "";
            while ((line = reader.readLine()) != null) {
                sB.append(line);
                //Log.d("SB", line);
            }
            String json = sB.toString();
            JsonParser jp = new JsonParser();
            triviaQuestions = jp.parse(json);
            return triviaQuestions;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<TriviaQuestions> tq) {
        if(tq != null){
            start.setUI(tq);
            super.onPostExecute(tq);
        }
    }

    static public interface StartTrivia{
        public void setUI(ArrayList<TriviaQuestions> tq);
    }

}
