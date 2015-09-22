package uk.co.mould.matt.frenchverbinator;

import android.test.ActivityInstrumentationTestCase2;
import android.view.ViewGroup;

import uk.co.mould.matt.marking.Score;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public final class QuestionViewTest extends ActivityInstrumentationTestCase2<TestActivity> {
    public AndroidQuestionView questionView;

    public QuestionViewTest() {
        super(TestActivity.class);
    }

    public void testThatScoreIsShownAsExpected() {
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

        Score score = new Score();
        questionView.showScore(score);
        onView(withText(score.toString())).check(
                matches(isDisplayed()));
    }

}
