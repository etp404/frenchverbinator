package uk.co.mould.matt.frenchverbinator;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import uk.co.mould.matt.frenchverbinator.settings.ui.AndroidSettingsView;
import uk.co.mould.matt.frenchverbinator.settings.SharedPrefsUserSettings;
import uk.co.mould.matt.frenchverbinator.settings.ui.SettingsPresenter;
import uk.co.mould.matt.frenchverbinator.settings.ui.SettingsPresenterImpl;
import uk.co.mould.matt.frenchverbinator.settings.ui.SettingsView;

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
