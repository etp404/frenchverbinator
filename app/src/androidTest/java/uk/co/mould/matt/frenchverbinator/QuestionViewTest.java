package uk.co.mould.matt.frenchverbinator;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
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

public final class QuestionViewTest extends AndroidTestCase {
    public AndroidQuestionView questionView;
    private String someVerbWithPronoun = "someVerbWithPronoun";
    private TextView scoreBox;
    private View submitButton;
    private View nextButton;
    private TextView resultBox;
    private TextView correctionBox;
    private View noTenseSelectedWarning;
    private Question question = new Question(Persons.SECOND_PERSON_SINGULAR, new InfinitiveVerb("some_verb_in_french", "some_verb_in_english", null), new PresentIndicative());
    private View greenTick;
    private View redCross;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Context context = getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        questionView = (AndroidQuestionView)layoutInflater.inflate(R.layout.question_layout, null);
        scoreBox = (TextView) questionView.findViewById(R.id.score);
        submitButton = questionView.findViewById(R.id.submit_button);
        nextButton = questionView.findViewById(R.id.next_button);
        resultBox = (TextView)questionView.findViewById(R.id.result_box);
        correctionBox = (TextView)questionView.findViewById(R.id.correction_box);
        noTenseSelectedWarning = questionView.findViewById(R.id.no_tenses_selected);
        greenTick = questionView.findViewById(R.id.green_tick);
        redCross = questionView.findViewById(R.id.red_cross);

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
        assertTrue(questionView.answerBox.isEnabled());
        assertEquals(expectedQuestion, questionView.question.get());
        assertEquals(resultBox.getVisibility(), View.GONE);

    }

    public void testThatCorrectAnswerIsShownCorrectly() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                questionView.setResultToCorrect();

                assertEquals("Correct", questionView.resultBox.getText());
                assertEquals(View.VISIBLE, resultBox.getVisibility());
                assertFalse(questionView.answerBox.isEnabled());
                assertEquals(correctionBox.getVisibility(), View.GONE);
                assertEquals(greenTick.getVisibility(), View.VISIBLE);

                assertEquals(submitButton.getVisibility(), View.GONE);

                assertEquals(nextButton.getVisibility(), View.VISIBLE);
                assertTrue(nextButton.isEnabled());
            }
        });

    }

    public void testThatIncorrectAnswerDisplayedCorrectly() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                questionView.setResultToIncorrect(new ConjugatedVerbWithPronoun(someVerbWithPronoun));

                assertEquals("Incorrect", questionView.resultBox.getText());
                assertEquals(resultBox.getVisibility(), View.VISIBLE);

                assertFalse(questionView.answerBox.isEnabled());
                assertEquals(someVerbWithPronoun, correctionBox.getText());
                assertEquals(View.VISIBLE, correctionBox.getVisibility());
                assertEquals(greenTick.getVisibility(), View.GONE);
                assertEquals(redCross.getVisibility(), View.VISIBLE);

                assertEquals(submitButton.getVisibility(), View.GONE);

                assertEquals(nextButton.getVisibility(), View.VISIBLE);
                assertTrue(nextButton.isEnabled());
            }});

    }

    public void testThatWhenIncorrectAnswerHasBeenShown_AndNextQuestionIsRequested_QuestionModeIsReentered() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {

                questionView.setResultToIncorrect(new ConjugatedVerbWithPronoun(someVerbWithPronoun));
                questionView.answerBox.setText("some answer");
                questionView.setQuestion(question);

                assertEquals(correctionBox.getVisibility(), View.GONE);
                assertEquals(submitButton.getVisibility(), View.VISIBLE);
                assertEquals(nextButton.getVisibility(), View.GONE);
                assertEquals(redCross.getVisibility(), View.GONE);
                assertTrue(nextButton.isEnabled());
                assertTrue(questionView.answerBox.isEnabled());
                assertEquals(0, questionView.answerBox.getText().length());
            }});
    }

    public void testThatWhenCorrectAnswerHasBeenShown_AndNextQuestionIsRequested_QuestionModeIsReentered() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                questionView.setResultToCorrect();
                questionView.setQuestion(question);

                assertEquals(correctionBox.getVisibility(), View.GONE);
                assertEquals(submitButton.getVisibility(), View.VISIBLE);
                assertEquals(nextButton.getVisibility(), View.GONE);
                assertEquals(greenTick.getVisibility(), View.GONE);
                assertTrue(nextButton.isEnabled());
                assertTrue(questionView.answerBox.isEnabled());
                assertTrue(questionView.answerBox.isVisible());
                assertEquals(0, questionView.answerBox.getText().length());
            }
        });
    }

    public void testThatNoTensesSelectedViewCanBeShown() {
        questionView.showNoTensesSelected();

        assertFalse(questionView.answerBox.isVisible());
        assertEquals(questionView.findViewById(R.id.question).getVisibility(), View.GONE);
        assertEquals(submitButton.getVisibility(), View.GONE);
        assertEquals(nextButton.getVisibility(), View.GONE);
        assertEquals(correctionBox.getVisibility(), View.GONE);
        assertEquals(resultBox.getVisibility(), View.GONE);
        assertEquals(scoreBox.getVisibility(), View.GONE);
        assertFalse(questionView.answerBox.isVisible());
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
        questionView.answerBox.setText(answer);
        questionView.onSubmitAnswer();
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
