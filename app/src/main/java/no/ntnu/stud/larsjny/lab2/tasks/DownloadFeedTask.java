package no.ntnu.stud.larsjny.lab2.tasks;

import android.os.AsyncTask;

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
import java.util.Date;
import java.util.List;

import no.ntnu.stud.larsjny.lab2.util.Article;
import no.ntnu.stud.larsjny.lab2.util.Callback;
import no.ntnu.stud.larsjny.lab2.util.RssListAdapter;

/**
 * Downloads the feed in a background thread
 */
public class DownloadFeedTask extends AsyncTask<String, Boolean, ArrayList<Article>> {


    private Callback callback;
    private RssListAdapter listAdapter;


    private int limit;

    public DownloadFeedTask(Callback callback, RssListAdapter listAdapter, int limit){
        super();
        this.callback = callback;
        this.listAdapter = listAdapter;
        this.limit = limit;

    }


    @Override
    protected ArrayList<Article> doInBackground(String... urls) {

        if (urls.length <= 0) {    // No URLs set. Quit
            publishProgress(true);
            return null;
        }

        ArrayList<Article> articles = new ArrayList<>();

        try {

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

                articles.add(new Article(this.listAdapter, imgUrl, title, summary, content, date, author));
            }

        } catch (FeedException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        publishProgress(true);
        return articles;
    }

    @Override
    protected void onProgressUpdate(Boolean... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        super.onPostExecute(articles);

        this.listAdapter.addAll(articles);
        this.listAdapter.notifyDataSetChanged();
        this.callback.doCallback();
    }

    private SyndFeed getFeed(String url) throws IOException, URISyntaxException, FeedException{
        URL uri = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        return new SyndFeedInput().build(new XmlReader(connection.getInputStream()));
    }
}
