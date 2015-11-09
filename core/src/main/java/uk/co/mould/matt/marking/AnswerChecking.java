package uk.co.mould.matt.marking;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.questions.Question;

public interface AnswerChecking {

    void check(Question question, String answer, Callback callback);

    interface Callback {
        void correct();

        void incorrect(ConjugatedVerbWithPronoun corrrection);
    }
}
