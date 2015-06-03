package uk.co.mould.matt.fakes;

import uk.co.mould.matt.ui.QuestionView;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;

public class FakeQuestionView implements QuestionView {
	public String person;
	public String verb;
	public boolean correctCalled = false;
	public boolean incorrectCalled = false;
	public boolean inAnswerMode = false;
	public boolean inQuestionMode = false;
	public String answer = "default";

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

	@Override
	public void showIncorrect() {
		incorrectCalled = true;
	}

	@Override
	public String getAnswer() {
		return answer;
	}

	@Override
	public void answerMode() {
		inAnswerMode = true;
	}

	@Override
	public void enterQuestionMode() {
		inQuestionMode = true;
	}
}
