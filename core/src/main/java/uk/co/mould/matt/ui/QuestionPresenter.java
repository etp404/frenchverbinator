package uk.co.mould.matt.ui;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.marking.AnswerChecker;
import uk.co.mould.matt.questions.QuestionGenerator;

public class QuestionPresenter {
	private AnswerChecker answerChecker;
	private QuestionView questionView;
	private QuestionGenerator fakeQuestionGenerator;

	public QuestionPresenter(uk.co.mould.matt.ui.QuestionView questionView,
							 uk.co.mould.matt.questions.QuestionGenerator questionGenerator,
							 Conjugator conjugator) {
		this.questionView = questionView;
		this.fakeQuestionGenerator = questionGenerator;
		this.answerChecker = new AnswerChecker(conjugator);
	}

	public void showQuestion() {
		Persons.Person questionPerson = fakeQuestionGenerator.getRandomPerson();
		questionView.setPerson(questionPerson);
		InfinitiveVerb questionVerb = fakeQuestionGenerator.getRandomVerb();
		questionView.setVerb(questionVerb);
		questionView.enterQuestionMode();
		this.answerChecker.setQuestion(questionPerson, questionVerb);
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

}
