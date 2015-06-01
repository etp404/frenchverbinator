package uk.co.mould.matt;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import uk.co.mould.matt.data.*;

import static org.junit.Assert.assertEquals;

public final class ConjugatorFacadeTest {

	private final ConjugatorFacade conjugatorFacade;

	public ConjugatorFacadeTest() throws ParserConfigurationException, SAXException, IOException {
		conjugatorFacade = new ConjugatorFacade();
	}

	@Test
	public void testThat_Aimer_Present_FirstPersonSingularIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("J'aime"), conjugatorFacade.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.FIRST_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aimer_Present_SecondPersonSingularIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Tu aimes"), conjugatorFacade.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.SECOND_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aimer_Present_ThirdPersonSingularIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Il aime"), conjugatorFacade.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.THIRD_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aimer_Present_FirstPersonPluralIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Nous aimons"), conjugatorFacade.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.FIRST_PERSON_PLURAL));
	}

	@Test
	public void testThat_Aimer_Present_SecondPersonPluralIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Vous aimez"), conjugatorFacade.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.SECOND_PERSON_PLURAL));
	}

	@Test
	public void testThat_Aimer_Present_ThirdPersonPluralIsConjugatedAsExpected() {
		ConjugatedVerbWithPronoun conjugatedVerbWithPronoun = conjugatorFacade.getPresentConjugationOf(new InfinitiveVerb("aimer"), Persons.THIRD_PERSON_PLURAL);
		assertEquals(new ConjugatedVerbWithPronoun("Ils aiment"), conjugatedVerbWithPronoun);
	}

	@Test
	public void testThat_Perdre_Present_FirstPersonPluralIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Je perds"), conjugatorFacade.getPresentConjugationOf(new InfinitiveVerb("perdre"), Persons.FIRST_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aller_Present_ThirdPersonPluralIsConjugatedAsExpected() {
		ConjugatedVerbWithPronoun conjugatedVerbWithPronoun = conjugatorFacade.getPresentConjugationOf(new InfinitiveVerb("aller"), Persons.THIRD_PERSON_SINGULAR);
		assertEquals(new ConjugatedVerbWithPronoun("Il va"), conjugatedVerbWithPronoun);
	}
}
