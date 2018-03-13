package no.ntnu.stud.larsjny.lab2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.Touch;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.acl.Permission;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ListActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private ListView list;

    private int frequency;
    private int feedSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);


        list = findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.rss_list_item_layout);

        list.setAdapter(adapter);
    }

    /**
     * Loads the newest articles from the specified RSS-URL
     */
    public void updateFeed(){

    }

    /**
     * Shows the selected article's content.
     */
    public void displayContent(){
        Intent displayContent = new Intent(this, ContentActivity.class);

        list.getSelectedItem();

        startActivity(displayContent);
    }

    private void fetchSource() {

        String rssurl = PreferenceManager.getDefaultSharedPreferences(this).getString("rssurl", "");


        DownloadFeedTask downloader = new DownloadFeedTask(this);

        downloader.execute(rssurl);

    }





    /*
     *    HANDELING SETTINGS
     */






    /**
     * Adds the Settings button to the action bar
     *
     * @param menu the menu to add the button to
     * @return State of the operation
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    /**
     * Called when an item in the action bar is pressed.
     *
     * @param item The pressed button
     * @return weather the operation is a success or not
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == R.id.action_preferences){
            Log.d(getString(R.string.LogTag), "Loading Settings Fragment");

            Intent pref = new Intent(this, SettingsActivity.class);

            startActivity(pref);
            return true;
        } else if (item.getItemId() == R.id.action_update) {

            String url = PreferenceManager.getDefaultSharedPreferences(this).getString("rssurl", "");
            new DownloadFeedTask(this).execute(url);
        }

        return false;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Log.d(getString(R.string.LogTag), "Preference changed: " + key);

        switch(key){
            case "rssurl":      // URL has changed
                fetchSource(); break;
            case "frequency":   // Frequency has changed
                this.frequency = Integer.parseInt(sharedPreferences.getString(key, getString(R.string.frequencyDefault))); break;
            case "feedSize":    // Number of items to fetch has changed
                this.feedSize = Integer.parseInt(sharedPreferences.getString(key, getString(R.string.itemsDefault))); break;
        }
    }


    /*
     * HELPER METHODS
     */


    public boolean hasPermission(Context context, String permission) {

        if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, 123);
            return false; // No permission
        }

        return true;    // Permission already granted
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 123 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchSource();
        }
    }


    /*
     * INNER CLASSES
     */

    /**
     * Downloads the feed in a background thread
     */
    private class DownloadFeedTask extends AsyncTask<String, Integer, Long> {

        private Context context;

        private View progress;

        public DownloadFeedTask(Context context){
            super();
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = findViewById(R.id.progressView);
            this.progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Long doInBackground(String... urls) {

            if (urls.length <= 0)    // No URLs set. Quit
                return 0L;


            if(!hasPermission(this.context, Manifest.permission.INTERNET)) {
                Toast.makeText(this.context, "Need permission to continue", Toast.LENGTH_LONG);
            }


            try {

                Log.d(getString(R.string.LogTag), Arrays.toString(urls));

                SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(urls[0])));

                String feedType = feed.getFeedType();

                Log.d(getString(R.string.LogTag), "Feed type is: " + feedType);

            } catch (FeedException e) {
                Log.d(getString(R.string.LogTag), e.getMessage());

            } catch (IOException e) {
                Log.d(getString(R.string.LogTag), "Unable to read URL");
            }


            return 1L;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            this.progress.setVisibility(View.GONE);
        }
    }


}
