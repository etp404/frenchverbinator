package uk.co.mould.matt.frenchverbinator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import matt.mould.co.uk.android.R;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.ui.SettingsPresenter;

class SettingsView extends LinearLayout {
    private Map<MoodAndTense, Integer> tenseToId = new HashMap<>();

    private SettingsPresenter settingsPresenter;

    public SettingsView(Context context) {
        super(context);
    }

    public SettingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    static SettingsView createView(ViewGroup viewGroup) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        SettingsView settingsView = (SettingsView) layoutInflater.inflate(R.layout.settings_layout, viewGroup, false);
        viewGroup.addView(settingsView);
        return settingsView;
    }

    public void showOptions(ArrayList<MoodAndTense> moodAndTenses) {
        for (final MoodAndTense moodAndTense : moodAndTenses) {
            CheckBox checkBox = new CheckBox(getContext());
            Integer id = generateViewId();
            tenseToId.put(moodAndTense, id);
            checkBox.setId(id);
            checkBox.setText(moodAndTense.toString());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        settingsPresenter.addToSelectedTenses(moodAndTense);
                    } else {
                        settingsPresenter.removeSelectedTenses(moodAndTense);
                    }
                }
            });
            this.addView(checkBox);
        }
    }

    public void setPresenter(SettingsPresenter settingsPresenter) {
        this.settingsPresenter = settingsPresenter;
    }

    public void checkOptions(ArrayList<MoodAndTense> moodAndTenses) {
        for (final MoodAndTense moodAndTense : moodAndTenses) {
            ((CheckBox) findViewById(tenseToId.get(moodAndTense))).setChecked(true);
        }
    }
}
