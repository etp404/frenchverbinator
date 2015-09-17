package uk.co.mould.matt.frenchverbinator;

import android.test.ActivityTestCase;

import org.xml.sax.InputSource;

import uk.co.mould.matt.exceptions.CantConjugateException;
import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.SupportedMoodsAndTenses;
import uk.co.mould.matt.data.SupportedPersons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbListParser;
import uk.co.mould.matt.parser.VerbTemplateParser;

public final class TestThatAllWhiteListedVerbsCanBeConjugated extends ActivityTestCase {

    public void testThatVerbsCanBeConjugated() {
        VerbListParser verbListParser = new VerbListParser(new InputSource(getInstrumentation().getTargetContext().getResources().openRawResource(R.raw.verb_list)));
        VerbTemplateParser verbTemplateParser = new VerbTemplateParser(new InputSource(getInstrumentation().getTargetContext().getResources().openRawResource(R.raw.verbs_fr)));
        ConjugationParser conjugationParser = new ConjugationParser(new InputSource(getInstrumentation().getTargetContext().getResources().openRawResource(R.raw.conjugation_fr)));
        Conjugator conjugator = new Conjugator(verbTemplateParser, conjugationParser);

        for (InfinitiveVerb verb : verbListParser.getVerbs()) {
            for (Persons.Person person : SupportedPersons.ALL) {
                for (MoodAndTense moodAndTense : SupportedMoodsAndTenses.ALL) {
                    try {
                        assertNotNull(String.format("Couldn't format %s form of %s", person.getPerson(), verb.frenchVerb),
                                conjugator.getConjugationOf(verb, person, moodAndTense));
                    } catch (CantConjugateException ex) {
                        fail(String.format("Couldn't format %s form of %s in %s", person.getPerson(), verb.frenchVerb, moodAndTense.toString()));
                    }
                }
            }
        }
    }
}
