package uk.co.mould.matt.frenchverbinator;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.marking.Score;
import uk.co.mould.matt.questions.Question;

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

public final class QuestionViewTest extends AndroidTestCase {
    public AndroidQuestionView questionView;
    private String someVerbWithPronoun = "someVerbWithPronoun";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Context context = getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        questionView = (AndroidQuestionView)layoutInflater.inflate(R.layout.question_layout, null);
        setArbitraryQuestion();
    }

    public void testThatScoreIsShownAsExpected() {
        Score score = new Score();
        questionView.showScore(score);
        assertEquals(score.toString(), ((TextView) questionView.findViewById(R.id.score)).getText());
    }

    public void testThatQuestionDisplayedCorrectly() {
        String someVerbInFrench = "someVerbInFrench";
        String someVerbInEnglish = "someVerbInEnglish";
        Persons.Person person = Persons.SECOND_PERSON_SINGULAR;
        MoodAndTense moodAndTense = new PresentIndicative();
        questionView.setQuestion(new Question(person, new InfinitiveVerb(someVerbInFrench, someVerbInEnglish, null), moodAndTense));

        String expectedQuestion = String.format("What is the '%s' form of %s (%s) in the %s?", person.getPerson(), someVerbInFrench, someVerbInEnglish, moodAndTense.toString());

        assertTrue(questionView.findViewById(R.id.submit_button).isEnabled());
        assertEquals(questionView.findViewById(R.id.submit_button).getVisibility(), View.VISIBLE);

        assertTrue(questionView.findViewById(R.id.next_button).isEnabled());
        assertTrue(questionView.findViewById(R.id.answer_box).isEnabled());
        assertEquals(expectedQuestion, ((TextView) questionView.findViewById(R.id.question)).getText());

    }

    public void testThatCorrectAnswerIsShownCorrectly() {
        questionView.setResultToCorrect();

        assertEquals("Correct", ((TextView) questionView.findViewById(R.id.result_box)).getText());
        assertEquals(questionView.findViewById(R.id.result_box).getVisibility(), View.VISIBLE);
        assertFalse(questionView.findViewById(R.id.answer_box).isEnabled());
        assertEquals(questionView.findViewById(R.id.correction_box).getVisibility(), View.GONE);

        assertEquals(questionView.findViewById(R.id.submit_button).getVisibility(), View.GONE);

        assertEquals(questionView.findViewById(R.id.next_button).getVisibility(), View.VISIBLE);
        assertTrue(questionView.findViewById(R.id.next_button).isEnabled());
    }

    public void testThatIncorrectAnswerDisplayedCorrectly() {
        questionView.setResultToIncorrect(new ConjugatedVerbWithPronoun(someVerbWithPronoun));

        assertEquals("Incorrect", ((TextView) questionView.findViewById(R.id.result_box)).getText());
        assertEquals(questionView.findViewById(R.id.result_box).getVisibility(), View.VISIBLE);

        assertFalse(questionView.findViewById(R.id.answer_box).isEnabled());
        assertEquals(someVerbWithPronoun, ((TextView) questionView.findViewById(R.id.correction_box)).getText());

        assertEquals(questionView.findViewById(R.id.submit_button).getVisibility(), View.GONE);

        assertEquals(questionView.findViewById(R.id.next_button).getVisibility(), View.VISIBLE);
        assertTrue(questionView.findViewById(R.id.next_button).isEnabled());

    }

    public void testThatWhenIncorrectAnswerHasBeenShown_AndNextQuestionIsRequested_QuestionModeIsReentered() {
        questionView.setResultToIncorrect(new ConjugatedVerbWithPronoun(someVerbWithPronoun));
        ((TextView)questionView.findViewById(R.id.answer_box)).setText("some answer");
        setArbitraryQuestion();

        assertEquals(questionView.findViewById(R.id.correction_box).getVisibility(), View.GONE);
        assertEquals(questionView.findViewById(R.id.submit_button).getVisibility(), View.VISIBLE);
        assertEquals(questionView.findViewById(R.id.next_button).getVisibility(), View.GONE);
        assertTrue(questionView.findViewById(R.id.next_button).isEnabled());
        assertTrue(questionView.findViewById(R.id.answer_box).isEnabled());
        assertEquals(0, ((TextView) questionView.findViewById(R.id.answer_box)).getText().length());
    }

    public void testThatNoTensesSelectedViewCanBeShown() {
        questionView.showNoTensesSelected();

        assertEquals(questionView.findViewById(R.id.answer_box).getVisibility(), View.GONE);
        assertEquals(questionView.findViewById(R.id.question).getVisibility(), View.GONE);
        assertEquals(questionView.findViewById(R.id.submit_button).getVisibility(), View.GONE);
        assertEquals(questionView.findViewById(R.id.next_button).getVisibility(), View.GONE);
        assertEquals(questionView.findViewById(R.id.correction_box).getVisibility(), View.GONE);
        assertEquals(questionView.findViewById(R.id.result_box).getVisibility(), View.GONE);
        assertEquals(questionView.findViewById(R.id.no_tenses_selected).getVisibility(), View.VISIBLE);
    }

    public void testThatNoTensesSelectedWarningIsRemoved_WhenQuestionIsShown() {
        questionView.showNoTensesSelected();

        setArbitraryQuestion();

        assertEquals(questionView.findViewById(R.id.no_tenses_selected).getVisibility(), View.GONE);

    }

    public void testThatPressingSubmitInvokesListener() {
        FakeSubmitListener submitListener = new FakeSubmitListener();
        questionView.addSubmitListener(submitListener);
        String answer = "some_answer";
        TextView answerBox = (TextView)questionView.findViewById(R.id.answer_box);
        answerBox.setText(answer);
        Button submitButton = (Button) questionView.findViewById(R.id.submit_button);
        submitButton.performClick();
        assertEquals(submitListener.invokedWith, answer);
    }

    public void testThatPressingNextInvokesListener() {
        FakeNextQuestionListener fakeNextQuestionListener = new FakeNextQuestionListener();
        questionView.addNextQuestionListener(fakeNextQuestionListener);
        Button nextButton = (Button) questionView.findViewById(R.id.next_button);
        nextButton.performClick();
        assertTrue(fakeNextQuestionListener.invoked);
    }

    private void setArbitraryQuestion() {
        questionView.setQuestion(new Question(Persons.SECOND_PERSON_SINGULAR, new InfinitiveVerb("some_verb_in_french", "some_verb_in_english", null), new PresentIndicative()));
    }

    private static class FakeSubmitListener implements QuestionView.SubmitListener {
        public String invokedWith;

        @Override
        public void submitAnswer(String answer) {
            invokedWith = answer;
        }
    }

    private static class FakeNextQuestionListener implements QuestionView.NextQuestionListener {

        public boolean invoked = false;

        @Override
        public void requestNextQuestion() {
            invoked = true;
        }
    }
}
