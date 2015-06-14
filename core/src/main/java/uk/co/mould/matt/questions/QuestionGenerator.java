package uk.co.mould.matt.questions;

import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;

public interface QuestionGenerator {
	Persons.Person getRandomPerson();
	InfinitiveVerb getRandomVerb();
}
