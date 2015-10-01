package uk.co.mould.matt.fakes;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.frenchverbinator.QuestionView;
import uk.co.mould.matt.marking.Score;

public class FakeQuestionView implements QuestionView {
	public InfinitiveVerb setQuestionCalledWithVerb;
	public Persons.Person setQuestionCalledWithPerson;
    public MoodAndTense setQuestionCalledWithVerbMoodAndTense;
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
    public Boolean noTensesSelectedIsShown;
    public Boolean answerBoxIsVisible;
    public Boolean questionBoxIsVisible;
    public Score hasBeenToldToShowScore;

    @Override
	public void setQuestion(Persons.Person person, InfinitiveVerb verb, MoodAndTense verbMoodAndTense) {
		this.setQuestionCalledWithPerson = person;
		this.setQuestionCalledWithVerb = verb;
        this.setQuestionCalledWithVerbMoodAndTense = verbMoodAndTense;
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
	public void setResultToIncorrect(ConjugatedVerbWithPronoun presentConjugationOf) {

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

    @Override
    public void hideAnswerBox() {
        answerBoxIsVisible = false;
    }

    @Override
    public void hideQuestionBox() {
        questionBoxIsVisible = false;
    }

    @Override
    public void showNoTensesSelected() {
        noTensesSelectedIsShown = true;
    }

    @Override
    public void showQuestionBox() {
        questionBoxIsVisible = true;
    }

    @Override
    public void showAnswerBox() {
        answerBoxIsVisible = true;
    }

    @Override
    public void hideNoTensesSelected() {
        noTensesSelectedIsShown = false;
    }

    @Override
    public void showScore(Score score) {
        hasBeenToldToShowScore = score;
    }
}
