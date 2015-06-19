package uk.co.mould.matt.ui;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.tenses.VerbMoodsAndTenses;

public interface QuestionView {
	void setQuestion(Persons.Person person, InfinitiveVerb verb, VerbMoodsAndTenses verbMoodAndTense);

	void setResultToCorrect();

	void setResultToIncorrect();

	String getAnswer();

	void showCorrection();

	void hideCorrection();

	void enableAnswerBox();

	void showResultBox();

	void hideSubmitButton();

	void enableSubmitButton();

	void disableSubmitButton();

	void setCorrection(ConjugatedVerbWithPronoun presentConjugationOf);

	void disableAnswerBox();

	void showNextQuestionButton();

	void enableNextQuestionButton();

	void disableNextQuestionButton();

	void showSubmitButton();

	void hideNextQuestionButton();

	void hideResultBox();

	void clearAnswerBox();
}
