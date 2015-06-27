package uk.co.mould.matt.frenchverbinator.settings;

import android.app.Activity;
import android.os.Bundle;

import matt.mould.co.uk.android.R;
import uk.co.mould.matt.ui.SettingsPresenter;

public final class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

    }
}
