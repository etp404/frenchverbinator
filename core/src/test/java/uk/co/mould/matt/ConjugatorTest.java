package uk.co.mould.matt;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import static org.junit.Assert.assertEquals;

public final class ConjugatorTest {

	private final Conjugator conjugator;

	public ConjugatorTest() throws ParserConfigurationException, SAXException, IOException {
		conjugator = new Conjugator();
	}

	@Test
	public void testThat_Aimer_Present_FirstPersonSingularIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("J'aime"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.FIRST_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aimer_Present_SecondPersonSingularIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Tu aimes"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.SECOND_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aimer_Present_ThirdPersonSingularIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Il aime"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.THIRD_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aimer_Present_FirstPersonPluralIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Nous aimons"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.FIRST_PERSON_PLURAL));
	}

	@Test
	public void testThat_Aimer_Present_SecondPersonPluralIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Vous aimez"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.SECOND_PERSON_PLURAL));
	}

	@Test
	public void testThat_Aimer_Present_ThirdPersonPluralIsConjugatedAsExpected() {
		ConjugatedVerbWithPronoun conjugatedVerb = conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.THIRD_PERSON_PLURAL);
		assertEquals(new ConjugatedVerbWithPronoun("Ils aiment"), conjugatedVerb);
	}

	@Test
	public void testThat_Perdre_Present_FirstPersonPluralIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Je perds"), conjugator.getPresentConjugationOf(new InfinitiveVerb("perdre"), Persons.FIRST_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aller_Present_ThirdPersonPluralIsConjugatedAsExpected() {
		ConjugatedVerbWithPronoun conjugatedVerb = conjugator.getPresentConjugationOf(new InfinitiveVerb("aller"), Persons.FIRST_PERSON_SINGULAR);
		assertEquals(new ConjugatedVerbWithPronoun("Il va"), conjugatedVerb);
	}
}
