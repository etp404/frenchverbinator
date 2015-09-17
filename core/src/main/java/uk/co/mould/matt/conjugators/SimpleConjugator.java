package uk.co.mould.matt.conjugators;

import uk.co.mould.matt.exceptions.CantConjugateException;
import uk.co.mould.matt.data.Conjugation;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.VerbTemplate;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbTemplateParser;

class SimpleConjugator implements VerbConjugator {

    private VerbTemplateParser verbTemplateParser;
    private ConjugationParser conjugationParser;

    public SimpleConjugator(VerbTemplateParser verbTemplateParser, ConjugationParser conjugationParser) {
        this.verbTemplateParser = verbTemplateParser;
        this.conjugationParser = conjugationParser;
    }

    public Conjugation conjugate(InfinitiveVerb infinitiveVerb, Persons.Person person, MoodAndTense verbMoodAndTense) throws CantConjugateException {
        VerbTemplate template = verbTemplateParser.getTemplateForVerb(
                infinitiveVerb.frenchVerb);
        return conjugationParser.getConjugation(infinitiveVerb.frenchVerb,
                template, person, verbMoodAndTense);
    }
}
