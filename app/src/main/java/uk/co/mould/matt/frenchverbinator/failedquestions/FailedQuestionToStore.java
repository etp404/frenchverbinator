package uk.co.mould.matt.frenchverbinator.failedquestions;

import uk.co.mould.matt.questions.Question;

public class FailedQuestionToStore {
    public final int position;
    public final Question question;

    public FailedQuestionToStore(int position, Question question) {
        this.position = position;
        this.question = question;
    }
}
