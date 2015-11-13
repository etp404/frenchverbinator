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
    };

    boolean hasFailedQuestions(Filter filter);

    Question pop();

    boolean hasFailedQuestions();

    void store(Question question);

    Question pop(Filter questionFilter);

    interface Filter {
        boolean match(Question question);
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
