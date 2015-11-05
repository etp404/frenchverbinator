package uk.co.mould.matt;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.questions.Callback;
import uk.co.mould.matt.questions.Question;
import uk.co.mould.matt.questions.QuestionGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuestionGeneratorTests {
    private final Persons.Person person = Persons.FIRST_PERSON_PLURAL;
    private final InfinitiveVerb verb = new InfinitiveVerb("regarder", "to watch", null);
    private MoodAndTense verbMoodAndTense = new PresentIndicative();

    @Test
    public void returnsNoTensesSelectedIfNoTensesAreSelected() {
        QuestionGenerator questionGenerator = new QuestionGenerator(
                new FakeRandomQuestionGenerator(),
                new ArrayList< InfinitiveVerb >(),
                new ArrayList< Persons.Person>(),
                new ArrayList< MoodAndTense>());

        CapturingCallback callback = new CapturingCallback();
        questionGenerator.getQuestion(callback);

        assertTrue(callback.noTensesSelected);
    }

    @Test
    public void returnsQuestionFromList() {
        QuestionGenerator questionGenerator = new QuestionGenerator(new FakeRandomQuestionGenerator(),
                Collections.singletonList(verb),
                Collections.singletonList(person),
                Collections.singletonList(verbMoodAndTense));

        CapturingCallback callback = new CapturingCallback();
        questionGenerator.getQuestion(callback);

        Question expectedQuestion = new Question(person, verb, verbMoodAndTense);

        assertEquals(expectedQuestion, callback.question);
    }

    private static class CapturingCallback implements Callback {
        private boolean noTensesSelected = false;
        private Question question;

        @Override
        public void questionProvided(Question question) {
            this.question = question;
        }

        @Override
        public void noTensesSelected() {
            noTensesSelected = true;
        }
    }
}
