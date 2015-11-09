package uk.co.mould.matt;

import org.junit.Test;
import org.xml.sax.InputSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.marking.AnswerChecker;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbTemplateParser;
import uk.co.mould.matt.questions.Question;

import static org.junit.Assert.assertTrue;

public class AnswerCheckerTest {
    private final Persons.Person person = Persons.FIRST_PERSON_PLURAL;
    private final InfinitiveVerb verb = new InfinitiveVerb("regarder", "to watch", null);
    private MoodAndTense verbMoodAndTense = new PresentIndicative();

    @Test
    public void testThatAnswerCheckerReportsCorrectAnswerForCorrectAnswer() throws FileNotFoundException {
        String correctAnswer = "correct answer";
        Conjugator conjugator = new FakeConjugator(person, verb, verbMoodAndTense, new ConjugatedVerbWithPronoun(correctAnswer));
        AnswerChecker answerChecker = new AnswerChecker(conjugator);
        Question question = new Question(person, verb, verbMoodAndTense);
        answerChecker.setQuestion(question);
        CapturingCallback capturingCallback = new CapturingCallback();
        answerChecker.check(correctAnswer, capturingCallback);
        assertTrue(capturingCallback.correctCalled);
    }

    @Test
    public void testThatAnswerCheckerReportsWrongAnswerForWrongAnswer() throws FileNotFoundException {
        String correctAnswer = "incorrect answer";
        Conjugator conjugator = new FakeConjugator(person, verb, verbMoodAndTense, new ConjugatedVerbWithPronoun(correctAnswer));
        AnswerChecker answerChecker = new AnswerChecker(conjugator);
        Question question = new Question(person, verb, verbMoodAndTense);
        answerChecker.setQuestion(question);
        CapturingCallback capturingCallback = new CapturingCallback();
        answerChecker.check(correctAnswer, capturingCallback);
        assertTrue(capturingCallback.correctCalled);
    }

    private static class CapturingCallback implements AnswerChecker.Callback {
        public boolean correctCalled;

        @Override
        public void correct() {
            correctCalled = true;
        }

        @Override
        public void incorrect(ConjugatedVerbWithPronoun corrrection) {

        }
    }
}
