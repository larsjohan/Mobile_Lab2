package no.ntnu.stud.larsjny.lab2.prefs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import no.ntnu.stud.larsjny.lab2.prefs.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load Settings fragment
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
