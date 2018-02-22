package no.ntnu.stud.larsjny.lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import no.ntnu.stud.larsjny.lab2.prefs.Preferences;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .add(new Preferences(), "Preferences")
                .commit();
    }
}
