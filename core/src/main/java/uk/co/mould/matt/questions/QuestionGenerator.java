package uk.co.mould.matt.questions;

import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.tenses.MoodAndTense;

public interface QuestionGenerator {
	Persons.Person getRandomPerson();
	InfinitiveVerb getRandomVerb();
    MoodAndTense getRandomVerbMoodAndTense();

	void getQuestion(Callback callback);
}
