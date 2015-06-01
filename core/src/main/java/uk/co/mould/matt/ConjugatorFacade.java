package uk.co.mould.matt;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.*;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbListParser;

public final class ConjugatorFacade {

	private final Conjugator conjugator;

	public ConjugatorFacade() throws IOException, SAXException, ParserConfigurationException {
		conjugator = new Conjugator(
							new VerbListParser(new File("res/verbs-fr.xml")),
							new ConjugationParser(new File("res/conjugation-fr.xml")));
	}

	public ConjugatedVerbWithPronoun getPresentConjugationOf(uk.co.mould.matt.data.InfinitiveVerb infinitive, uk.co.mould.matt.data.Persons.Person person) {
		return conjugator.getPresentConjugationOf(infinitive, person);
	}
}
