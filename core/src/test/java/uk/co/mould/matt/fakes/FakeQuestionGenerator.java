package uk.co.mould.matt.fakes;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.VerbMoodsAndTenses;
import uk.co.mould.matt.questions.QuestionGenerator;
import uk.co.mould.matt.data.Persons;

public class FakeQuestionGenerator implements QuestionGenerator {
	private Persons.Person person;
	private InfinitiveVerb verb;
    private VerbMoodsAndTenses.VerbMoodAndTense verbMoodAndTense;

    public FakeQuestionGenerator(InfinitiveVerb verb,
                                 Persons.Person person,
                                 VerbMoodsAndTenses.VerbMoodAndTense verbMoodAndTense) {
		this.person = person;
		this.verb = verb;
        this.verbMoodAndTense = verbMoodAndTense;
    }

	@Override
	public Persons.Person getRandomPerson() {
		return person;
	}

	@Override
	public InfinitiveVerb getRandomVerb() {
		return verb;
	}

    @Override
    public VerbMoodsAndTenses.VerbMoodAndTense getRandomVerbMoodAndTense() {
        return verbMoodAndTense;
    }
}
