package uk.co.mould.matt.frenchverbinator;

import org.junit.Test;

import uk.co.mould.matt.QuestionGenerator;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;

import static org.junit.Assert.assertEquals;

public final class UITest {

	@Test
	public void testThatQuestionFieldsAreFilled() {
		Persons.Person person = Persons.FIRST_PERSON_PLURAL;
		String verbString = "regarder";

		InfinitiveVerb verb = InfinitiveVerb.fromString(verbString);

		FakeQuestionView questionView = new FakeQuestionView();
		QuestionPresenter questionPresenter = new QuestionPresenter(questionView, new FakeQuestionGenerator(person, verb));

		questionPresenter.showQuestion();

		assertEquals(questionView.person, "We");
		assertEquals(questionView.verb, verbString);
	}

	private class FakeQuestionView {
		public String person;
		public String verb;

		public void setPerson(Persons.Person randomPerson) {
			person = randomPerson.getEnglishPronoun();
		}

		public void setVerb(InfinitiveVerb randomVerb) {
			verb = randomVerb.toString();
		}
	}

	private class QuestionPresenter {
		private FakeQuestionView questionView;
		private FakeQuestionGenerator fakeQuestionGenerator;

		public QuestionPresenter(FakeQuestionView questionView, FakeQuestionGenerator fakeQuestionGenerator) {
			this.questionView = questionView;
			this.fakeQuestionGenerator = fakeQuestionGenerator;
		}

		public void showQuestion() {
			questionView.setPerson(fakeQuestionGenerator.getRandomPerson());
			questionView.setVerb(fakeQuestionGenerator.getRandomVerb());

		}
	}

	private class FakeQuestionGenerator implements QuestionGenerator {
		private Persons.Person person;
		private InfinitiveVerb verb;

		public FakeQuestionGenerator() {
			super();
		}

		public FakeQuestionGenerator(Persons.Person person, InfinitiveVerb verb) {
			this.person = person;
			this.verb = verb;
		}

		@Override
		public Persons.Person getRandomPerson() {
			return person;
		}

		@Override
		public InfinitiveVerb getRandomVerb() {
			return verb;
		}
	}
}
