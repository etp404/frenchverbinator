package uk.co.mould.matt.frenchverbinator;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SettingsTest extends ActivityInstrumentationTestCase2<QuestionActivity> {
    public SettingsTest() {
        super(QuestionActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testThatOptionsAppear() {
        onView(withId(R.id.answerBox)).perform(typeText("Steve"));
        onView(withId(R.id.answerBox)).check(matches(withText("Steve")));
    }
}
