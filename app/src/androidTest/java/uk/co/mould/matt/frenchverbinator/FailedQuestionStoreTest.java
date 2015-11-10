package uk.co.mould.matt.frenchverbinator;

import android.content.SharedPreferences;
import android.test.AndroidTestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.co.mould.matt.FailedQuestionStore;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.frenchverbinator.settings.MoodAndTenseFactory;
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
            Question question = null;
            Set<String> failedQuestionsAsStringset = sharedPreferences.getStringSet(FAILED_QUESTIONS, new HashSet<String>());
            try {
                question = JSONSerialiser.deserializeQuestion(failedQuestionsAsStringset.iterator().next());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return question;
        }

        @Override
        public boolean hasFailedQuestions() {
            return !sharedPreferences.getStringSet(FAILED_QUESTIONS, new HashSet<String>()).isEmpty();
        }

        @Override
        public void store(Question question) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            try {
                editor.putStringSet(FAILED_QUESTIONS, convertToStringSet(question));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            editor.apply();
        }

        private static Set<String> convertToStringSet(Question question) throws JSONException {
            Set<String> failedQuestionsAsStringSet = new HashSet<>();
            String serialisedQuestion = JSONSerialiser.serialiseQuestion(question);
            failedQuestionsAsStringSet.add(serialisedQuestion);
            return failedQuestionsAsStringSet;
        }

        private static class JSONSerialiser {
            public static String serialiseQuestion(Question question) throws JSONException {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("moodAndTense", serialiseMoodAndTense(question.moodAndTense));
                jsonObject.put("person", question.person.getPerson());
                jsonObject.put("verb", serialiseVerb(question.verb));
                return jsonObject.toString();
            }

            private static String serialiseVerb(InfinitiveVerb verb) throws JSONException {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("englishVerb", verb.englishVerb);
                jsonObject.put("frenchVerb", verb.frenchVerb.toString());
                jsonObject.put("auxiliary", verb.auxiliary.toString());
                return jsonObject.toString();
            }

            private static String serialiseMoodAndTense(MoodAndTense moodAndTense) throws JSONException {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("mood", moodAndTense.getMood());
                jsonObject.put("tense", moodAndTense.getTense());
                return jsonObject.toString();
            }

            public static Question deserializeQuestion(String serealisedQuestion) throws JSONException {
                JSONObject jsonObject = new JSONObject(serealisedQuestion);
                InfinitiveVerb infinitiveVerb = new InfinitiveVerb(jsonObject.getString("frenchVerb"), jsonObject.getString("englishVerb"), jsonObject.getString("auxiliary"));
                MoodAndTense moodAndTense = new MoodAndTenseFactory().createFromString(jsonObject.getString("moodAndTense"));
                return new Question(null, infinitiveVerb, moodAndTense);
            }
        }
    }
}
