package uk.co.mould.matt.frenchverbinator;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import uk.co.mould.matt.frenchverbinator.settings.ui.AndroidSettingsView;
import uk.co.mould.matt.frenchverbinator.settings.SharedPrefsUserSettings;
import uk.co.mould.matt.frenchverbinator.settings.ui.SettingsPresenter;
import uk.co.mould.matt.frenchverbinator.settings.ui.SettingsPresenterImpl;

public final class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Drawable upArrow = ContextCompat.getDrawable(getApplicationContext(), R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icons), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AndroidSettingsView settingsView = (AndroidSettingsView) findViewById(R.id.android_settings_view);

        SharedPrefsUserSettings storedUserSettings =
                new SharedPrefsUserSettings(getSharedPreferences(SharedPrefsUserSettings.SETTINGS, 0));
        SettingsPresenter settingsPresenter = new SettingsPresenterImpl(storedUserSettings, settingsView);
        settingsView.setPresenter(settingsPresenter);
        settingsPresenter.updateView();
    }

}
