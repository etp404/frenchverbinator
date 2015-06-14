package uk.co.mould.matt.fakes;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.ui.QuestionView;
import uk.co.mould.matt.data.Persons;

public class FakeQuestionView implements QuestionView {
	public Persons.Person person;
	public InfinitiveVerb verb;
	public Boolean resultBoxShowingCorrect = null;
	public Boolean resultBoxShowingIncorrect = null;
	public String answer = "default";
	public Boolean correctionVisible = false;
	public String correctionValue;
	public Boolean answerBoxIsEnabled = null;
	public Boolean resultBoxVisible = null;
	public Boolean nextQuestionButtonVisible = null;
	public Boolean nextQuestionButtonEnabled = null;
	public Boolean submitButtonEnabled = null;
	public Boolean submitButtonVisible = null;

	@Override
	public void setQuestion(Persons.Person person, InfinitiveVerb verb) {
		this.person = person;
		this.verb = verb;

	}

	@Override
	public void setResultToCorrect() {
		resultBoxShowingCorrect = true;
	}

	@Override
	public void setResultToIncorrect() {
		resultBoxShowingIncorrect = true;
	}

	@Override
	public String getAnswer() {
		return answer;
	}

	@Override
	public void setCorrection(ConjugatedVerbWithPronoun presentConjugationOf) {
		correctionValue = presentConjugationOf.toString();
	}

	@Override
	public void disableAnswerBox() {
		answerBoxIsEnabled = false;
	}

	@Override
	public void showNextQuestionButton() {
		nextQuestionButtonVisible = true;
	}

	@Override
	public void enableNextQuestionButton() {
		nextQuestionButtonEnabled = true;
	}

	@Override
	public void disableNextQuestionButton() {
		nextQuestionButtonEnabled = false;
	}

	@Override
	public void showSubmitButton() {
		submitButtonVisible = true;
	}

	@Override
	public void hideNextQuestionButton() {
		nextQuestionButtonVisible = false;
	}

	@Override
	public void hideResultBox() {
		resultBoxVisible = false;
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
	public void showResultBox() {
		resultBoxVisible = true;
	}

	@Override
	public void hideSubmitButton() {
		submitButtonVisible = false;
	}

	@Override
	public void enableSubmitButton() {
		submitButtonEnabled = true;
	}

	@Override
	public void disableSubmitButton() {
		submitButtonEnabled = false;
	}

	@Override
	public void enableAnswerBox() {
		answerBoxIsEnabled = true;
	}

	@Override
	public void clearAnswerBox() {
		answer = "";
	}
}
