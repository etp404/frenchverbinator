package uk.co.mould.matt;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.*;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbTemplateParser;

import static org.junit.Assert.assertEquals;

public final class ConjugatorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Conjugator conjugator;

    public ConjugatorTest() throws ParserConfigurationException, SAXException, IOException {
        conjugator = new Conjugator(
                new VerbTemplateParser(new InputSource(new FileInputStream("res/verbs-fr.xml"))),
                new ConjugationParser(new InputSource(new FileInputStream("res/conjugation-fr.xml"))));
    }

    @Test
    public void testThat_Aimer_Present_FirstPersonSingularIsConjugatedAsExpected() throws CantConjugateException {
        assertEquals(new ConjugatedVerbWithPronoun("J'aime"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer", null), Persons.FIRST_PERSON_SINGULAR));
    }

    @Test
    public void testThat_Aimer_Present_SecondPersonSingularIsConjugatedAsExpected() throws CantConjugateException {
        assertEquals(new ConjugatedVerbWithPronoun("Tu aimes"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer", null), Persons.SECOND_PERSON_SINGULAR));
    }

    @Test
    public void testThat_Aimer_Present_ThirdPersonSingularIsConjugatedAsExpected() throws CantConjugateException {
        assertEquals(new ConjugatedVerbWithPronoun("Il aime"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer", null), Persons.THIRD_PERSON_SINGULAR));
    }

    @Test
    public void testThat_Aimer_Present_FirstPersonPluralIsConjugatedAsExpected() throws CantConjugateException {
        assertEquals(new ConjugatedVerbWithPronoun("Nous aimons"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer", null), Persons.FIRST_PERSON_PLURAL));
    }

    @Test
    public void testThat_Aimer_Present_SecondPersonPluralIsConjugatedAsExpected() throws CantConjugateException {
        assertEquals(new ConjugatedVerbWithPronoun("Vous aimez"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer", null), Persons.SECOND_PERSON_PLURAL));
    }

    @Test
    public void testThat_Aimer_Present_ThirdPersonPluralIsConjugatedAsExpected() throws CantConjugateException {
        ConjugatedVerbWithPronoun conjugatedVerbWithPronoun = conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer", null), Persons.THIRD_PERSON_PLURAL);
        assertEquals(new ConjugatedVerbWithPronoun("Ils aiment"), conjugatedVerbWithPronoun);
    }

    @Test
    public void testThat_Perdre_Present_FirstPersonPluralIsConjugatedAsExpected() throws CantConjugateException {
        assertEquals(new ConjugatedVerbWithPronoun("Je perds"), conjugator.getPresentConjugationOf(new InfinitiveVerb("perdre", null), Persons.FIRST_PERSON_SINGULAR));
    }

    @Test
    public void testThat_Aller_Present_ThirdPersonPluralIsConjugatedAsExpected() throws CantConjugateException {
        ConjugatedVerbWithPronoun conjugatedVerbWithPronoun = conjugator.getPresentConjugationOf(new InfinitiveVerb("aller", null), Persons.THIRD_PERSON_SINGULAR);
        assertEquals(new ConjugatedVerbWithPronoun("Il va"), conjugatedVerbWithPronoun);
    }

    @Test
    public void testThatIfVerbIsUnknownNullExceptionIsThrown() throws CantConjugateException {
        thrown.expect(CantConjugateException.class);
        conjugator.getPresentConjugationOf(new InfinitiveVerb("some nonexistent verb", null), Persons.THIRD_PERSON_SINGULAR);
    }

    @Test
    public void testThatImperfectTenseCanBeConjugated() throws CantConjugateException {
        ConjugatedVerbWithPronoun conjugatedVerbWithPronoun =
                conjugator.getPresentConjugationOf(
                        new InfinitiveVerb("savoir", null),
                        Persons.THIRD_PERSON_PLURAL,
                        Tenses.IMPERFECT);
        assertEquals(new ConjugatedVerbWithPronoun("Ils savaient"), conjugatedVerbWithPronoun);
    }
}
