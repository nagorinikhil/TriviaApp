/*
HomeWork 4
GetImage.java
Hozefa Haveliwala, Nikhil Nagori    Group 29
 */

package com.homework.triviaapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Nikhil on 08/02/2017.
 */

public class GetImage extends AsyncTask<String, Void, Bitmap>{

    LoadImage load;

    public GetImage(LoadImage load) {
        this.load = load;
    }

    @Override
    protected void onPreExecute() {
        load.setProgressBar();
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            Bitmap image = BitmapFactory.decodeStream(conn.getInputStream());
            return image;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        load.setImage(result);
        super.onPostExecute(result);
    }

    static public interface LoadImage{
        public void setImage(Bitmap image);
        public void setProgressBar();
    }
}
