package uk.co.mould.matt.helpers;

import java.util.List;

import uk.co.mould.matt.FailedQuestionStore;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.questions.Question;

public class FakeFailedQuestionStore implements FailedQuestionStore {
    public Question question;
    public Question storedQuestion;

    public FakeFailedQuestionStore() {

    }

    public FakeFailedQuestionStore(Question question) {
        this.question = question;
    }

    @Override
    public boolean hasFailedQuestions(Filter filter) {
        return filter.match(question);
    }

    public Question pop() {
        return question;
    }

    @Override
    public boolean hasFailedQuestions() {
        return question!=null;
    }

    @Override
    public void store(Question question) {
        storedQuestion = question;
    }

    @Override
    public Question pop(Filter questionFilter) {
        if (questionFilter.match(question)) {
            return question;
        }
        return null;
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
