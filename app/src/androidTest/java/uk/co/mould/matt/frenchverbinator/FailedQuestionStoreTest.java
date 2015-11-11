package uk.co.mould.matt.frenchverbinator;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.test.AndroidTestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.co.mould.matt.FailedQuestionStore;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.SupportedPersons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PerfectIndicative;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.frenchverbinator.settings.MoodAndTenseFactory;
import uk.co.mould.matt.questions.Question;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FailedQuestionStoreTest extends AndroidTestCase {

    private final InfinitiveVerb verb = new InfinitiveVerb("regarder", "to watch", "avoir");
    private static MoodAndTense verbMoodAndTense = new PresentIndicative();
    List<Question> questionList = new ArrayList<Question>() {{
        for (Persons.Person person : SupportedPersons.ALL) {
            add(new Question(person, verb, verbMoodAndTense));
        }
    }};
    private SharedPreferences sharedPreferences;

    @Before
    public void setUp() {
        sharedPreferences = getContext().getSharedPreferences(AndroidFailedQuestionStore.FAILED_QUESTIONS, 0);
        sharedPreferences.edit().clear().apply();
    }
    @Test
    public void testThatCanStoreAndRetrieveFailedQuestions() {
        FailedQuestionStore androidFailedQuestionStore = new AndroidFailedQuestionStore(sharedPreferences);
        androidFailedQuestionStore.store(questionList.get(0));
        assertTrue(androidFailedQuestionStore.hasFailedQuestions());
        Question actualQuestion = androidFailedQuestionStore.pop();
        assertThat(actualQuestion, is(questionList.get(0)));
        assertFalse(androidFailedQuestionStore.hasFailedQuestions());
    }

    @Test
    public void testThatCanStoreAndRetrieveFailedInOrderQuestions() {
        FailedQuestionStore androidFailedQuestionStore = new AndroidFailedQuestionStore(sharedPreferences);
        for (Question question : questionList) {
            androidFailedQuestionStore.store(question);
        }
        for (Question question : questionList) {
            assertThat(question, is(androidFailedQuestionStore.pop()));
        }
    }


    private static class AndroidFailedQuestionStore implements FailedQuestionStore {
        public static final String FAILED_QUESTIONS = "failed_questions";

        private SharedPreferences sharedPreferences;

        public AndroidFailedQuestionStore(SharedPreferences sharedPreferences) {
            this.sharedPreferences = sharedPreferences;
        }

        @Override
        public boolean hasFailedQuestions() {
            return !sharedPreferences.getStringSet(FAILED_QUESTIONS, new HashSet<String>()).isEmpty();
        }

        @Override
        public Question pop() {
            List<FailedQuestionToStore> failedQuestions = getQuestions();
            Collections.sort(failedQuestions, new Comparator<FailedQuestionToStore>() {
                @Override
                public int compare(FailedQuestionToStore lhs, FailedQuestionToStore rhs) {
                    return lhs.position > rhs.position ? 1 : -1;
                }
            });

            FailedQuestionToStore failedQuestionToStore = failedQuestions.get(0);
            failedQuestions.remove(failedQuestionToStore);
            store(failedQuestions);
            return failedQuestionToStore.question;
        }

        @Override
        public void store(Question question) {
            List<FailedQuestionToStore> failedQuestions = getQuestions();
            FailedQuestionToStore failedQuestionToStore = new FailedQuestionToStore(failedQuestions.size(), question);

            failedQuestions.add(failedQuestionToStore);
            store(failedQuestions);
        }

        private void store(List<FailedQuestionToStore> failedQuestions) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Set<String> failedQuestionsAsStringset = JSONSerialiser.convertToStringSet(failedQuestions);
            editor.putStringSet(FAILED_QUESTIONS, failedQuestionsAsStringset);
            editor.apply();
        }

        private List<FailedQuestionToStore> getQuestions() {
            Set<String> failedQuestionsAsStringset = sharedPreferences.getStringSet(FAILED_QUESTIONS, new HashSet<String>());
            return JSONSerialiser.deserialiseQuestions(failedQuestionsAsStringset);
        }
    }

    private static class JSONSerialiser {

        private static final Map<String, Persons.Person> STRING_TO_PERSON = new HashMap<String, Persons.Person>() {{
            for (Persons.Person person : SupportedPersons.ALL) {
                put(person.getPerson(), person);
            }
        }};
        private static final String PERSON_KEY = "person";
        private static final String MOOD_AND_TEST_KEY = "moodAndTense";
        private static final String VERB_KEY = "verb";
        private static final String ORDER_KEY = "orderKey";

        private static Set<String> convertToStringSet(List<FailedQuestionToStore> failedQuestionToStoreList)  {
            Set<String> failedQuestionsAsStringSet = new HashSet<>();
            for (FailedQuestionToStore failedQuestionToStore : failedQuestionToStoreList) {
                try {
                    failedQuestionsAsStringSet.add(JSONSerialiser.serialiseQuestion(failedQuestionToStore));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return failedQuestionsAsStringSet;
        }

        private static String serialiseQuestion(FailedQuestionToStore failedQuestionToStore) throws JSONException {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(ORDER_KEY, failedQuestionToStore.position);
            jsonObject.put(MOOD_AND_TEST_KEY, failedQuestionToStore.question.moodAndTense.toString());
            jsonObject.put(PERSON_KEY, failedQuestionToStore.question.person.getPerson());
            jsonObject.put(VERB_KEY, VerbSerialiser.serialiseVerb(failedQuestionToStore.question.verb));
            return jsonObject.toString();
        }


        private static FailedQuestionToStore deserializeQuestion(String serialisedQuestion) throws JSONException {
            JSONObject jsonQuestion = new JSONObject(serialisedQuestion);
            String serialisedVerb = jsonQuestion.getString(VERB_KEY);
            InfinitiveVerb infinitiveVerb = VerbSerialiser.deserialiseVerb(serialisedVerb);
            MoodAndTense moodAndTense = new MoodAndTenseFactory().createFromString(jsonQuestion.getString("moodAndTense"));
            return new FailedQuestionToStore(jsonQuestion.getInt(ORDER_KEY), new Question(STRING_TO_PERSON.get(jsonQuestion.getString("person")), infinitiveVerb, moodAndTense));
        }

        public static List<FailedQuestionToStore> deserialiseQuestions(Set<String> failedQuestionsAsStringset) {
            List<FailedQuestionToStore> failedQuestions = new ArrayList<>();
            Iterator<String> failedQuestionsIterator = failedQuestionsAsStringset.iterator();
            while(failedQuestionsIterator.hasNext()) {
                try {
                    failedQuestions.add(JSONSerialiser.deserializeQuestion(failedQuestionsIterator.next()));
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
            return failedQuestions;
        }
    }

    private static class VerbSerialiser {

        private static final String FRENCH_VERB_KEY = "frenchVerb";
        private static final String ENGLISH_VERB_KEY = "englishVerb";
        private static final String AUXILIARY_KEY = "auxiliary";

        public static String serialiseVerb(InfinitiveVerb verb) throws JSONException {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(FRENCH_VERB_KEY, verb.frenchVerb.toString());
            jsonObject.put(ENGLISH_VERB_KEY, verb.englishVerb);
            jsonObject.put(AUXILIARY_KEY, verb.auxiliary.toString());
            return jsonObject.toString();
        }

        @NonNull
        private static InfinitiveVerb deserialiseVerb(String serialisedVerb) throws JSONException {
            JSONObject jsonVerb = new JSONObject(serialisedVerb);
            return new InfinitiveVerb(jsonVerb.getString(FRENCH_VERB_KEY), jsonVerb.getString(ENGLISH_VERB_KEY), jsonVerb.getString(AUXILIARY_KEY));
        }
    }

    private static class FailedQuestionToStore {
        private final int position;
        private final Question question;

        public FailedQuestionToStore(int position, Question question) {
            this.position = position;
            this.question = question;
        }
    }
}
