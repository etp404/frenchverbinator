package uk.co.mould.matt.questions;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;

public interface QuestionGenerator {
	Persons.Person getRandomPerson();
	InfinitiveVerb getRandomVerb();
}
