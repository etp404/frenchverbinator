package uk.co.mould.matt.frenchverbinator;

import android.content.SharedPreferences;
import android.test.AndroidTestCase;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.co.mould.matt.FailedQuestionStore;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.frenchverbinator.settings.SharedPrefsUserSettings;
import uk.co.mould.matt.questions.Question;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FailedQuestionStoreTest extends AndroidTestCase {

    private final Persons.Person person = Persons.FIRST_PERSON_PLURAL;
    private final InfinitiveVerb verb = new InfinitiveVerb("regarder", "to watch", null);
    private MoodAndTense verbMoodAndTense = new PresentIndicative();
    private Question question = new Question(person, verb, verbMoodAndTense);


    @Test
    public void xtestThatCanStoreAndRetrieveFailedQuestions() {
        FailedQuestionStore androidFailedQuestionStore = new AndroidFailedQuestionStore(getContext().getSharedPreferences(AndroidFailedQuestionStore.FAILED_QUESTIONS, 0));
        androidFailedQuestionStore.store(question);
        assertTrue(androidFailedQuestionStore.hasFailedQuestions());
        assertThat(androidFailedQuestionStore.pop(), is(question));
        assertFalse(androidFailedQuestionStore.hasFailedQuestions());
    }

    private static class AndroidFailedQuestionStore implements FailedQuestionStore {
        public static final String FAILED_QUESTIONS = "failed_questions";

        private SharedPreferences sharedPreferences;

        public AndroidFailedQuestionStore(SharedPreferences sharedPreferences) {
            this.sharedPreferences = sharedPreferences;
        }

        @Override
        public Question pop() {
            Set<String> failedQuestionsAsStringset = sharedPreferences.getStringSet(FAILED_QUESTIONS, new HashSet<String>());
            return null;
        }

        @Override
        public boolean hasFailedQuestions() {
            return false;
        }

        @Override
        public void store(Question question) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(FAILED_QUESTIONS, convertToStringSet(question));
            editor.apply();
        }

        private static Set<String> convertToStringSet(Question question) {
            Set<String> failedQuestionsAsStringSet = new HashSet<>();
            failedQuestionsAsStringSet.add(question.toString());
            return failedQuestionsAsStringSet;
        }
    }
}
