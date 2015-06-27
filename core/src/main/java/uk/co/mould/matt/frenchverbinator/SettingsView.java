package uk.co.mould.matt.frenchverbinator;

import java.util.List;

import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.ui.SettingsPresenter;

public interface SettingsView {
    void showOptions(List<MoodAndTense> moodAndTenses);

    void setPresenter(SettingsPresenter settingsPresenter);

    void checkOptions(List<MoodAndTense> moodAndTenses);
}
