package uk.co.mould.matt.fakes;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.ui.QuestionView;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;

public class FakeQuestionView implements QuestionView {
	public String person;
	public String verb;
	public boolean showingAnswerAsCorrect = false;
	public boolean showingAnswerAsIncorrect = false;
	public boolean inAnswerMode = false;
	public boolean inQuestionMode = false;
	public String answer = "default";
	public boolean correctionVisible = false;
	public String correctAnswerValue;
	public boolean answerBoxIsEnabled;
	public boolean showingResultBox;

	@Override
	public void setPerson(Persons.Person randomPerson) {
		person = randomPerson.getEnglishPronoun();
	}

	@Override
	public void setVerb(InfinitiveVerb randomVerb) {
		verb = randomVerb.toString();
	}

	@Override
	public void setResultToCorrect() {
		showingAnswerAsCorrect = true;
	}

	@Override
	public void setResultToIncorrect() {
		showingAnswerAsIncorrect = true;
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

	@Override
	public void showCorrection() {
		correctionVisible = true;
	}

	@Override
	public void hideCorrection() {
		correctionVisible = false;
	}

	@Override
	public void setCorrectAnswerValue(ConjugatedVerbWithPronoun presentConjugationOf) {
		correctAnswerValue = presentConjugationOf.toString();
	}

	@Override
	public void showResultBox() {
		showingResultBox = true;
	}

	@Override
	public void enableAnswerBox() {
		answerBoxIsEnabled = true;
	}
}
