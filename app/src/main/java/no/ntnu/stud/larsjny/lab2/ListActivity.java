package no.ntnu.stud.larsjny.lab2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import no.ntnu.stud.larsjny.lab2.prefs.SettingsHandler;
import no.ntnu.stud.larsjny.lab2.tasks.DownloadFeedTask;

public class ListActivity extends AppCompatActivity {

    private ListView list;


    private RssListAdapter adapter;
    private ArrayList<Article> articles;

    private String url;
    private int frequency;
    private int feedSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(new SettingsHandler(this));

        this.articles = new ArrayList<>();

        this.list = findViewById(R.id.list);
        this.adapter = new RssListAdapter(this,0, this.articles);

        this.list.setAdapter(this.adapter);
        updateFeed();
    }

    /**
     * Loads the newest articles from the specified RSS-URL
     */
    public void updateFeed(){
        update();                           // Fetch parameters from Prefs
        this.articles.clear();              // Clear the loaded articles
        fetchSource();                      // Fetch the new feed and update list
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


        DownloadFeedTask downloader = new DownloadFeedTask(this, this.feedSize);

        downloader.execute(rssurl);

    }

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
            updateFeed();
        }

        return false;
    }


    /**
     * Called when preferences change
     */
    public void update() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        this.url = pref.getString("rssurl", "");
        this.frequency = Integer.parseInt(pref.getString("frequency", getString(R.string.frequencyDefault)));
        this.feedSize = Integer.parseInt(pref.getString("feedSize", getString(R.string.itemsDefault)));
    }

    public void addArticles(ArrayList<Article> articles) {
        this.articles.clear();
        this.articles.addAll(articles);
        this.adapter.notifyDataSetChanged();
    }

    public RssListAdapter getAdapter() {
        return this.adapter;
    }

}
