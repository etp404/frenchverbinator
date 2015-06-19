package uk.co.mould.matt.marking;

import uk.co.mould.matt.CantConjugateException;
import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.tenses.MoodAndTense;

//TODO: this needs a test.
public class AnswerChecker {
    private InfinitiveVerb infinitiveVerb;
    private Persons.Person questionPerson;
    private MoodAndTense verbMoodAndTense;
    private Conjugator conjugator;

    public AnswerChecker(Conjugator conjugator) {
        this.conjugator = conjugator;
    }

    public void setQuestion(Persons.Person questionPerson,
                            InfinitiveVerb infinitiveVerb,
                            MoodAndTense verbMoodAndTense) {
        this.questionPerson = questionPerson;
        this.infinitiveVerb = infinitiveVerb;
        this.verbMoodAndTense = verbMoodAndTense;
    }

    public void check(String answer, Callback callback) {
        if (isAnswerCorrect(answer)) {
            callback.correct();
        } else {
            callback.incorrect();
        }
    }

    public boolean isAnswerCorrect(String answer) {
        ConjugatedVerbWithPronoun correctAnswer;
        try {
            correctAnswer = conjugator.getPresentConjugationOf(
                    infinitiveVerb,
                    questionPerson,
                    verbMoodAndTense);
        } catch (CantConjugateException ignored) {
            return false;
        }
        return correctAnswer.toString().toLowerCase().equals(answer.toLowerCase().trim());
    }

    public interface Callback {
        void correct();

        void incorrect();
    }
}
