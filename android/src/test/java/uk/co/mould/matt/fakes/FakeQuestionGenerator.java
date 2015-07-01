package uk.co.mould.matt.fakes;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.questions.QuestionGenerator;
import uk.co.mould.matt.data.Persons;

public class FakeQuestionGenerator implements QuestionGenerator {
	private Persons.Person person;
	private InfinitiveVerb verb;
    private MoodAndTense verbMoodAndTense;

    public FakeQuestionGenerator(InfinitiveVerb verb,
                                 Persons.Person person,
                                 MoodAndTense verbMoodAndTense) {
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
    public MoodAndTense getRandomVerbMoodAndTense() {
        return verbMoodAndTense;
    }
}
