package uk.co.mould.matt;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.data.tenses.PresentSubjunctive;
import uk.co.mould.matt.helpers.FakeFailedQuestionStore;
import uk.co.mould.matt.helpers.FakeRandomNumberGenerator;
import uk.co.mould.matt.questions.Callback;
import uk.co.mould.matt.questions.Question;
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
                new ArrayList< MoodAndTense>(),
                new FakeFailedQuestionStore(),
                new FakeShouldUseFailedQuestion(false));

        CapturingCallback callback = new CapturingCallback();
        randomQuestionGenerator.getQuestion(callback);

        assertTrue(callback.noTensesSelected);
    }

    @Test
    public void generatesNewQuestionFromAvailableCombinationsIfShouldUseOldQuestionReturnsFalse() {
        RandomQuestionGenerator randomQuestionGenerator = new RandomQuestionGenerator(new FakeRandomNumberGenerator(0),
                Collections.singletonList(verb),
                Collections.singletonList(person),
                Collections.singletonList(verbMoodAndTense),
                new FakeFailedQuestionStore(),
                new FakeShouldUseFailedQuestion(false)
                );

        CapturingCallback callback = new CapturingCallback();
        randomQuestionGenerator.getQuestion(callback);

        Question expectedQuestion = new Question(person, verb, verbMoodAndTense);

        assertEquals(expectedQuestion, callback.question);
    }

    @Test
    public void repeatsFailedQuestionFromFailedQuestionStoreIfOldNewSelectorSelectsOldAndThereIsAnOldQuestion() {
        Question failedQuestion = new Question(Persons.SECOND_PERSON_SINGULAR, new InfinitiveVerb("bblah", "blaaahdy", "blah"), verbMoodAndTense);

        FailedQuestionStore failedQuestionStore = new FakeFailedQuestionStore();
        failedQuestionStore.store(failedQuestion);
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

    @Test
    public void generatesNewQuestionIfOldNewSelectorSelectsOldButThereAreNoOldQuestionsWithRightTense() {
        Question expectedQuestion = new Question(person, verb, verbMoodAndTense);

        FailedQuestionStore failedQuestionStore = new FakeFailedQuestionStore();
        failedQuestionStore.store(new Question(Persons.SECOND_PERSON_PLURAL, new InfinitiveVerb(null,null,null), new PresentSubjunctive()));
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
