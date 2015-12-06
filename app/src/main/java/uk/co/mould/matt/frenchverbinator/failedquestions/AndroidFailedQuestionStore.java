package uk.co.mould.matt.frenchverbinator.failedquestions;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.co.mould.matt.FailedQuestionStore;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.questions.Question;

public class AndroidFailedQuestionStore implements FailedQuestionStore {
    public static final String FAILED_QUESTIONS = "failed_questions";

    private SharedPreferences sharedPreferences;

    public AndroidFailedQuestionStore(Context context) {
        sharedPreferences = context.getSharedPreferences(AndroidFailedQuestionStore.FAILED_QUESTIONS, 0);
    }

    @Override
    public void store(Question newFailedQuestion) {
        List<FailedQuestionToStore> failedQuestions = getQuestions();
        for (FailedQuestionToStore failedQuestion : failedQuestions) {
            if (failedQuestion.question.equals(newFailedQuestion)) {
                return;
            }
        }
        FailedQuestionToStore failedQuestionToStore = new FailedQuestionToStore(failedQuestions.size(), newFailedQuestion);

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


    public void getFailedQuestion(Callback capturingCallback, List<MoodAndTense> moodsAndTenses) {
        List<FailedQuestionToStore> allFailedQuestions = getQuestions();
        List<FailedQuestionToStore> filteredFailedQuestion =  new ArrayList<>();
        for (FailedQuestionToStore failedQuestion : allFailedQuestions) {
            if (moodsAndTenses.contains(failedQuestion.question.moodAndTense)) {
                filteredFailedQuestion.add(failedQuestion);
            }
        }

        if (filteredFailedQuestion.isEmpty()) {
            capturingCallback.failure();
        }
        else {
            Collections.sort(filteredFailedQuestion, new Comparator<FailedQuestionToStore>() {
                @Override
                public int compare(FailedQuestionToStore lhs, FailedQuestionToStore rhs) {
                    return lhs.position > rhs.position ? 1 : -1;
                }
            });
            FailedQuestionToStore failedQuestionToReturn = filteredFailedQuestion.get(0);
            allFailedQuestions.remove(failedQuestionToReturn);
            store(allFailedQuestions);
            capturingCallback.success(failedQuestionToReturn.question);
        }
    }


}
