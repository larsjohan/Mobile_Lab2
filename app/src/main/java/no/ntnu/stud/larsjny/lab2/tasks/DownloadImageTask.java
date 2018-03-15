package no.ntnu.stud.larsjny.lab2.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import no.ntnu.stud.larsjny.lab2.util.Article;

/**
 * Downloads the RSS-image in the backound
 */
public class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {

    private Article article;



    public DownloadImageTask(Article article) {
        this.article = article;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        @NonNull String url = strings[0];

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream is = connection.getInputStream();
            Bitmap bitmapImage = BitmapFactory.decodeStream(is);

            return bitmapImage;

        } catch (IOException e) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        this.article.setImage(bitmap);
        this.article.getAdapter().notifyDataSetChanged();
    }
}
