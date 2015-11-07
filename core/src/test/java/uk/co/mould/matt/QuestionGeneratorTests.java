package uk.co.mould.matt;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.data.tenses.PresentSubjunctive;
import uk.co.mould.matt.questions.Callback;
import uk.co.mould.matt.questions.Question;
import uk.co.mould.matt.questions.RandomNumberGenerator;
import uk.co.mould.matt.questions.RandomQuestionGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuestionGeneratorTests {
    private final Persons.Person person = Persons.FIRST_PERSON_PLURAL;
    private final InfinitiveVerb verb = new InfinitiveVerb("regarder", "to watch", null);
    private MoodAndTense verbMoodAndTense = new PresentIndicative();

    @Test
    public void returnsNoTensesSelectedIfNoTensesAreSelected() {
        RandomQuestionGenerator randomQuestionGenerator = new RandomQuestionGenerator(
                new FakeRandomNumberGenerator(0),
                new ArrayList< InfinitiveVerb >(),
                new ArrayList< Persons.Person>(),
                new ArrayList< MoodAndTense>());

        CapturingCallback callback = new CapturingCallback();
        randomQuestionGenerator.getQuestion(callback);

        assertTrue(callback.noTensesSelected);
    }

    @Test
    public void returnsQuestionFromList() {
        RandomQuestionGenerator randomQuestionGenerator = new RandomQuestionGenerator(new FakeRandomNumberGenerator(0),
                Collections.singletonList(verb),
                Collections.singletonList(person),
                Collections.singletonList(verbMoodAndTense));

        CapturingCallback callback = new CapturingCallback();
        randomQuestionGenerator.getQuestion(callback);

        Question expectedQuestion = new Question(person, verb, verbMoodAndTense);

        assertEquals(expectedQuestion, callback.question);
    }

    @Test
    public void repeatsFailedQuestionFromFailedQuestionStoreIfOldNewSelectorSelectsOldAndThereIsAnOldQuestion() {
        Question failedQuestion = new Question(Persons.SECOND_PERSON_SINGULAR, new InfinitiveVerb("bblah", "blaaahdy", "blah"), new PresentSubjunctive());

        FailedQuestionStore failedQuestionStore = new FakeFailedQuestionStore(failedQuestion);
        RandomQuestionGenerator randomQuestionGenerator = new RandomQuestionGenerator(
                new FakeRandomNumberGenerator(0),
                Collections.singletonList(verb),
                Collections.singletonList(person),
                Collections.singletonList(verbMoodAndTense),
                failedQuestionStore,
                new FakeShouldUseFailedQuestion(true)
        );

        CapturingCallback callback = new CapturingCallback();
        randomQuestionGenerator.getQuestion(callback);
        assertEquals(failedQuestion, callback.question);
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

    private class FakeFailedQuestionStore implements FailedQuestionStore {
        private Question question;

        private FakeFailedQuestionStore(Question question) {
            this.question = question;
        }

        public Question pop() {
            return question;
        }
    }

    private class FakeShouldUseFailedQuestion implements ShouldUseFailedQuestion {
        private boolean bool;

        public FakeShouldUseFailedQuestion(boolean bool) {
            this.bool = bool;
        }

        public boolean invoke() {
            return bool;
        }
    }
}
