package uk.co.mould.matt.ui;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.marking.AnswerChecker;
import uk.co.mould.matt.questions.QuestionGenerator;

public class QuestionPresenter {
	private final Conjugator conjugator;
	private AnswerChecker answerChecker;
	private QuestionView questionView;
	private QuestionGenerator fakeQuestionGenerator;
	private Persons.Person questionPerson;
	private InfinitiveVerb questionVerb;

	public QuestionPresenter(QuestionView questionView,
							 QuestionGenerator questionGenerator,
							 Conjugator conjugator) {
		this.questionView = questionView;
		this.fakeQuestionGenerator = questionGenerator;
		this.conjugator = conjugator;
		this.answerChecker = new AnswerChecker(conjugator);
	}

	public void showQuestion() {
		questionPerson = fakeQuestionGenerator.getRandomPerson();
		questionVerb = fakeQuestionGenerator.getRandomVerb();
		questionView.setQuestion(questionPerson, questionVerb);
		questionView.hideCorrection();
		questionView.enableAnswerBox();

		questionView.showSubmitButton();
		questionView.enableSubmitButton();

		questionView.hideNextQuestionButton();
		questionView.disableNextQuestionButton();

		questionView.hideResultBox();

		this.answerChecker.setQuestion(questionPerson, questionVerb);
	}

	public void submitAnswer() {
		answerChecker.check(questionView.getAnswer(), new AnswerChecker.Callback() {
			@Override
			public void correct() {
				questionView.setResultToCorrect();
			}

			@Override
			public void incorrect() {
				questionView.setResultToIncorrect();
				questionView.showCorrection();
				questionView.setCorrection(conjugator.getPresentConjugationOf(questionVerb, questionPerson));
			}
		});
		questionView.disableAnswerBox();
		questionView.showResultBox();

		questionView.hideSubmitButton();
		questionView.disableSubmitButton();

		questionView.showNextQuestionButton();
		questionView.enableNextQuestionButton();
	}

}
