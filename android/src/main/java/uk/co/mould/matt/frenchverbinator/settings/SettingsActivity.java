package uk.co.mould.matt.frenchverbinator.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import matt.mould.co.uk.android.R;
import uk.co.mould.matt.frenchverbinator.AndroidSettingsView;
import uk.co.mould.matt.frenchverbinator.SettingsView;
import uk.co.mould.matt.ui.SettingsPresenter;
import uk.co.mould.matt.ui.SettingsPresenterImpl;

public final class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity_layout);

        ViewGroup activityTestViewGroup = (ViewGroup) findViewById(R.id.settings_activity_parent);
        SettingsView settingsView = AndroidSettingsView.createView(activityTestViewGroup);
        SharedPrefsUserSettings storedUserSettings =
                new SharedPrefsUserSettings(getSharedPreferences(SharedPrefsUserSettings.SETTINGS, 0));
        SettingsPresenter settingsPresenter = new SettingsPresenterImpl(storedUserSettings, settingsView);
        settingsView.setPresenter(settingsPresenter);
        settingsPresenter.updateView();
    }

}
