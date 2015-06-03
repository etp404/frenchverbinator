package uk.co.mould.matt.fakes;

import uk.co.mould.matt.QuestionView;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;

public class FakeQuestionView implements QuestionView {
	public String person;
	public String verb;
	public boolean correctCalled = false;

	@Override
	public void setPerson(Persons.Person randomPerson) {
		person = randomPerson.getEnglishPronoun();
	}

	@Override
	public void setVerb(InfinitiveVerb randomVerb) {
		verb = randomVerb.toString();
	}

	@Override
	public void showCorrect() {
		correctCalled = true;
	}
}
