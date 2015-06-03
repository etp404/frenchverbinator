package uk.co.mould.matt.fakes;

import uk.co.mould.matt.questions.QuestionGenerator;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;

public class FakeQuestionGenerator implements QuestionGenerator {
	private Persons.Person person;
	private InfinitiveVerb verb;

	public FakeQuestionGenerator(Persons.Person person, InfinitiveVerb verb) {
		this.person = person;
		this.verb = verb;
	}

	@Override
	public Persons.Person getRandomPerson() {
		return person;
	}

	@Override
	public InfinitiveVerb getRandomVerb() {
		return verb;
	}
}
