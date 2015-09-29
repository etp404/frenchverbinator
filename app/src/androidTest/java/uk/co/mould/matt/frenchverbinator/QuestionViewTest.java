package uk.co.mould.matt.frenchverbinator;

import android.test.ActivityInstrumentationTestCase2;
import android.view.ViewGroup;

import org.junit.Before;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.marking.Score;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.IsNot.not;

public final class QuestionViewTest extends ActivityInstrumentationTestCase2<TestActivity> {
    public AndroidQuestionView questionView;

    public QuestionViewTest() {
        super(TestActivity.class);
    }

    @Before
    public void setUp() {
        final TestActivity activity = getActivity();
        Runnable runnable = new Runnable() {

            public void run() {
                activity.setContentView(R.layout.question_layout);
                ViewGroup questionViewGroup = (ViewGroup) activity.findViewById(
                        R.id.question_view_group);
                questionView = new AndroidQuestionView(questionViewGroup);
            }
        };
        try {
            runTestOnUiThread(runnable);
        } catch (Throwable throwable) {}
    }

    public void testThatScoreIsShownAsExpected() {
        Score score = new Score();
        questionView.showScore(score);
        onView(withText(score.toString())).check(
                matches(isDisplayed()));
    }

    public void testThatQuestionDisplayedCorrectly() {
        questionView.setQuestion(Persons.SECOND_PERSON_SINGULAR, new InfinitiveVerb("some_verb_in_french", "some_verb_in_english", null), new PresentIndicative());

        onView(
                allOf(
                        withId(R.id.question),
                        withText("What is the 'tu' form of some_verb_in_french (some_verb_in_english) in the present indicative?")
                )
        ).check(matches(isDisplayed()));

        onView(withId(R.id.submit_button)).check(matches(allOf(isDisplayed(), isEnabled())));
        onView(withId(R.id.next_button)).check(matches(not(allOf(isDisplayed(), isEnabled()))));
    }

}
