package uk.co.mould.matt.ui;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;

public interface QuestionView {
	void setPerson(Persons.Person randomPerson);

	void setVerb(InfinitiveVerb randomVerb);

	void showCorrect();

	void showIncorrect();

	String getAnswer();

	void answerMode();

	void enterQuestionMode();
}
