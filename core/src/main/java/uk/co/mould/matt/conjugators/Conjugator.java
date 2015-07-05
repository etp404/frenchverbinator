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
        //TODO: tidy this out.
        Conjugation conjugation;
        if (verbMoodAndTense instanceof PerfectIndicative && infinitiveVerb.auxiliary.equals("être")) {
            VerbTemplate avoirTemplate = verbTemplateParser.getTemplateForVerb(
                    FrenchInfinitiveVerb.fromString("être"));
            Conjugation auxiliaryConjugation = conjugationParser.getConjugation(
                    FrenchInfinitiveVerb.fromString("être"),
                    avoirTemplate, person, new PresentIndicative());
            Conjugation participle = conjugationParser.getPerfectParticiple(
                    infinitiveVerb.frenchVerb, template, person);
            conjugation = new Conjugation(auxiliaryConjugation.toString() + " " + participle.toString());
        }
        else if (verbMoodAndTense instanceof PerfectIndicative) {
            VerbTemplate avoirTemplate = verbTemplateParser.getTemplateForVerb(
                    FrenchInfinitiveVerb.fromString("avoir"));
            Conjugation auxiliaryConjugation = conjugationParser.getConjugation(
                    FrenchInfinitiveVerb.fromString("avoir"),
                    avoirTemplate, person, new PresentIndicative());
            Conjugation participle = conjugationParser.getPerfectParticiple(
                    infinitiveVerb.frenchVerb, template);
            conjugation = new Conjugation(auxiliaryConjugation.toString() + " " + participle.toString());
        }
        else{
            conjugation = conjugationParser.getConjugation(infinitiveVerb.frenchVerb,
                    template, person, verbMoodAndTense);
        }
        return pronounHandler.addPronoun(conjugation, person);
    }
}

