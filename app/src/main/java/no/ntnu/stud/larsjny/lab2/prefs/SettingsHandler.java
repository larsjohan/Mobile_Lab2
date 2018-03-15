package no.ntnu.stud.larsjny.lab2.prefs;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import no.ntnu.stud.larsjny.lab2.ListActivity;
import no.ntnu.stud.larsjny.lab2.R;

/**
 * Created by Lars Johan on 13.03.2018.
 */

public class SettingsHandler implements SharedPreferences.OnSharedPreferenceChangeListener {

    private Activity activity;

    public SettingsHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Log.d(this.activity.getString(R.string.LogTag), "Preference changed: " + key);

        ((ListActivity) this.activity).updateMembersFromPreferences();
    }
}
