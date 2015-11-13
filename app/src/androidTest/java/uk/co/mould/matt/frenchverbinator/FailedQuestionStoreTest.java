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

public class FailedQuestionStoreTest extends AndroidTestCase {

    private AndroidFailedQuestionStore androidFailedQuestionStore;
    private final InfinitiveVerb verb = new InfinitiveVerb("regarder", "to watch", "avoir");
    private static MoodAndTense verbMoodAndTense = new PresentIndicative();
    List<Question> questionList = new ArrayList<Question>() {{
        for (Persons.Person person : SupportedPersons.ALL) {
            add(new Question(person, verb, verbMoodAndTense));
        }
    }};
    private Question actualQuestion;

    @Before
    public void setUp() {
        androidFailedQuestionStore = new AndroidFailedQuestionStore(getContext());
        androidFailedQuestionStore.clear();
    }

    @Test
    public void testThatCanStoreAndRetrieveFailedQuestions() {
        androidFailedQuestionStore.store(questionList.get(0));
        assertTrue(androidFailedQuestionStore.hasFailedQuestions());
        Question actualQuestion = androidFailedQuestionStore.pop();
        assertThat(actualQuestion, is(questionList.get(0)));
        assertFalse(androidFailedQuestionStore.hasFailedQuestions());
    }

    @Test
    public void testThatCheckThatThereAreQuestionsOfAGivenTypeReturnsFalseAsExpected() {
        for (Question question : questionList) {
            androidFailedQuestionStore.store(question);
        }
        AndroidFailedQuestionStore.FilterForTheseTenses filterForTheseTenses =
                new AndroidFailedQuestionStore.FilterForTheseTenses(
                        new ArrayList<MoodAndTense>() {{
                            add(new PresentSubjunctive());
                        }});
        assertFalse(androidFailedQuestionStore.hasFailedQuestions(filterForTheseTenses));
    }

    @Test
    public void testThatCheckThatThereAreQuestionsOfAGivenTypeReturnsTrueAsExpected() {
        for (Question question : questionList) {
            androidFailedQuestionStore.store(question);
        }
        AndroidFailedQuestionStore.FilterForTheseTenses filterForTheseTenses =
                new AndroidFailedQuestionStore.FilterForTheseTenses(
                        new ArrayList<MoodAndTense>() {{
                            add(new PresentIndicative());
                        }});
        assertTrue(androidFailedQuestionStore.hasFailedQuestions(filterForTheseTenses));
    }

    @Test
    public void testThatCanStoreAndRetrieveFailedInOrderQuestions() {
        for (Question question : questionList) {
            androidFailedQuestionStore.store(question);
        }
        for (Question question : questionList) {
            assertThat(question, is(androidFailedQuestionStore.pop()));
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

        final FailedQuestionStore.Filter questionFilter = new AndroidFailedQuestionStore.FilterForTheseTenses(moodAndTenses);
        actualQuestion = androidFailedQuestionStore.pop(questionFilter);
        assertThat(expectedQuestion, is(actualQuestion));
    }


}
