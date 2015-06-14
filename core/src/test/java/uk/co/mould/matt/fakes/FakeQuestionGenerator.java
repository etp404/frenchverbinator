package uk.co.mould.matt.fakes;

import uk.co.mould.matt.data.QuestionVerb;
import uk.co.mould.matt.questions.QuestionGenerator;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;

public class FakeQuestionGenerator implements QuestionGenerator {
	private Persons.Person person;
	private QuestionVerb verb;

	public FakeQuestionGenerator(Persons.Person person, QuestionVerb verb) {
		this.person = person;
		this.verb = verb;
	}

	@Override
	public Persons.Person getRandomPerson() {
		return person;
	}

	@Override
	public QuestionVerb getRandomVerb() {
		return verb;
	}
}
