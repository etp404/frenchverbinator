package uk.co.mould.matt;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.helpers.FakeConjugator;
import uk.co.mould.matt.helpers.FakeFailedQuestionStore;
import uk.co.mould.matt.marking.AnswerChecker;
import uk.co.mould.matt.marking.AnswerChecking;
import uk.co.mould.matt.questions.Question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnswerCheckerTest {
    private final Persons.Person person = Persons.FIRST_PERSON_PLURAL;
    private final InfinitiveVerb verb = new InfinitiveVerb("regarder", "to watch", null);
    private MoodAndTense verbMoodAndTense = new PresentIndicative();
    private ConjugatedVerbWithPronoun correctAnswer = new ConjugatedVerbWithPronoun("correct answer");
    private FakeConjugator fakeConjugator;
    private AnswerChecking answerChecking;
    private FakeFailedQuestionStore fakeFailedQuestionStore;
    private Question question = new Question(person, verb, verbMoodAndTense);
    private CapturingCallback capturingCallback;


    @Before
    public void setUp() throws Exception {
        fakeConjugator = new FakeConjugator(person, verb, verbMoodAndTense, correctAnswer);
        fakeFailedQuestionStore = new FakeFailedQuestionStore();
        answerChecking = new AnswerChecker(fakeConjugator, fakeFailedQuestionStore);
        capturingCallback = new CapturingCallback();
    }

    @Test
    public void testThatAnswerCheckerReportsCorrectAnswerForCorrectAnswer() throws FileNotFoundException {
        answerChecking.check(question, correctAnswer.toString(), capturingCallback);
        assertTrue(capturingCallback.correctCalled);
    }

    @Test
    public void testThatAnswerCheckerReportsWrongAnswerForWrongAnswer() throws FileNotFoundException {
        answerChecking.check(question, "wrong answer", capturingCallback);
        assertEquals(capturingCallback.incorrectCalledWithCorrection, correctAnswer);
    }

    @Test
    public void testThatAnswerCheckerRecordsAWrongAnswer() throws FileNotFoundException {
        answerChecking.check(question, "wrong answer", capturingCallback);
        assertEquals(fakeFailedQuestionStore.question, question);
    }

    @Test
    public void testThatAnswerCheckerReportsCorrectAnswerForCorrectAnswerWithTrailingSpace() throws FileNotFoundException {
        answerChecking.check(question, correctAnswer.toString() + " ", capturingCallback);
        assertTrue(capturingCallback.correctCalled);
    }

    private static class CapturingCallback implements AnswerChecking.Callback {
        public boolean correctCalled = false;
        public ConjugatedVerbWithPronoun incorrectCalledWithCorrection;

        @Override
        public void correct() {
            correctCalled = true;
        }

        @Override
        public void incorrect(ConjugatedVerbWithPronoun corrrection) {
            incorrectCalledWithCorrection = corrrection;
        }
    }
}
