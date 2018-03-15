package no.ntnu.stud.larsjny.lab2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import no.ntnu.stud.larsjny.lab2.prefs.SettingsActivity;
import no.ntnu.stud.larsjny.lab2.prefs.SettingsHandler;
import no.ntnu.stud.larsjny.lab2.tasks.DownloadFeedTask;
import no.ntnu.stud.larsjny.lab2.util.Article;
import no.ntnu.stud.larsjny.lab2.util.Callback;
import no.ntnu.stud.larsjny.lab2.util.RssListAdapter;

public class ListActivity extends AppCompatActivity implements Callback {

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
        this.adapter = new RssListAdapter( this,0, this.articles);

        this.list.setAdapter(this.adapter);

        this.list.setOnItemClickListener(this::displayContent);




        updateMembersFromPreferences();

        Log.d(getString(R.string.LogTag), "Scheduling update every " + this.frequency + " seconds");

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(this::updateFeed, this.frequency, this.frequency, TimeUnit.SECONDS);

        updateFeed();

    }




    /**
     * Shows the selected article's content.
     */
    public void displayContent(AdapterView<?> parent, View view, int position, long id){


        Article selected = this.adapter.getItem(position);

        if (selected != null) {
            Intent displayContent = new Intent(this, ContentActivity.class);

            //displayContent.putExtra("image", selected.getImage());
            displayContent.putExtra("title", selected.getTitle());
            displayContent.putExtra("date", selected.getDate());
            displayContent.putExtra("author", selected.getAuthor());
            displayContent.putExtra("content", selected.getSummary());

            startActivityForResult(displayContent, 0);
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Download a new feed and updateMembersFromPreferences list
     */
    private void fetchSource() {

        String rssurl = PreferenceManager.getDefaultSharedPreferences(this).getString("rssurl", "");

        DownloadFeedTask downloader = new DownloadFeedTask(this, this.adapter, this.feedSize);

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
            return true;
        }

        return false;
    }


    /**
     * Called when preferences change
     */
    public void updateMembersFromPreferences() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        this.url = pref.getString("rssurl", "");
        this.frequency = Integer.parseInt(pref.getString("frequency", getString(R.string.frequencyDefault)));
        this.feedSize = Integer.parseInt(pref.getString("feedSize", getString(R.string.itemsDefault)));
    }

    /**
     * Add articles to the list
     * @param articles
     */
    public void addArticles(ArrayList<Article> articles) {
        this.articles.clear();
        this.articles.addAll(articles);
        this.adapter.notifyDataSetChanged();
    }

    /**
     * Loads the newest articles from the specified RSS-URL
     */
    public void updateFeed(){
        Log.d(getString(R.string.LogTag), "UPDATING FEED");

        ConstraintLayout progress = findViewById(R.id.progressView);
        progress.setVisibility(View.VISIBLE);


        updateMembersFromPreferences();                           // Fetch parameters from Prefs
        this.articles.clear();              // Clear the loaded articles
        fetchSource();                      // Fetch the new feed and updateMembersFromPreferences list
    }

    /**
     * Run when the DownloadFeedTask is done
     */
    @Override
    public void doCallback() {
        findViewById(R.id.progressView).setVisibility(View.GONE);
    }
}
