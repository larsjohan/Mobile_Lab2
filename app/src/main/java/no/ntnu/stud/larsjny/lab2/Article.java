package no.ntnu.stud.larsjny.lab2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Lars Johan on 13.03.2018.
 */

public class Article {

    private Activity activity;

    private String imgUrl;

    private String title;

    private String summary;

    private String content;

    private Bitmap image;

    public Article(Activity activity, String imgUrl, String title, String summary, String content) {
        this.activity = activity;
        this.imgUrl = imgUrl;
        this.title = title;
        this.summary = summary;
        this.content = content;
        downloadImage();
    }

    private void downloadImage() {
        if (this.imgUrl != null) {
            DownloadImage downloader = new DownloadImage(this.activity, this);
            downloader.execute(this.imgUrl);
        }
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }








    private class DownloadImage extends AsyncTask<String, Integer, Bitmap> {

        private Activity activity;

        private Article article;



        public DownloadImage(Activity activity, Article article) {
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

}
