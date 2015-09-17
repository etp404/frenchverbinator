package uk.co.mould.matt.conjugators;

import java.util.HashMap;
import java.util.Map;

import uk.co.mould.matt.exceptions.CantConjugateException;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.Conjugation;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.tenses.FutureIndicative;
import uk.co.mould.matt.data.tenses.ImperfectIndicative;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PerfectIndicative;
import uk.co.mould.matt.data.tenses.PresentConditional;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.data.tenses.PresentSubjunctive;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbTemplateParser;

public class Conjugator {

    private Map<Class<? extends MoodAndTense>, VerbConjugator> tenseToConjugator = new HashMap<>();

    private final VerbTemplateParser verbTemplateParser;
    private final ConjugationParser conjugationParser;
    private final PronounHandler pronounHandler;

    public Conjugator(VerbTemplateParser verbTemplateParser, ConjugationParser conjugationParser) {
        SimpleConjugator simpleConjugator = new SimpleConjugator(verbTemplateParser, conjugationParser);
        tenseToConjugator.put(PresentIndicative.class, simpleConjugator);
        tenseToConjugator.put(PresentConditional.class, simpleConjugator);
        tenseToConjugator.put(PresentSubjunctive.class, simpleConjugator);
        tenseToConjugator.put(ImperfectIndicative.class, simpleConjugator);
        tenseToConjugator.put(FutureIndicative.class, simpleConjugator);
        tenseToConjugator.put(PerfectIndicative.class, new PerfectTenseConjugator(verbTemplateParser, conjugationParser));

        this.verbTemplateParser = verbTemplateParser;
        this.conjugationParser = conjugationParser;
        pronounHandler = new PronounHandler();
    }

    public ConjugatedVerbWithPronoun getConjugationOf(InfinitiveVerb infinitiveVerb, Persons.Person person, MoodAndTense verbMoodAndTense) throws CantConjugateException {
        VerbConjugator conjugator = tenseToConjugator.get(verbMoodAndTense.getClass());
        Conjugation conjugation = conjugator.conjugate(infinitiveVerb, person, verbMoodAndTense);
        return pronounHandler.addPronoun(conjugation, person);
    }

}

