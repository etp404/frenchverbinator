package uk.co.mould.matt.marking;

import uk.co.mould.matt.FailedQuestionStore;
import uk.co.mould.matt.exceptions.CantConjugateException;
import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.questions.Question;

public class AnswerChecker implements AnswerChecking {
    private Conjugator conjugator;
    private FailedQuestionStore failedQuestionStore;

    public AnswerChecker(Conjugator conjugator, FailedQuestionStore failedQuestionStore) {
        this.conjugator = conjugator;
        this.failedQuestionStore = failedQuestionStore;
    }

    @Override
    public void check(Question question, String answer, Callback callback) {
        ConjugatedVerbWithPronoun correctAnswer;
        try {
            correctAnswer = conjugator.getConjugationOf(
                    question.verb,
                    question.person,
                    question.moodAndTense);
            if (correctAnswer.toString().toLowerCase().equals(answer.toLowerCase().trim())) {
                callback.correct();
            }
            else {
                callback.incorrect(correctAnswer);
                failedQuestionStore.store(question);
            }
        } catch (CantConjugateException ignored) {
        }
    }

}
