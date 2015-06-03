package uk.co.mould.matt;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;

public class QuestionPresenter {
	private QuestionView questionView;
	private QuestionGenerator fakeQuestionGenerator;
	private Conjugator conjugator;
	private Persons.Person questionPerson;
	private InfinitiveVerb questionVerb;


	public QuestionPresenter(QuestionView questionView,
							 QuestionGenerator questionGenerator,
							 Conjugator conjugator) {
		this.questionView = questionView;
		this.fakeQuestionGenerator = questionGenerator;
		this.conjugator = conjugator;
	}

	public void showQuestion() {
		questionPerson = fakeQuestionGenerator.getRandomPerson();
		questionView.setPerson(questionPerson);
		questionVerb = fakeQuestionGenerator.getRandomVerb();
		questionView.setVerb(questionVerb);
	}

	public void submitAnswer(String answer) {
		if (checkAnswer(answer)) {
			questionView.showCorrect();
		}
		else {
			questionView.showIncorrect();
		}
	}

	private boolean checkAnswer(String answer) {
		ConjugatedVerbWithPronoun correctAnswer = conjugator.getPresentConjugationOf(questionVerb, questionPerson);
		return correctAnswer.toString().toLowerCase().equals(answer.toLowerCase());
	}
}
