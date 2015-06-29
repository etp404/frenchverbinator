package uk.co.mould.matt.frenchverbinator.settings.ui;

import uk.co.mould.matt.data.SupportedMoodsAndTenses;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.frenchverbinator.settings.StoredUserSettings;

public class SettingsPresenterImpl implements SettingsPresenter {
    private StoredUserSettings storedUserSettings;
    private SettingsView settingsView;

    public SettingsPresenterImpl(StoredUserSettings storedUserSettings, SettingsView settingsView) {
        this.storedUserSettings = storedUserSettings;
        this.settingsView = settingsView;
    }

    @Override
    public void addToSelectedTenses(MoodAndTense moodAndTense) {
        storedUserSettings.addToIncludedTenses(moodAndTense);
    }

    @Override
    public void removeFromSelectedTenses(MoodAndTense moodAndTense) {
        storedUserSettings.removeFromIncludedTenses(moodAndTense);
    }

    @Override
    public void updateView() {
        settingsView.showOptions(SupportedMoodsAndTenses.ALL);
        settingsView.checkOptions(storedUserSettings.includedTenses());
    }
}
