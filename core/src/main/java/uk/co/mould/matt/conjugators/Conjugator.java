package uk.co.mould.matt.conjugators;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.Conjugation;
import uk.co.mould.matt.data.FrenchInfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.VerbTemplate;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbTemplateParser;

public class Conjugator {

	private final VerbTemplateParser verbTemplateParser;
	private final ConjugationParser conjugationParser;
	private final PronounHandler pronounHandler;

	public Conjugator(VerbTemplateParser verbTemplateParser, ConjugationParser conjugationParser) {
		this.verbTemplateParser = verbTemplateParser;
		this.conjugationParser = conjugationParser;
		pronounHandler = new PronounHandler();
	}

	public ConjugatedVerbWithPronoun getPresentConjugationOf(InfinitiveVerb infinitiveVerb, Persons.Person person) {
		VerbTemplate template = verbTemplateParser.getTemplateForVerb(infinitiveVerb.frenchVerb);
		Conjugation conjugation = conjugationParser.getConjugation(infinitiveVerb.frenchVerb, template, person);
		return pronounHandler.addPronoun(conjugation, person);
	}

}
