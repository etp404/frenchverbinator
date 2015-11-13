package uk.co.mould.matt;

import java.util.List;

import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.questions.Question;

public interface FailedQuestionStore {
    FailedQuestionStore NULL = new FailedQuestionStore() {
        @Override
        public boolean hasFailedQuestions(FilterForTheseTenses filterForTheseTenses) {
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
        public Question pop(Filter questionFilter) {
            return null;
        }
    };

    boolean hasFailedQuestions(FilterForTheseTenses filterForTheseTenses);

    Question pop();

    boolean hasFailedQuestions();

    void store(Question question);

    Question pop(Filter questionFilter);

    interface Filter {
        boolean match(Question question);
    }

    class FilterForTheseTenses implements Filter {
        private List<MoodAndTense> moodAndTenses;

        public FilterForTheseTenses(List<MoodAndTense> moodAndTenses) {
            this.moodAndTenses = moodAndTenses;
        }

        @Override
        public boolean match(Question question) {
            if (moodAndTenses.contains(question.moodAndTense)) {
                return true;
            }
            return false;
        }
    }
}
