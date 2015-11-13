package uk.co.mould.matt;

import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.questions.Question;

public interface FailedQuestionStore {
    FailedQuestionStore NULL = new FailedQuestionStore() {
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
        public Question pop(Filter questionFilter) {
            return null;
        }
    };

    Question pop();

    boolean hasFailedQuestions();

    void store(Question question);

    Question pop(Filter questionFilter);

    interface Filter {
        boolean match(Question question);
    }
}
