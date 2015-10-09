package uk.co.mould.matt.marking;

import uk.co.mould.matt.exceptions.CantConjugateException;
import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.questions.Question;

public class AnswerChecker {
    private Conjugator conjugator;
    private Question question;

    public AnswerChecker(Conjugator conjugator) {
        this.conjugator = conjugator;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void check(String answer, Callback callback) {
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

    public interface Callback {
        void correct();

        void incorrect(ConjugatedVerbWithPronoun corrrection);
    }
}
