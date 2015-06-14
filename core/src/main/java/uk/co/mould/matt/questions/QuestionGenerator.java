package uk.co.mould.matt.questions;

import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.QuestionVerb;

public interface QuestionGenerator {
	Persons.Person getRandomPerson();
	QuestionVerb getRandomVerb();
}
