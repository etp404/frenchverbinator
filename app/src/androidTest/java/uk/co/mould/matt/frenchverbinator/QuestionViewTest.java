package uk.co.mould.matt.frenchverbinator;

import android.test.ActivityInstrumentationTestCase2;
import android.view.ViewGroup;

import org.junit.Before;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class QuestionViewTest extends ActivityInstrumentationTestCase2<TestActivity> {
    public AndroidQuestionView questionView;
    private String someVerbWithPronoun = "someVerbWithPronoun";

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
        } catch (Throwable throwable) {
        }
    }

    public void testThatScoreIsShownAsExpected() {
        Score score = new Score();
        questionView.showScore(score);
        onView(withText(score.toString())).check(
                matches(isDisplayed()));
    }

    public void testThatQuestionDisplayedCorrectly() {
        String someVerbInFrench = "someVerbInFrench";
        String someVerbInEnglish = "someVerbInEnglish";
        Persons.Person person = Persons.SECOND_PERSON_SINGULAR;
        MoodAndTense moodAndTense = new PresentIndicative();
        questionView.setQuestion(person, new InfinitiveVerb(someVerbInFrench, someVerbInEnglish, null), moodAndTense);

        String expectedQuestion = String.format("What is the '%s' form of %s (%s) in the %s?", person.getPerson(), someVerbInFrench, someVerbInEnglish, moodAndTense.toString());
        onView(allOf(withId(R.id.question), withText(expectedQuestion))
        ).check(matches(isDisplayed()));

        onView(withId(R.id.submit_button)).check(matches(allOf(isDisplayed(), isEnabled())));
        onView(withId(R.id.next_button)).check(matches(not(allOf(isDisplayed(), isEnabled()))));
        onView(withId(R.id.answer_box)).check(matches(isEnabled()));
    }

    public void testThatCorrectAnswerDisplayedCorrectly() {
        questionView.setResultToCorrect();

        onView(allOf(withId(R.id.result_box), withText("Correct"))).check(matches(isDisplayed()));

        onView(withId(R.id.answer_box)).check(matches(not(isEnabled())));
        onView(withId(R.id.correction_box)).check(matches(not(isDisplayed())));

        onView(withId(R.id.submit_button)).check(matches(not(allOf(isDisplayed(), isEnabled()))));
        onView(withId(R.id.next_button)).check(matches(allOf(isDisplayed(), isEnabled())));

    }

    public void testThatIncorrectAnswerDisplayedCorrectly() {
        questionView.setResultToIncorrect(new ConjugatedVerbWithPronoun(someVerbWithPronoun));

        onView(allOf(withId(R.id.result_box), withText("Incorrect"))).check(matches(isDisplayed()));

        onView(withId(R.id.answer_box)).check(matches(not(isEnabled())));
        onView(allOf(withId(R.id.correction_box), withText(someVerbWithPronoun))).check(matches(isDisplayed()));

        onView(withId(R.id.submit_button)).check(matches(not(allOf(isDisplayed(), isEnabled()))));
        onView(withId(R.id.next_button)).check(matches(allOf(isDisplayed(), isEnabled())));

    }

    public void testThatWhenIncorrectAnswerHasBeenShown_AndNextQuestionIsRequested_QuestionModeIsReentered() {
        questionView.setResultToIncorrect(new ConjugatedVerbWithPronoun(someVerbWithPronoun));
        setArbitraryQuestion();

        onView(withId(R.id.correction_box)).check(matches(not(isDisplayed())));
        onView(withId(R.id.submit_button)).check(matches(allOf(isDisplayed(), isEnabled())));
        onView(withId(R.id.next_button)).check(matches(not(allOf(isDisplayed(), isEnabled()))));
        onView(withId(R.id.answer_box)).check(matches(isEnabled()));
    }

    public void testThatNoTensesSelectedViewCanBeShown() {
        questionView.showNoTensesSelected();

        onView(withId(R.id.answer_box)).check(matches(not(isDisplayed())));
        onView(withId(R.id.question)).check(matches(not(isDisplayed())));
        onView(withId(R.id.submit_button)).check(matches(not(isDisplayed())));
        onView(withId(R.id.next_button)).check(matches(not(isDisplayed())));
        onView(withId(R.id.correction_box)).check(matches(not(isDisplayed())));
        onView(withId(R.id.result_box)).check(matches(not(isDisplayed())));

        onView(withId(R.id.no_tenses_selected)).check(matches(isDisplayed()));
    }

    public void testThatNoTensesSelectedWarningIsRemoved_WhenQuestionIsShown() {
        questionView.showNoTensesSelected();

        setArbitraryQuestion();

        onView(withId(R.id.no_tenses_selected)).check(matches(not(isDisplayed())));

    }

    private void setArbitraryQuestion() {
        questionView.setQuestion(Persons.SECOND_PERSON_SINGULAR, new InfinitiveVerb("some_verb_in_french", "some_verb_in_english", null), new PresentIndicative());
    }
}
