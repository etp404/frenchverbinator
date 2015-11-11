package uk.co.mould.matt.frenchverbinator.failedquestions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.SupportedPersons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.frenchverbinator.settings.MoodAndTenseFactory;
import uk.co.mould.matt.questions.Question;

class JSONSerialiser {

    private static final Map<String, Persons.Person> STRING_TO_PERSON = new HashMap<String, Persons.Person>() {{
        for (Persons.Person person : SupportedPersons.ALL) {
            put(person.getPerson(), person);
        }
    }};
    private static final String PERSON_KEY = "person";
    private static final String MOOD_AND_TEST_KEY = "moodAndTense";
    private static final String VERB_KEY = "verb";
    private static final String ORDER_KEY = "orderKey";

    public static Set<String> convertToStringSet(List<FailedQuestionToStore> failedQuestionToStoreList)  {
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
