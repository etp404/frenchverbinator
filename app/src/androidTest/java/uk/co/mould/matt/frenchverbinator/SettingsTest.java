package uk.co.mould.matt.frenchverbinator;

import android.support.test.espresso.assertion.ViewAssertions;
import android.test.ActivityInstrumentationTestCase2;

import uk.co.mould.matt.frenchverbinator.settings.SettingsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SettingsTest extends ActivityInstrumentationTestCase2<SettingsActivity> {
    public SettingsTest() {
        super(SettingsActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testThatOptionsAppear() {
        onView(withText("Settings")).check(ViewAssertions.matches(isDisplayed()));
    }
}
