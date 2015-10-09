package uk.co.mould.matt.fakes;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.frenchverbinator.QuestionPresenter;
import uk.co.mould.matt.frenchverbinator.QuestionView;
import uk.co.mould.matt.marking.Score;
import uk.co.mould.matt.questions.Question;

public class FakeQuestionView implements QuestionView {
	public Question setQuestionCalledWithQuestion;
    public Boolean noTensesSelectedIsShown;
    public Score hasBeenToldToShowScore;
	public SubmitListener submitListener;
	public boolean toldToShowCorrectAnswer;
	public ConjugatedVerbWithPronoun toldToShowIncorrectWithCorrection;
	public NextQuestionListener nextQuestionListener;

	@Override
	public void setQuestion(Question question) {
		this.setQuestionCalledWithQuestion = question;
	}

	@Override
	public void setResultToCorrect() {
		toldToShowCorrectAnswer = true;
	}

	@Override
	public void setResultToIncorrect(ConjugatedVerbWithPronoun presentConjugationOf) {
		toldToShowIncorrectWithCorrection = presentConjugationOf;
	}

    @Override
    public void showNoTensesSelected() {
        noTensesSelectedIsShown = true;
    }

    @Override
    public void showScore(Score score) {
        hasBeenToldToShowScore = score;
    }

	@Override
	public void addSubmitListener(SubmitListener submitListener) {
		this.submitListener = submitListener;
	}

	@Override
	public void addNextQuestionListener(NextQuestionListener nextQuestionListener) {
		this.nextQuestionListener = nextQuestionListener;
	}
}
