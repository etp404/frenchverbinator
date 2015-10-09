package uk.co.mould.matt.frenchverbinator;

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.marking.Score;
import uk.co.mould.matt.questions.Question;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class QuestionViewTest extends AndroidTestCase {
    public AndroidQuestionView questionView;
    private String someVerbWithPronoun = "someVerbWithPronoun";
    private TextView scoreBox;
    private View submitButton;
    private View nextButton;
    private TextView answerBox;
    private TextView questionBox;
    private TextView resultBox;
    private View correctionBox;
    private View noTenseSelectedWarning;
    private Question question = new Question(Persons.SECOND_PERSON_SINGULAR, new InfinitiveVerb("some_verb_in_french", "some_verb_in_english", null), new PresentIndicative());

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Context context = getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        questionView = (AndroidQuestionView)layoutInflater.inflate(R.layout.question_layout, null);
        scoreBox = (TextView) questionView.findViewById(R.id.score);
        submitButton = questionView.findViewById(R.id.submit_button);
        nextButton = questionView.findViewById(R.id.next_button);
        answerBox = (TextView)questionView.findViewById(R.id.answer_box);
        questionBox = (TextView) questionView.findViewById(R.id.question);
        resultBox = (TextView)questionView.findViewById(R.id.result_box);
        correctionBox = questionView.findViewById(R.id.correction_box);
        noTenseSelectedWarning = questionView.findViewById(R.id.no_tenses_selected);
        questionView.setQuestion(question);
    }

    public void testThatScoreIsShownAsExpected() {
        Score score = new Score();
        questionView.showScore(score);
        assertEquals(score.toString(), scoreBox.getText());
    }

    public void testThatQuestionDisplayedCorrectly() {
        String someVerbInFrench = "someVerbInFrench";
        String someVerbInEnglish = "someVerbInEnglish";
        Persons.Person person = Persons.SECOND_PERSON_SINGULAR;
        MoodAndTense moodAndTense = new PresentIndicative();
        questionView.setQuestion(new Question(person, new InfinitiveVerb(someVerbInFrench, someVerbInEnglish, null), moodAndTense));

        String expectedQuestion = String.format("What is the '%s' form of %s (%s) in the %s?", person.getPerson(), someVerbInFrench, someVerbInEnglish, moodAndTense.toString());

        assertTrue(submitButton.isEnabled());
        assertEquals(submitButton.getVisibility(), View.VISIBLE);

        assertTrue(nextButton.isEnabled());
        assertTrue(answerBox.isEnabled());
        assertEquals(expectedQuestion, questionBox.getText());
        assertEquals(resultBox.getVisibility(), View.GONE);

    }

    public void testThatCorrectAnswerIsShownCorrectly() {
        questionView.setResultToCorrect();

        assertEquals("Correct", resultBox.getText());
        assertEquals(View.VISIBLE, resultBox.getVisibility());
        assertFalse(answerBox.isEnabled());
        assertEquals(correctionBox.getVisibility(), View.GONE);

        assertEquals(submitButton.getVisibility(), View.GONE);

        assertEquals(nextButton.getVisibility(), View.VISIBLE);
        assertTrue(nextButton.isEnabled());
    }

    public void testThatIncorrectAnswerDisplayedCorrectly() {
        questionView.setResultToIncorrect(new ConjugatedVerbWithPronoun(someVerbWithPronoun));

        assertEquals("Incorrect", resultBox.getText());
        assertEquals(resultBox.getVisibility(), View.VISIBLE);

        assertFalse(questionView.findViewById(R.id.answer_box).isEnabled());
        assertEquals(someVerbWithPronoun, ((TextView) questionView.findViewById(R.id.correction_box)).getText());

        assertEquals(submitButton.getVisibility(), View.GONE);

        assertEquals(nextButton.getVisibility(), View.VISIBLE);
        assertTrue(nextButton.isEnabled());

    }

    public void testThatWhenIncorrectAnswerHasBeenShown_AndNextQuestionIsRequested_QuestionModeIsReentered() {
        questionView.setResultToIncorrect(new ConjugatedVerbWithPronoun(someVerbWithPronoun));
        answerBox.setText("some answer");
        questionView.setQuestion(question);

        assertEquals(correctionBox.getVisibility(), View.GONE);
        assertEquals(submitButton.getVisibility(), View.VISIBLE);
        assertEquals(nextButton.getVisibility(), View.GONE);
        assertTrue(nextButton.isEnabled());
        assertTrue(answerBox.isEnabled());
        assertEquals(0, answerBox.getText().length());
    }

    public void testThatNoTensesSelectedViewCanBeShown() {
        questionView.showNoTensesSelected();

        assertEquals(questionView.findViewById(R.id.answer_box).getVisibility(), View.GONE);
        assertEquals(questionView.findViewById(R.id.question).getVisibility(), View.GONE);
        assertEquals(submitButton.getVisibility(), View.GONE);
        assertEquals(nextButton.getVisibility(), View.GONE);
        assertEquals(correctionBox.getVisibility(), View.GONE);
        assertEquals(resultBox.getVisibility(), View.GONE);
        assertEquals(noTenseSelectedWarning.getVisibility(), View.VISIBLE);
    }

    public void testThatNoTensesSelectedWarningIsRemoved_WhenQuestionIsShown() {
        questionView.showNoTensesSelected();
        questionView.setQuestion(question);
        assertEquals(noTenseSelectedWarning.getVisibility(), View.GONE);
    }

    public void testThatPressingSubmitInvokesListener() {
        FakeSubmitListener submitListener = new FakeSubmitListener();
        questionView.addSubmitListener(submitListener);
        String answer = "some_answer";
        answerBox.setText(answer);
        submitButton.performClick();
        assertEquals(submitListener.invokedWith, answer);
    }

    public void testThatPressingNextInvokesListener() {
        FakeNextQuestionListener fakeNextQuestionListener = new FakeNextQuestionListener();
        questionView.addNextQuestionListener(fakeNextQuestionListener);
        nextButton.performClick();
        assertTrue(fakeNextQuestionListener.invoked);
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
