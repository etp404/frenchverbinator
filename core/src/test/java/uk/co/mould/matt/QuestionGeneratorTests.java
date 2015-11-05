package uk.co.mould.matt;

import org.junit.Test;

import java.util.ArrayList;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.questions.Callback;
import uk.co.mould.matt.questions.Question;
import uk.co.mould.matt.questions.QuestionGenerator;

import static org.junit.Assert.assertTrue;

public class QuestionGeneratorTests {

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

    private static class CapturingCallback implements Callback {
        private boolean noTensesSelected = false;

        @Override
        public void questionProvided(Question question) {

        }

        @Override
        public void noTensesSelected() {
            noTensesSelected = true;
        }
    }
}
