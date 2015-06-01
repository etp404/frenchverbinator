package uk.co.mould.matt.conjugators;

import uk.co.mould.matt.data.*;

public final class PronounHandler {
	public ConjugatedVerbWithPronoun addPronoun(uk.co.mould.matt.data.Conjugation conjugation, uk.co.mould.matt.data.Persons.Person person) {
		return new ConjugatedVerbWithPronoun(person.getPronoun(conjugation.conjugatedVerb) + conjugation.conjugatedVerb);
	}
}
