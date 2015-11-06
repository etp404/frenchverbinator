package uk.co.mould.matt.questions;

public interface QuestionGenerator {
    void getQuestion(Callback callback);

    void repeatFailedQuestionAfter(Question failedQuestion, int repeatAfter);
}
