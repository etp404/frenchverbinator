package uk.co.mould.matt;

import uk.co.mould.matt.questions.Question;

public interface FailedQuestionStore {
    Question pop();
}
