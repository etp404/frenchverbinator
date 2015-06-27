package uk.co.mould.matt.ui;

import uk.co.mould.matt.data.tenses.MoodAndTense;

public interface SettingsPresenter {
    void addToSelectedTenses(MoodAndTense moodAndTense);
    void removeFromSelectedTenses(MoodAndTense moodAndTense);
    void updateView();
}
