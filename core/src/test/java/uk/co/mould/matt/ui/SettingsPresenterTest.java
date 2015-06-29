package uk.co.mould.matt.ui;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import uk.co.mould.matt.data.SupportedMoodsAndTenses;
import uk.co.mould.matt.data.tenses.ImperfectIndicative;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentSubjunctive;
import uk.co.mould.matt.frenchverbinator.SettingsView;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SettingsPresenterTest extends TestCase {

    public void testThatWhenViewTellsPresenterToStoreATenseSettingsIsInformed() {
        FakeStoredUserSettings fakeStoredUserSettings = new FakeStoredUserSettings();

        SettingsPresenter settingsPresenter = new SettingsPresenterImpl(fakeStoredUserSettings,
                null);
        settingsPresenter.addToSelectedTenses(new ImperfectIndicative());
        assertThat(fakeStoredUserSettings.hasStored, instanceOf(ImperfectIndicative.class));
    }

    public void testThatWhenViewTellsPresenterToRemoveATenseSettingsIsInformed() {
        FakeStoredUserSettings fakeStoredUserSettings = new FakeStoredUserSettings();

        SettingsPresenter settingsPresenter = new SettingsPresenterImpl(fakeStoredUserSettings,
                null);
        settingsPresenter.removeFromSelectedTenses(new ImperfectIndicative());
        assertThat(fakeStoredUserSettings.hasRemoved, instanceOf(ImperfectIndicative.class));
    }

    public void testThatViewWillUpdateViewAsExpected() {
        FakeSettingsView fakeSettingsView = new FakeSettingsView();
        FakeStoredUserSettings fakeStoredUserSettings = new FakeStoredUserSettings();
        SettingsPresenter settingsPresenter =
                new SettingsPresenterImpl(fakeStoredUserSettings, fakeSettingsView);
        List<MoodAndTense> includedMoodsAndTenses = new ArrayList<MoodAndTense>(){{add(new ImperfectIndicative()); add(new PresentSubjunctive());}};
        fakeStoredUserSettings.includedTenses = includedMoodsAndTenses;
        settingsPresenter.updateView();

        assertThat(fakeSettingsView.shownOptions, is(SupportedMoodsAndTenses.ALL));
        assertThat(fakeSettingsView.moodsAndTensesChecked, is(includedMoodsAndTenses));
    }

    private class FakeSettingsView implements SettingsView {

        List<MoodAndTense> shownOptions;
        List<MoodAndTense> moodsAndTensesChecked;

        @Override
        public void showOptions(List<MoodAndTense> moodAndTenses) {
            shownOptions = moodAndTenses;
        }

        @Override
        public void setPresenter(SettingsPresenter settingsPresenter) {

        }

        @Override
        public void checkOptions(List<MoodAndTense> moodAndTenses) {
            moodsAndTensesChecked = moodAndTenses;
        }
    }

    private class FakeStoredUserSettings implements StoredUserSettings {
        MoodAndTense hasStored;
        MoodAndTense hasRemoved;
        List<MoodAndTense> includedTenses;

        public void addToIncludedTenses(MoodAndTense moodAndTense) {
            hasStored = moodAndTense;
        }

        public void removeFromIncludedTenses(MoodAndTense moodAndTense) {
            hasRemoved = moodAndTense;
        }

        public List<MoodAndTense> includedTenses() {
            return includedTenses;
        }
    }
}