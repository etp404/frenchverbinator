package uk.co.mould.matt.frenchverbinator.failedquestions;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.co.mould.matt.FailedQuestionStore;
import uk.co.mould.matt.questions.Question;

public class AndroidFailedQuestionStore implements FailedQuestionStore {
    public static final String FAILED_QUESTIONS = "failed_questions";

    private SharedPreferences sharedPreferences;

    public AndroidFailedQuestionStore(Context context) {
        sharedPreferences = context.getSharedPreferences(AndroidFailedQuestionStore.FAILED_QUESTIONS, 0);
    }

    @Override
    public boolean hasFailedQuestions() {
        return !sharedPreferences.getStringSet(FAILED_QUESTIONS, new HashSet<String>()).isEmpty();
    }

    @Override
    public boolean hasFailedQuestions(Filter filter) {
        for (FailedQuestionToStore failedQuestionToStore : getQuestions()) {
            if (filter.match(failedQuestionToStore.question)) {
                return true;
            }
        }
        return false;
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
    public Question pop(Filter filter) {
        List<FailedQuestionToStore> failedQuestions = getQuestions();
        Collections.sort(failedQuestions, new Comparator<FailedQuestionToStore>() {
            @Override
            public int compare(FailedQuestionToStore lhs, FailedQuestionToStore rhs) {
                return lhs.position > rhs.position ? 1 : -1;
            }
        });

        FailedQuestionToStore failedQuestionToStore = null;

        for (FailedQuestionToStore failedQuestion : failedQuestions) {
            if (filter.match(failedQuestion.question)) {
                failedQuestionToStore = failedQuestion;
            }
        }
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

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

}
