/*
HomeWork 4
JsonParser.java
Hozefa Haveliwala, Nikhil Nagori    Group 29
 */

package com.homework.triviaapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nikhil on 07/02/2017.
 */

public class JsonParser {

    ArrayList<TriviaQuestions> triviaQuestions;
    ArrayList<String> choices;

    public ArrayList<TriviaQuestions> parse(String json){

        try {
            triviaQuestions = new ArrayList<TriviaQuestions>();
            JSONObject jObject = new JSONObject(json);
            JSONArray jArray = jObject.getJSONArray("questions");
            for (int i = 0; i < jArray.length(); i++){
                choices = new ArrayList<String>();
                JSONObject jQuestions = jArray.getJSONObject(i);

                TriviaQuestions tq = new TriviaQuestions();
                tq.setId(jQuestions.getString("id"));
                tq.setQuestion(jQuestions.getString("text"));
                if(jQuestions.has("image")){
                    tq.setImage(jQuestions.getString("image"));
                }
                else{
                    tq.setImage("");
                }
                JSONObject obj = jQuestions.getJSONObject("choices");
                JSONArray jChoices = obj.getJSONArray("choice");
                for(int j=0;j<jChoices.length();j++){
                    choices.add(jChoices.getString(j));
                }
                tq.setChoice(choices);
                tq.setCorrect_answer(Integer.parseInt(obj.getString("answer")));

                triviaQuestions.add(tq);
                //Log.d("JSON",tq.toString());

            }
            return triviaQuestions;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
