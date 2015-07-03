package uk.co.mould.matt.frenchverbinator;

import android.test.ActivityInstrumentationTestCase2;
import android.view.ViewGroup;

import org.junit.Before;

import java.util.ArrayList;

import matt.mould.co.uk.testharnessapplication.R;
import matt.mould.co.uk.testharnessapplication.TestActivity;
import uk.co.mould.matt.data.tenses.ImperfectIndicative;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentConditional;
import uk.co.mould.matt.frenchverbinator.settings.ui.AndroidSettingsView;
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

public class SettingsViewTest extends ActivityInstrumentationTestCase2<TestActivity> {
    private TestActivity activity;
    private ViewGroup activityTestViewGroup;
    private SettingsView settingsView;
    private FakeSettingsPresenter fakeSettingsPresenter;
    private final PresentConditional presentConditional = new PresentConditional();
    private final ImperfectIndicative imperfectIndicative = new ImperfectIndicative();

    public SettingsViewTest() {
        super(TestActivity.class);
    }

    @Before
    public void setUp() {
        fakeSettingsPresenter = new FakeSettingsPresenter();
        final TestActivity activity = getActivity();
        Runnable runnable = new Runnable() {
            public void run() {
                activityTestViewGroup = (ViewGroup) activity.findViewById(R.id.settings_activity_parent);
                settingsView = AndroidSettingsView.createView(activityTestViewGroup);
                settingsView.setPresenter(fakeSettingsPresenter);
            }
        };
        try {
            runTestOnUiThread(runnable);
        } catch (Throwable throwable) {}
    }

    public void testThatMoodAndTenseOptionsAppear() {
        tellViewToAddTenseOptions(presentConditional, imperfectIndicative);

        onView(withText(imperfectIndicative.toString())).check(
                matches(isDisplayed()));
        onView(withText(presentConditional.toString())).check(matches(isDisplayed()));
    }

    public void testThatExpectedMoodandTeseOptionsAreChecked() {
        tellViewToAddTenseOptions(presentConditional, imperfectIndicative);
        tellViewToSelectRequiredTenseOptions(presentConditional);
        onView(withText(presentConditional.toString())).check(matches(isChecked()));
    }

    public void testThatPresenterIsToldWhenTenseIsChecked() {
        tellViewToAddTenseOptions(presentConditional, imperfectIndicative);
        onView(withText(imperfectIndicative.toString())).check(matches(isNotChecked()));
        onView(withText(imperfectIndicative.toString())).perform(click());
        assertThat(fakeSettingsPresenter.toldToInclude, instanceOf(ImperfectIndicative.class));
    }

    public void testThatPresenterIsToldWhenTenseIsUnchecked() {

        tellViewToAddTenseOptions(presentConditional, imperfectIndicative);
        tellViewToSelectRequiredTenseOptions(presentConditional);
        tellViewToSelectRequiredTenseOptions(imperfectIndicative);
        onView(withText(imperfectIndicative.toString())).perform(click());
        assertThat(fakeSettingsPresenter.toldToRemove, instanceOf(ImperfectIndicative.class));
    }

    private void tellViewToAddTenseOptions(final PresentConditional presentConditional, final ImperfectIndicative imperfectIndicative) {
        Runnable runnable = new Runnable() {
            public void run() {
                settingsView.showOptions(new ArrayList<MoodAndTense>() {{
                    add(presentConditional);
                    add(imperfectIndicative);
                }});
            }
        };
        try {
            runTestOnUiThread(runnable);
        } catch (Throwable throwable) {}
    }

    private void tellViewToSelectRequiredTenseOptions(final MoodAndTense moodAndTense) {
        Runnable runnable = new Runnable() {
            public void run() {
                settingsView.checkOptions(new ArrayList<MoodAndTense>() {{
                    add(moodAndTense);
                }});
            }
        };
        try {
            runTestOnUiThread(runnable);
        } catch (Throwable throwable) {}
    }


    private class FakeSettingsPresenter implements SettingsPresenter {

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