package uk.co.mould.matt;

import java.util.ArrayList;
import java.util.List;

import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.questions.Question;

public interface FailedQuestionStore {
    FailedQuestionStore NULL = new FailedQuestionStore() {
        @Override
        public boolean hasFailedQuestions(Filter filter) {
            return false;
        }

        @Override
        public Question pop() {
            return null;
        }

        @Override
        public boolean hasFailedQuestions() {
            return false;
        }

        @Override
        public void store(Question question) {

        }

        @Override
        public Question pop(Filter filter) {
            return null;
        }

        @Override
        public void clear() {

        }

        @Override
        public void getFailedQuestion(Callback capturingCallback, List<MoodAndTense> moodAndTenses) {

        }
    };

    boolean hasFailedQuestions(Filter filter);

    Question pop();

    boolean hasFailedQuestions();

    void store(Question question);

    Question pop(Filter questionFilter);

    void clear();

    void getFailedQuestion(Callback capturingCallback, List<MoodAndTense> moodAndTenses);

    interface Filter {
        boolean match(Question question);
    }

    interface Callback {
        void success(Question question);
        void failure();
    }

    class FilterForTheseTenses implements Filter {
        private List<MoodAndTense> moodAndTenses = new ArrayList<>();

        public FilterForTheseTenses(List<MoodAndTense> moodAndTenses) {
            this.moodAndTenses = moodAndTenses;
        }

        @Override
        public boolean match(Question question) {
            if (question!=null && moodAndTenses.contains(question.moodAndTense)) {
                return true;
            }
            return false;
        }
    }
}
