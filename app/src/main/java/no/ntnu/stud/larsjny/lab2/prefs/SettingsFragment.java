package no.ntnu.stud.larsjny.lab2.prefs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.MenuItem;

import no.ntnu.stud.larsjny.lab2.R;

/**
 * Created by Lars Johan on 22.02.2018.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }




}
