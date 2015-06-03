package uk.co.mould.matt;

import org.junit.Test;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.fakes.FakeQuestionGenerator;
import uk.co.mould.matt.fakes.FakeQuestionView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class PresenterTest {

	private final Persons.Person person = Persons.FIRST_PERSON_PLURAL;
	private final String verbString = "regarder";
	private final InfinitiveVerb verb = InfinitiveVerb.fromString(verbString);
	private final FakeQuestionView questionView = new FakeQuestionView();
	private final QuestionPresenter questionPresenter = new QuestionPresenter(questionView, new FakeQuestionGenerator(person, verb));

	@Test
	public void testThatQuestionFieldsAreFilled() {
		questionPresenter.showQuestion();

		assertEquals(questionView.person, "We");
		assertEquals(questionView.verb, verbString);
	}

	@Test
	public void testThatCorrectAnswerSetsViewToCorrect() {
		questionPresenter.submitAnswer("Vous regardez");

		assertTrue(questionView.correctCalled);
	}

}
