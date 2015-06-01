package uk.co.mould.matt;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import static org.junit.Assert.assertEquals;

public final class ConjugatorTest {
	@Test
	public void testThat_Aimer_Present_FirstPersonSingularIsConjugatedAsExpected() throws ParserConfigurationException, SAXException, IOException {
		Conjugator conjugator = new Conjugator();
		assertEquals(new ConjugatedVerbWithPronoun("J'aime"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.FIRST_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aimer_Present_SecondPersonSingularIsConjugatedAsExpected() throws ParserConfigurationException, SAXException, IOException {
		Conjugator conjugator = new Conjugator();
		assertEquals(new ConjugatedVerbWithPronoun("Tu aimes"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.SECOND_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aimer_Present_ThirdPersonSingularIsConjugatedAsExpected() throws ParserConfigurationException, SAXException, IOException {
		Conjugator conjugator = new Conjugator();
		assertEquals(new ConjugatedVerbWithPronoun("Il aime"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.THIRD_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aimer_Present_FirstPersonPluralIsConjugatedAsExpected() throws ParserConfigurationException, SAXException, IOException {
		Conjugator conjugator = new Conjugator();
		assertEquals(new ConjugatedVerbWithPronoun("Nous aimons"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.FIRST_PERSON_PLURAL));
	}

	@Test
	public void testThat_Aimer_Present_SecondPersonPluralIsConjugatedAsExpected() throws ParserConfigurationException, SAXException, IOException {
		Conjugator conjugator = new Conjugator();
		assertEquals(new ConjugatedVerbWithPronoun("Vous aimez"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.SECOND_PERSON_PLURAL));
	}

	@Test
	public void testThat_Aimer_Present_ThirdPersonPluralIsConjugatedAsExpected() throws ParserConfigurationException, SAXException, IOException {
		Conjugator conjugator = new Conjugator();
		assertEquals(new ConjugatedVerbWithPronoun("Ils aiment"), conjugator.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.THIRD_PERSON_PLURAL));
	}
}
