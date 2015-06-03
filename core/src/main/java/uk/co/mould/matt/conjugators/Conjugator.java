package uk.co.mould.matt.conjugators;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.Conjugation;
import uk.co.mould.matt.data.VerbTemplate;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbListParser;

public class Conjugator {

	private final VerbListParser verbListParser;
	private final ConjugationParser conjugationParser;
	private final PronounHandler pronounHandler;

	public Conjugator(VerbListParser verbListParser, ConjugationParser conjugationParser) {
		this.verbListParser = verbListParser;
		this.conjugationParser = conjugationParser;
		pronounHandler = new PronounHandler();
	}

	public ConjugatedVerbWithPronoun getPresentConjugationOf(uk.co.mould.matt.data.InfinitiveVerb infinitive, uk.co.mould.matt.data.Persons.Person person) {
		VerbTemplate template = verbListParser.getTemplateForVerb(infinitive);
		Conjugation conjugation = conjugationParser.getConjugation(infinitive, template, person);
		return pronounHandler.addPronoun(conjugation, person);
	}

}
