package uk.co.mould.matt.conjugators;

import uk.co.mould.matt.CantConjugateException;
import uk.co.mould.matt.data.Conjugation;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.VerbTemplate;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbTemplateParser;

class PerfectTenseConjugator implements VerbConjugator {

    private VerbTemplateParser verbTemplateParser;
    private ConjugationParser conjugationParser;

    public PerfectTenseConjugator(VerbTemplateParser verbTemplateParser, ConjugationParser conjugationParser) {
        this.verbTemplateParser = verbTemplateParser;
        this.conjugationParser = conjugationParser;
    }

    public Conjugation conjugate(InfinitiveVerb infinitiveVerb, Persons.Person person, MoodAndTense verbMoodAndTense) throws CantConjugateException {
        VerbTemplate template = verbTemplateParser.getTemplateForVerb(infinitiveVerb.frenchVerb);
        VerbTemplate auxiliaryTemplate = verbTemplateParser.getTemplateForVerb(infinitiveVerb.auxiliary);
        Conjugation auxiliaryConjugation = conjugationParser.getConjugation(
                infinitiveVerb.auxiliary,
                auxiliaryTemplate,
                person,
                new PresentIndicative());
        Conjugation participle = conjugationParser.getPerfectParticiple(infinitiveVerb.frenchVerb, template, person);
        return new Conjugation(auxiliaryConjugation.toString() + " " + participle.toString());
    }
}
