package uk.co.mould.matt;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public final class Conjugator {

	private final ConjugationParser conjugationParser;
	private final VerbListParser verbListParser;

	public Conjugator() throws IOException, SAXException, ParserConfigurationException {
		verbListParser = new VerbListParser(new File("res/verbs-fr.xml"));
		conjugationParser = new ConjugationParser(new File("res/conjugation-fr.xml"));
	}

	public ConjugatedVerbWithPronoun getPresentConjugationOf(InfinitiveVerb infinitive, Persons.Person person) {
		VerbTemplate template = verbListParser.getTemplateForVerb(infinitive);
		Conjugation conjugation = conjugationParser.getConjugator(template, person);
		String conjugatedVerb = infinitive.conjugate(conjugation.infinitiveEnding, conjugation.ending);
		return new ConjugatedVerbWithPronoun(person.getPronoun() + conjugatedVerb);
	}
}
