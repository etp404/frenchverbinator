package uk.co.mould.matt.helpers;

import java.util.List;

import uk.co.mould.matt.FailedQuestionStore;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.questions.Question;

public class FakeFailedQuestionStore implements FailedQuestionStore {
    public Question question;

    public FakeFailedQuestionStore() {

    }

    @Override
    public void store(Question question) {
        this.question = question;
    }

    @Override
    public void clear() {

    }

    @Override
    public void getFailedQuestion(Callback callback, List<MoodAndTense> moodAndTenses) {
        if (question!=null && moodAndTenses.contains(question.moodAndTense)) {
            callback.success(question);
        }
        else {
            callback.failure();
        }

    }
}
