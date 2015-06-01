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
		assertEquals(new ConjugatedVerbWithPronoun("J'aime"), conjugatorFacade.getPresentConjugationOf(InfinitiveVerb.fromString("aimer"), Persons.FIRST_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aimer_Present_SecondPersonSingularIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Tu aimes"), conjugatorFacade.getPresentConjugationOf(InfinitiveVerb.fromString("aimer"), Persons.SECOND_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aimer_Present_ThirdPersonSingularIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Il aime"), conjugatorFacade.getPresentConjugationOf(InfinitiveVerb.fromString("aimer"), Persons.THIRD_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aimer_Present_FirstPersonPluralIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Nous aimons"), conjugatorFacade.getPresentConjugationOf(InfinitiveVerb.fromString("aimer"), Persons.FIRST_PERSON_PLURAL));
	}

	@Test
	public void testThat_Aimer_Present_SecondPersonPluralIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Vous aimez"), conjugatorFacade.getPresentConjugationOf(InfinitiveVerb.fromString("aimer"), Persons.SECOND_PERSON_PLURAL));
	}

	@Test
	public void testThat_Aimer_Present_ThirdPersonPluralIsConjugatedAsExpected() {
		ConjugatedVerbWithPronoun conjugatedVerbWithPronoun = conjugatorFacade.getPresentConjugationOf(InfinitiveVerb.fromString("aimer"), Persons.THIRD_PERSON_PLURAL);
		assertEquals(new ConjugatedVerbWithPronoun("Ils aiment"), conjugatedVerbWithPronoun);
	}

	@Test
	public void testThat_Perdre_Present_FirstPersonPluralIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Je perds"), conjugatorFacade.getPresentConjugationOf(InfinitiveVerb.fromString("perdre"), Persons.FIRST_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aller_Present_ThirdPersonPluralIsConjugatedAsExpected() {
		ConjugatedVerbWithPronoun conjugatedVerbWithPronoun = conjugatorFacade.getPresentConjugationOf(InfinitiveVerb.fromString("aller"), Persons.THIRD_PERSON_SINGULAR);
		assertEquals(new ConjugatedVerbWithPronoun("Il va"), conjugatedVerbWithPronoun);
	}
}
