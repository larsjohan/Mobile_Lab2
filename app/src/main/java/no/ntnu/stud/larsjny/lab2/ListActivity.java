package no.ntnu.stud.larsjny.lab2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import no.ntnu.stud.larsjny.lab2.prefs.Preferences;

public class ListActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);



        list = findViewById(R.id.list);
    }

    /**
     * Loads the newest articles from the specified RSS-URL
     */
    public void updateList(){

    }

    /**
     * Shows the selected article's content.
     */
    public void displayContent(){
        Intent displayContent = new Intent(this, ContentActivity.class);

        list.getSelectedItem();

        startActivity(displayContent);
    }
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.action_preferences){

            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new Preferences())
                    .commit();

            return true;
        }

        return false;
    }
}
