package uk.co.mould.matt.frenchverbinator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import uk.co.mould.matt.frenchverbinator.settings.ui.AndroidSettingsView;
import uk.co.mould.matt.frenchverbinator.settings.SharedPrefsUserSettings;
import uk.co.mould.matt.frenchverbinator.settings.ui.SettingsPresenter;
import uk.co.mould.matt.frenchverbinator.settings.ui.SettingsPresenterImpl;
import uk.co.mould.matt.frenchverbinator.settings.ui.SettingsView;

public final class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity_layout);

        AndroidSettingsView settingsView = (AndroidSettingsView) findViewById(R.id.android_settings_view);

        SharedPrefsUserSettings storedUserSettings =
                new SharedPrefsUserSettings(getSharedPreferences(SharedPrefsUserSettings.SETTINGS, 0));
        SettingsPresenter settingsPresenter = new SettingsPresenterImpl(storedUserSettings, settingsView);
        settingsView.setPresenter(settingsPresenter);
        settingsPresenter.updateView();
    }

}
