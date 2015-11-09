package uk.co.mould.matt.marking;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.questions.Question;

public interface AnswerChecking {
    void setQuestion(Question question);

    void check(String answer, Callback callback);

    public interface Callback {
        void correct();

        void incorrect(ConjugatedVerbWithPronoun corrrection);
    }
}
