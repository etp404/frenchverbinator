package uk.co.mould.matt.frenchverbinator;

import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import uk.co.mould.matt.FailedQuestionStore;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.SupportedPersons;
import uk.co.mould.matt.data.tenses.ImperfectIndicative;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.data.tenses.PresentSubjunctive;
import uk.co.mould.matt.frenchverbinator.failedquestions.AndroidFailedQuestionStore;
import uk.co.mould.matt.questions.Question;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AndroidFailedQuestionStoreTest extends AndroidTestCase {

    private FailedQuestionStore androidFailedQuestionStore;
    private final InfinitiveVerb verb = new InfinitiveVerb("regarder", "to watch", "avoir");
    private static MoodAndTense verbMoodAndTense = new PresentIndicative();
    List<Question> questionList = new ArrayList<Question>() {{
        for (Persons.Person person : SupportedPersons.ALL) {
            add(new Question(person, verb, verbMoodAndTense));
        }
    }};
    private CapturingCallback capturingCallback;

    @Before
    public void setUp() {
        androidFailedQuestionStore = new AndroidFailedQuestionStore(getContext());
        androidFailedQuestionStore.clear();
        capturingCallback = new CapturingCallback();
    }

    @Test
    public void testThatCanStoreAndRetrieveFailedQuestions() {
        androidFailedQuestionStore.store(questionList.get(0));
        ArrayList<MoodAndTense> supportedTenses = new ArrayList<MoodAndTense>() {{
            add(verbMoodAndTense);
        }};
        androidFailedQuestionStore.getFailedQuestion(capturingCallback, supportedTenses);
        assertThat(capturingCallback.question, is(questionList.get(0)));
        androidFailedQuestionStore.getFailedQuestion(capturingCallback, supportedTenses);
        assertNull(capturingCallback.question);
        assertTrue(capturingCallback.failureCalled);
    }

    @Test
    public void testThatCheckThatThereAreQuestionsOfAGivenTypeCallsFailureCallbackAsExpected() {
        for (Question question : questionList) {
            androidFailedQuestionStore.store(question);
        }

        List<MoodAndTense> includedMoodsAndTenses = new ArrayList<MoodAndTense>() {{
            add(new PresentSubjunctive());
        }};

        androidFailedQuestionStore.getFailedQuestion(capturingCallback, includedMoodsAndTenses);
        assertNull(capturingCallback.question);
    }

    @Test
    public void testThatCanStoreAndRetrieveFailedInOrderQuestions() {
        List<MoodAndTense> storedMoodsAndTenses = new ArrayList<>();
        for (Question question : questionList) {
            storedMoodsAndTenses.add(question.moodAndTense);
            androidFailedQuestionStore.store(question);
        }
        for (Question question : questionList) {
            androidFailedQuestionStore.getFailedQuestion(capturingCallback, storedMoodsAndTenses);
            assertThat(capturingCallback.question, is(question));
        }
    }

    @Test
    public void testThatCanStoreAndRetrieveQuestionsWithFilter() {
        for (Question question : questionList) {
            androidFailedQuestionStore.store(question);
        }

        Question expectedQuestion =
                new Question(
                        Persons.SECOND_PERSON_PLURAL,
                        new InfinitiveVerb("regarder", "to watch", "avoir"),
                        new ImperfectIndicative()
                );

        androidFailedQuestionStore.store(expectedQuestion);

        List<MoodAndTense> moodAndTenses = new ArrayList<>();
        moodAndTenses.add(new PresentSubjunctive());
        moodAndTenses.add(expectedQuestion.moodAndTense);

        androidFailedQuestionStore.getFailedQuestion(capturingCallback, moodAndTenses);
        assertThat(expectedQuestion, is(capturingCallback.question));
    }


    private static class CapturingCallback implements AndroidFailedQuestionStore.Callback {

        private Question question;
        public boolean failureCalled;

        @Override
        public void success(Question question) {
            this.question = question;
        }

        @Override
        public void failure() {
            question = null;
            failureCalled = true;
        }
    }
}
