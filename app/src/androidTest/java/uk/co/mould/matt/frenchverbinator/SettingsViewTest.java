package uk.co.mould.matt.frenchverbinator;

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;

import org.junit.Before;

import java.util.ArrayList;

import uk.co.mould.matt.data.tenses.ImperfectIndicative;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentConditional;
import uk.co.mould.matt.frenchverbinator.settings.ui.AndroidSettingsView;
import uk.co.mould.matt.frenchverbinator.settings.ui.OptionsCheckBox;
import uk.co.mould.matt.frenchverbinator.settings.ui.SettingsPresenter;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("ResourceType")
public class SettingsViewTest extends AndroidTestCase {
    private AndroidSettingsView settingsView;
    private FakeSettingsPresenter fakeSettingsPresenter;
    private final PresentConditional presentConditional = new PresentConditional();
    private final ImperfectIndicative imperfectIndicative = new ImperfectIndicative();
    private ArrayList<MoodAndTense> supportedTenses = new ArrayList<MoodAndTense>() {{
        add(presentConditional);
        add(imperfectIndicative);
    }};


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