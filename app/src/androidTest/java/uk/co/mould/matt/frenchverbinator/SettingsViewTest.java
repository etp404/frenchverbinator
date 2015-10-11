package uk.co.mould.matt.frenchverbinator;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Ignore;

import java.util.ArrayList;

import uk.co.mould.matt.data.tenses.ImperfectIndicative;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentConditional;
import uk.co.mould.matt.frenchverbinator.settings.ui.AndroidSettingsView;
import uk.co.mould.matt.frenchverbinator.settings.ui.OptionsCheckBox;
import uk.co.mould.matt.frenchverbinator.settings.ui.SettingsPresenter;
import uk.co.mould.matt.frenchverbinator.settings.ui.SettingsView;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;

public class SettingsViewTest extends AndroidTestCase {
    private AndroidSettingsView settingsView;
    private FakeSettingsPresenter fakeSettingsPresenter;
    private final PresentConditional presentConditional = new PresentConditional();
    private final ImperfectIndicative imperfectIndicative = new ImperfectIndicative();
    private ArrayList<MoodAndTense> supportedTenses = new ArrayList<MoodAndTense>() {{
        add(presentConditional);
        add(imperfectIndicative);
    }};;


    @Before
    public void setUp() throws Exception {
        super.setUp();

        Context context = getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        settingsView = (AndroidSettingsView)layoutInflater.inflate(R.layout.settings_activity_layout, null);

        fakeSettingsPresenter = new FakeSettingsPresenter();

        settingsView.setPresenter(fakeSettingsPresenter);
    }

    public void testThatMoodAndTenseOptionsAppear() {
        tellViewToAddTenseOptions(presentConditional, imperfectIndicative);

        assertEquals(((OptionsCheckBox) settingsView.findViewById(0)).getText(), presentConditional.toString());
        assertEquals(((OptionsCheckBox) settingsView.findViewById(1)).getText(), imperfectIndicative.toString());
    }

    public void testThatExpectedMoodandTeseOptionsAreChecked() {
        tellViewToAddTenseOptions(presentConditional, imperfectIndicative);
        tellViewToSelectRequiredTenseOptions(presentConditional);

        assertTrue(((OptionsCheckBox) settingsView.findViewById(0)).isChecked());
    }

    public void testThatPresenterIsToldWhenTenseIsChecked() {
        tellViewToAddTenseOptions(presentConditional, imperfectIndicative);
        settingsView.findViewById(1).performClick();
        assertThat(fakeSettingsPresenter.toldToInclude, instanceOf(ImperfectIndicative.class));
    }

    public void testThatPresenterIsToldWhenTenseIsUnchecked() {
        tellViewToAddTenseOptions(presentConditional, imperfectIndicative);
        tellViewToSelectRequiredTenseOptions(presentConditional);
        tellViewToSelectRequiredTenseOptions(imperfectIndicative);
        settingsView.findViewById(1).performClick();
        assertThat(fakeSettingsPresenter.toldToRemove, instanceOf(ImperfectIndicative.class));
    }

    private void tellViewToAddTenseOptions(final PresentConditional presentConditional, final ImperfectIndicative imperfectIndicative) {

        settingsView.showOptions(supportedTenses);
    }

    private void tellViewToSelectRequiredTenseOptions(final MoodAndTense moodAndTense) {
        settingsView.checkOptions(new ArrayList<MoodAndTense>() {{add(moodAndTense);}});
    }


    private static class FakeSettingsPresenter implements SettingsPresenter {

        public MoodAndTense toldToInclude;
        public MoodAndTense toldToRemove;

        @Override
        public void addToSelectedTenses(MoodAndTense moodAndTense) {
            toldToInclude = moodAndTense;
        }

        @Override
        public void removeFromSelectedTenses(MoodAndTense moodAndTense) {
            toldToRemove = moodAndTense;
        }

        @Override
        public void updateView() {

        }
    }
}