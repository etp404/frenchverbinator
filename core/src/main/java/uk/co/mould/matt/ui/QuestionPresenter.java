package uk.co.mould.matt.ui;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;

public class QuestionPresenter {
	private final AnswerChecker answerChecker;
	private uk.co.mould.matt.ui.QuestionView questionView;
	private uk.co.mould.matt.questions.QuestionGenerator fakeQuestionGenerator;
	private Conjugator conjugator;
	private Persons.Person questionPerson;
	private InfinitiveVerb questionVerb;


	public QuestionPresenter(uk.co.mould.matt.ui.QuestionView questionView,
							 uk.co.mould.matt.questions.QuestionGenerator questionGenerator,
							 Conjugator conjugator) {
		this.questionView = questionView;
		this.fakeQuestionGenerator = questionGenerator;
		this.conjugator = conjugator;
		this.answerChecker = new AnswerChecker();
	}

	public void showQuestion() {
		questionPerson = fakeQuestionGenerator.getRandomPerson();
		questionView.setPerson(questionPerson);
		questionVerb = fakeQuestionGenerator.getRandomVerb();
		questionView.setVerb(questionVerb);
		questionView.enterQuestionMode();
	}

	public void submitAnswer() {
		if (answerChecker.check(questionView.getAnswer())) {
			questionView.showCorrect();
		}
		else {
			questionView.showIncorrect();
		}
		questionView.answerMode();
	}

	private class AnswerChecker {
		private boolean check(String answer) {
			ConjugatedVerbWithPronoun correctAnswer = conjugator.getPresentConjugationOf(questionVerb, questionPerson);
			return correctAnswer.toString().toLowerCase().equals(answer.toLowerCase());
		}

	}

}
