package uk.co.mould.matt.frenchverbinator.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import java.util.List;

import matt.mould.co.uk.android.R;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.frenchverbinator.AndroidSettingsView;
import uk.co.mould.matt.frenchverbinator.SettingsView;
import uk.co.mould.matt.ui.SettingsPresenter;
import uk.co.mould.matt.ui.SettingsPresenterImpl;
import uk.co.mould.matt.ui.StoredUserSettings;

public final class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity_layout);

        ViewGroup activityTestViewGroup = (ViewGroup) findViewById(R.id.settings_activity_parent);
        SettingsView settingsView = AndroidSettingsView.createView(activityTestViewGroup);
        SettingsPresenter settingsPresenter = new SettingsPresenterImpl(new SharedPrefsUserSettings(), settingsView);
        settingsView.setPresenter(settingsPresenter);
    }

    private static class SharedPrefsUserSettings implements StoredUserSettings {
        @Override
        public void addToIncludedTenses(MoodAndTense moodAndTense) {

        }

        @Override
        public void removeFromIncludedTenses(MoodAndTense moodAndTense) {

        }

        @Override
        public List<MoodAndTense> includedTenses() {
            return null;
        }
    }
}
