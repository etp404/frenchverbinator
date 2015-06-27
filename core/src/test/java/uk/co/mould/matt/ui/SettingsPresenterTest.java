package uk.co.mould.matt.ui;

import junit.framework.TestCase;

import org.junit.Test;

import uk.co.mould.matt.data.tenses.ImperfectIndicative;
import uk.co.mould.matt.data.tenses.MoodAndTense;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class SettingsPresenterTest extends TestCase {

    @Test
    public void testThatWhenViewTellsPresenterToStoreATenseSettingsIsInformed() {
        FakeStoredUserSettings fakeStoredUserSettings = new FakeStoredUserSettings();

        SettingsPresenter settingsPresenter = new SettingsPresenterImpl(fakeStoredUserSettings);
        settingsPresenter.addToSelectedTenses(new ImperfectIndicative());
        assertThat(fakeStoredUserSettings.hasStored, instanceOf(ImperfectIndicative.class));
    }

    @Test
    public void testThatWhenViewTellsPresenterToRemoveATenseSettingsIsInformed() {
        FakeStoredUserSettings fakeStoredUserSettings = new FakeStoredUserSettings();

        SettingsPresenter settingsPresenter = new SettingsPresenterImpl(fakeStoredUserSettings);
        settingsPresenter.removeFromSelectedTenses(new ImperfectIndicative());
        assertThat(fakeStoredUserSettings.hasRemoved, instanceOf(ImperfectIndicative.class));
    }

    private static class SettingsPresenterImpl implements SettingsPresenter {
        private FakeStoredUserSettings storedUserSettings;

        public SettingsPresenterImpl(FakeStoredUserSettings storedUserSettings) {
            this.storedUserSettings = storedUserSettings;
        }

        @Override
        public void addToSelectedTenses(MoodAndTense moodAndTense) {
            storedUserSettings.addToIncludedTenses(moodAndTense);
        }

        @Override
        public void removeFromSelectedTenses(MoodAndTense moodAndTense) {
            storedUserSettings.removeFromIncludedTenses(moodAndTense);
        }
    }

    private class FakeStoredUserSettings {
        MoodAndTense hasStored;
        MoodAndTense hasRemoved;

        public void addToIncludedTenses(MoodAndTense moodAndTense) {
            hasStored = moodAndTense;
        }

        public void removeFromIncludedTenses(MoodAndTense moodAndTense) {
            hasRemoved = moodAndTense;
        }
    }
}