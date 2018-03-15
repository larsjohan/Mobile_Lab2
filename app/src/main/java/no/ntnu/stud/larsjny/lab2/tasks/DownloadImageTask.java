package no.ntnu.stud.larsjny.lab2.tasks;

import android.app.Activity;
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
import no.ntnu.stud.larsjny.lab2.ListActivity;

/**
 * Downloads the RSS-image in the backound
 */
public class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {

    private Activity activity;

    private Article article;



    public DownloadImageTask(Activity activity, Article article) {
        this.activity = activity;
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
            Log.d("Lab2", "Unable to downlad Image for article: " + this.article.getTitle());
            return null;
        }

    }

    @Override
    protected void onPostExecute(Bitmap image) {
        this.article.setImage(image);
        ((ListActivity) this.activity).getAdapter().notifyDataSetChanged();
    }
}
