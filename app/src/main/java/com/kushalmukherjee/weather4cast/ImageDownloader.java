package com.kushalmukherjee.weather4cast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... strings) {

        String imgDownloadUrl = strings[0];
        try {

            URL imgUrl = new URL(imgDownloadUrl);

            HttpURLConnection urlConnection = (HttpURLConnection) imgUrl.openConnection();

            InputStream in = urlConnection.getInputStream();
            Bitmap imgBitmap = BitmapFactory.decodeStream(in);
            return imgBitmap;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
