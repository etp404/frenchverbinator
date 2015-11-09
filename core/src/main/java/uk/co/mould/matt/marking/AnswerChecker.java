package uk.co.mould.matt.marking;

import uk.co.mould.matt.exceptions.CantConjugateException;
import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.questions.Question;

public class AnswerChecker implements AnswerChecking {
    private Conjugator conjugator;

    public AnswerChecker(Conjugator conjugator) {
        this.conjugator = conjugator;
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
            }
        } catch (CantConjugateException ignored) {
        }
    }

}
