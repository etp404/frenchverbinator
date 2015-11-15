package uk.co.mould.matt.helpers;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;

public class FakeConjugator extends Conjugator {
    private Persons.Person personMatchingAnswer;
    private InfinitiveVerb verbMatchingAnswer;
    private MoodAndTense verbMoodAndTestMatchingAnswer;
    private ConjugatedVerbWithPronoun correctAnswer;


    public FakeConjugator(Persons.Person personMatchingAnswer,
                          InfinitiveVerb verbMatchingAnswer,
                          MoodAndTense verbMoodAndTestMatchingAnswer,
                          ConjugatedVerbWithPronoun correctAnswer) {
        super(null, null);
        this.personMatchingAnswer = personMatchingAnswer;
        this.verbMatchingAnswer = verbMatchingAnswer;
        this.verbMoodAndTestMatchingAnswer = verbMoodAndTestMatchingAnswer;
        this.correctAnswer = correctAnswer;
    }

    public FakeConjugator(ConjugatedVerbWithPronoun correctAnswer) {
        super(null,null);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public ConjugatedVerbWithPronoun getConjugationOf(InfinitiveVerb infinitive, Persons.Person person, MoodAndTense verbMoodAndTense) {
        if (personMatchingAnswer == person && verbMatchingAnswer == infinitive && verbMoodAndTestMatchingAnswer == verbMoodAndTense) {
            return correctAnswer;
        }
        return null;
    }
}
