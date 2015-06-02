package uk.co.mould.matt;

import org.junit.Test;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.fakes.FakeQuestionGenerator;
import uk.co.mould.matt.fakes.FakeQuestionView;

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

}
