package uk.co.mould.matt.conjugators;

import uk.co.mould.matt.CantConjugateException;
import uk.co.mould.matt.data.Conjugation;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;

public interface VerbConjugator {
    Conjugation conjugate(InfinitiveVerb infinitiveVerb, Persons.Person person, MoodAndTense verbMoodAndTense) throws CantConjugateException;
}
