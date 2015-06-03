package uk.co.mould.matt.marking;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;

public class AnswerChecker {
	private Persons.Person questionPerson;
	private InfinitiveVerb questionVerb;
	private Conjugator conjugator;

	public AnswerChecker(Conjugator conjugator) {
		this.conjugator = conjugator;
	}

	public void setQuestion(Persons.Person questionPerson, InfinitiveVerb questionVerb) {
		this.questionPerson = questionPerson;
		this.questionVerb = questionVerb;
	}

	public void check(String answer, Callback callback) {
		if (isAnswerCorrect(answer)) {
			callback.correct();
		}
		else {
			callback.incorrect();
		}
	}

	public boolean isAnswerCorrect(String answer) {
		ConjugatedVerbWithPronoun correctAnswer = conjugator.getPresentConjugationOf(questionVerb, questionPerson);
		return correctAnswer.toString().toLowerCase().equals(answer.toLowerCase());
	}

	public interface Callback {
		void correct();
		void incorrect();
	}
}
