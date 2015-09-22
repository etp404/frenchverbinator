package uk.co.mould.matt.frenchverbinator.settings.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.frenchverbinator.R;

public class AndroidSettingsView extends LinearLayout implements SettingsView {
    private Map<Class<? extends MoodAndTense>, Integer> tenseToId = new HashMap<>();

    private SettingsPresenter settingsPresenter;
    private static LayoutInflater layoutInflater;

    public AndroidSettingsView(Context context) {
        super(context);
    }

    public AndroidSettingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AndroidSettingsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static SettingsView createView(ViewGroup viewGroup) {
        layoutInflater = LayoutInflater.from(viewGroup.getContext());
        AndroidSettingsView settingsView = (AndroidSettingsView) layoutInflater.inflate(
                R.layout.settings_layout, viewGroup, false);
        viewGroup.addView(settingsView);
        return settingsView;
    }

    @Override
    public void showOptions(List<MoodAndTense> moodAndTenses) {
        Integer id = 0;
        for (final MoodAndTense moodAndTense : moodAndTenses) {
            id++;
            OptionsCheckBox checkBox = (OptionsCheckBox) layoutInflater.inflate(R.layout.option_checkbox, null);

            tenseToId.put(moodAndTense.getClass(), id);
            checkBox.setId(id);
            checkBox.setText(moodAndTense.toString());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        settingsPresenter.addToSelectedTenses(moodAndTense);
                    } else {
                        settingsPresenter.removeFromSelectedTenses(moodAndTense);
                    }
                }
            });
            this.addView(checkBox);
        }
    }

    @Override
    public void setPresenter(SettingsPresenter settingsPresenter) {
        this.settingsPresenter = settingsPresenter;
    }

    @Override
    public void checkOptions(List<MoodAndTense> moodAndTenses) {
        for (final MoodAndTense moodAndTense : moodAndTenses) {
            ((CheckBox) findViewById(tenseToId.get(moodAndTense.getClass()))).setChecked(true);
        }
    }
}
