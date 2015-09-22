package uk.co.mould.matt.frenchverbinator.settings.ui;

import java.util.List;

import uk.co.mould.matt.data.tenses.MoodAndTense;

public interface SettingsView {
    void showOptions(List<MoodAndTense> moodAndTenses);

    void setPresenter(SettingsPresenter settingsPresenter);

    void checkOptions(List<MoodAndTense> moodAndTenses);
}
