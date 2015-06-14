package uk.co.mould.matt;

import org.junit.Before;
import org.junit.Test;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.QuestionVerb;
import uk.co.mould.matt.fakes.FakeQuestionGenerator;
import uk.co.mould.matt.fakes.FakeQuestionView;
import uk.co.mould.matt.ui.QuestionPresenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class PresenterTest {

	private final Persons.Person person = Persons.FIRST_PERSON_PLURAL;
	private final String verbString = "regarder";
	private final QuestionVerb verb = new QuestionVerb("regarder", "to watch");
	private final String correctAnswer = "Vous regardez";
	private FakeQuestionView questionView;
	private QuestionPresenter questionPresenter;


	@Before
	public void setup() {
		questionView = new FakeQuestionView();
		questionPresenter = new QuestionPresenter(
				questionView,
				new FakeQuestionGenerator(person, verb),
				new FakeConjugator(person, verb, new ConjugatedVerbWithPronoun(correctAnswer)));
		questionPresenter.showQuestion();
	}
	@Test
	public void testThatQuestionViewIsSetCorrectly() {
		assertEquals(questionView.person, person);
		assertEquals(questionView.verb, verb);
        assertFalse(questionView.resultBoxVisible);

        assertTrue(questionView.submitButtonEnabled);
        assertTrue(questionView.submitButtonVisible);
	}

	@Test
	public void testThatCorrectAnswerSetsViewToCorrect() {
		questionView.answer = correctAnswer;
		questionPresenter.submitAnswer();

		assertTrue(questionView.resultBoxShowingCorrect);
		assertTrue(questionView.resultBoxVisible);

        assertFalse(questionView.answerBoxIsEnabled);

        assertFalse(questionView.correctionVisible);

		assertFalse(questionView.submitButtonVisible);
		assertFalse(questionView.submitButtonEnabled);

        assertTrue(questionView.nextQuestionButtonVisible);
        assertTrue(questionView.nextQuestionButtonEnabled);
    }

	@Test
	public void testThatIncorrectAnswerSetsViewToIncorrectAndShowsCorrectAnswer() {
		questionView.answer = "wrong answer";
		questionPresenter.submitAnswer();

		assertTrue(questionView.resultBoxShowingIncorrect);
        assertTrue(questionView.resultBoxVisible);

        assertEquals(correctAnswer, questionView.correctionValue);
		assertTrue(questionView.correctionVisible);
		assertFalse(questionView.answerBoxIsEnabled);

        assertFalse(questionView.submitButtonVisible);
        assertFalse(questionView.submitButtonEnabled);

        assertTrue(questionView.nextQuestionButtonVisible);
        assertTrue(questionView.nextQuestionButtonEnabled);
    }

	@Test
	public void testThatForQuestionAfterAnIncorrectAnswerCorrectionIsGone() {
		questionView.answer = "wrong answer";
		questionPresenter.submitAnswer();
		questionPresenter.showQuestion();

		assertFalse(questionView.correctionVisible);
	}

	@Test
	public void testThatWhenQuestionHasBeenRequestedUISwitchesToQuestionMode() {
		questionPresenter.showQuestion();
		questionView.answer = "some answer";
		questionPresenter.submitAnswer();
		questionPresenter.showQuestion();

		assertTrue(questionView.answerBoxIsEnabled);
		assertEquals("", questionView.answer);

		assertTrue(questionView.submitButtonVisible);
        assertTrue(questionView.submitButtonEnabled);

        assertFalse(questionView.nextQuestionButtonVisible);
        assertFalse(questionView.nextQuestionButtonEnabled);

    }

	private class FakeConjugator extends Conjugator {
		private Persons.Person personMatchingAnswer;
		private QuestionVerb verbMatchingAnswer;
		private ConjugatedVerbWithPronoun correctAnswer;


		public FakeConjugator(Persons.Person personMatchingAnswer, QuestionVerb verbMatchingAnswer, ConjugatedVerbWithPronoun correctAnswer) {
			super(null, null);
			this.personMatchingAnswer = personMatchingAnswer;
			this.verbMatchingAnswer = verbMatchingAnswer;
			this.correctAnswer = correctAnswer;
		}

		@Override
		public ConjugatedVerbWithPronoun getPresentConjugationOf(QuestionVerb infinitive, Persons.Person person) {
			if (personMatchingAnswer == person && verbMatchingAnswer == infinitive) {
				return correctAnswer;
			}
			return new ConjugatedVerbWithPronoun("gibberish");
		}
	}
}
