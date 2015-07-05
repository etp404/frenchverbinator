package uk.co.mould.matt.conjugators;

import uk.co.mould.matt.CantConjugateException;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.Conjugation;
import uk.co.mould.matt.data.FrenchInfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.VerbTemplate;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PerfectIndicative;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbTemplateParser;

public class Conjugator {

    private final VerbTemplateParser verbTemplateParser;
    private final ConjugationParser conjugationParser;
    private final PronounHandler pronounHandler;

    public Conjugator(VerbTemplateParser verbTemplateParser, ConjugationParser conjugationParser) {
        this.verbTemplateParser = verbTemplateParser;
        this.conjugationParser = conjugationParser;
        pronounHandler = new PronounHandler();
    }

    public ConjugatedVerbWithPronoun getConjugationOf(InfinitiveVerb infinitiveVerb, Persons.Person person, MoodAndTense verbMoodAndTense) throws CantConjugateException {
        VerbTemplate template = verbTemplateParser.getTemplateForVerb(
                infinitiveVerb.frenchVerb);
        Conjugation conjugation;
        if (verbMoodAndTense instanceof PerfectIndicative) {
            conjugation = new CompoundTenseConjugator().conjugate(infinitiveVerb, person, template);
        }
        else{
            conjugation = conjugationParser.getConjugation(infinitiveVerb.frenchVerb,
                    template, person, verbMoodAndTense);
        }
        return pronounHandler.addPronoun(conjugation, person);
    }

    private class CompoundTenseConjugator {

        public Conjugation conjugate(InfinitiveVerb infinitiveVerb, Persons.Person person, VerbTemplate template) throws CantConjugateException {
            Conjugation conjugation;
            VerbTemplate auxiliaryTemplate = verbTemplateParser.getTemplateForVerb(infinitiveVerb.auxiliary);
            Conjugation auxiliaryConjugation = conjugationParser.getConjugation(
                    infinitiveVerb.auxiliary,
                    auxiliaryTemplate,
                    person,
                    new PresentIndicative());
            Conjugation participle = conjugationParser.getPerfectParticiple(infinitiveVerb.frenchVerb, template, person);
            conjugation = new Conjugation(auxiliaryConjugation.toString() + " " + participle.toString());
            return conjugation;
        }
    }
}

