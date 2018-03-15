package no.ntnu.stud.larsjny.lab2.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import no.ntnu.stud.larsjny.lab2.util.Article;
import no.ntnu.stud.larsjny.lab2.ListActivity;
import no.ntnu.stud.larsjny.lab2.R;

/**
 * Downloads the feed in a background thread
 */
public class DownloadFeedTask extends AsyncTask<String, Integer, Long> {

    private Activity activity;

    private View progress;

    private ArrayList<Article> articles;

    private int limit;

    public DownloadFeedTask(Activity activity, int limit){
        super();
        this.activity = activity;
        this.articles = new ArrayList<>();
        this.limit = limit;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        this.progress = this.activity.findViewById(R.id.progressView);
        this.progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Long doInBackground(String... urls) {

        if (urls.length <= 0)    // No URLs set. Quit
            return 0L;

        try {

            Log.d(this.activity.getString(R.string.LogTag), "Fetching URLS: " + Arrays.toString(urls));

            SyndFeed feed = getFeed(urls[0]);

            List<SyndEntry> entries = feed.getEntries();

            for (int i = 0; i < this.limit; i++){
                SyndEnclosure image = null;

                for(SyndEnclosure enclosure : entries.get(i).getEnclosures()){
                    String type = enclosure.getType();

                    if (type.equals("image/jpeg") || type.equals("image/jpg")){
                        image = enclosure;
                        break;
                    }
                }

                String imgUrl = (null != image) ? image.getUrl() : null;
                String title = entries.get(i).getTitle();
                Date date = entries.get(i).getPublishedDate();
                String author = entries.get(i).getAuthor();
                String summary = entries.get(i).getDescription().getValue();
                String content = entries.get(i).getSource().getDescription();

                this.articles.add(new Article(this.activity, imgUrl, title, summary, content, date, author));
            }

        } catch (FeedException e) {
            Log.d(this.activity.getString(R.string.LogTag), "Invalid Feed: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(this.activity.getString(R.string.LogTag), "Unable to read URL: " + e.getMessage());
            e.printStackTrace();
        } catch (URISyntaxException e) {
            Log.d(this.activity.getString(R.string.LogTag), "Invalid URL: " + e.getMessage());
            e.printStackTrace();
        }


        return 1L;
    }



    @Override
    protected void onPostExecute(Long aLong) {
        ((ListActivity) this.activity).addArticles(this.articles);
        this.progress.setVisibility(View.GONE);
    }



    private SyndFeed getFeed(String url) throws IOException, URISyntaxException, FeedException{
        URL uri = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        return new SyndFeedInput().build(new XmlReader(connection.getInputStream()));
    }
}
