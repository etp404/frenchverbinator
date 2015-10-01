package uk.co.mould.matt.frenchverbinator;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.marking.Score;

public interface QuestionView {
	void setQuestion(Persons.Person person, InfinitiveVerb verb, MoodAndTense verbMoodAndTense);

	void setResultToCorrect();

	void setResultToIncorrect();

	void setResultToIncorrect(ConjugatedVerbWithPronoun presentConjugationOf);

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

    void hideAnswerBox();

    void hideQuestionBox();

    void showNoTensesSelected();

    void showQuestionBox();

    void showAnswerBox();

    void hideNoTensesSelected();

    void showScore(Score score);
}
