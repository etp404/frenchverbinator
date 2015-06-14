package uk.co.mould.matt.marking;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;

public class AnswerChecker {
	private Persons.Person questionPerson;
	private InfinitiveVerb infinitiveVerb;
	private Conjugator conjugator;

	public AnswerChecker(Conjugator conjugator) {
		this.conjugator = conjugator;
	}

	public void setQuestion(Persons.Person questionPerson, InfinitiveVerb infinitiveVerb) {
		this.questionPerson = questionPerson;
		this.infinitiveVerb = infinitiveVerb;
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
		ConjugatedVerbWithPronoun correctAnswer = conjugator.getPresentConjugationOf(infinitiveVerb, questionPerson);
		return correctAnswer.toString().toLowerCase().equals(answer.toLowerCase().trim());
	}

	public interface Callback {
		void correct();
		void incorrect();
	}
}
