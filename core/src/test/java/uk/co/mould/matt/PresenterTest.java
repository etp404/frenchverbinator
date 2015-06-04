package uk.co.mould.matt;

import org.junit.Before;
import org.junit.Test;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.fakes.FakeQuestionGenerator;
import uk.co.mould.matt.fakes.FakeQuestionView;
import uk.co.mould.matt.ui.QuestionPresenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class PresenterTest {

	private final Persons.Person person = Persons.FIRST_PERSON_PLURAL;
	private final String verbString = "regarder";
	private final InfinitiveVerb verb = InfinitiveVerb.fromString(verbString);
	private final String correctAnswer = "Vous regardez";
	private FakeQuestionView questionView;
	private QuestionPresenter questionPresenter;


	@Before
	public void setup() {
		questionView = new FakeQuestionView();
		questionPresenter = new uk.co.mould.matt.ui.QuestionPresenter(
				questionView,
				new FakeQuestionGenerator(person, verb),
				new FakeConjugator(person, verb, new ConjugatedVerbWithPronoun(correctAnswer)));
		questionPresenter.showQuestion();
	}
	@Test
	public void testThatQuestionFieldsAreFilled() {
		assertEquals(questionView.person, "We");
		assertEquals(questionView.verb, verbString);
	}

	@Test
	public void testThatCorrectAnswerSetsViewToCorrect() {
		questionView.answer = correctAnswer;
		questionPresenter.submitAnswer();

		assertTrue(questionView.showingAnswerAsCorrect);
		assertTrue(questionView.showingResultBox);
		assertFalse(questionView.correctionVisible);
	}

	@Test
	public void testThatIncorrectAnswerSetsViewToIncorrectAndShowsCorrectAnswer() {
		questionView.answer = "wrong answer";
		questionPresenter.submitAnswer();

		assertTrue(questionView.showingAnswerAsIncorrect);
		assertEquals(correctAnswer, questionView.correctAnswerValue);
		assertTrue(questionView.correctionVisible);
		assertTrue(questionView.answerBoxIsEnabled);
	}

	@Test
	public void testThatForQuestionAfterAnIncorrectAnswerCorrectionIsGone() {
		questionView.answer = "wrong answer";
		questionPresenter.submitAnswer();
		questionPresenter.showQuestion();

		assertFalse(questionView.correctionVisible);
	}

	@Test
	public void testThatWhenAnswerHasBeenSubmittedUISwitchesToAnswerMode() {
		questionPresenter.submitAnswer();

		assertTrue(questionView.inAnswerMode);
		assertTrue(questionView.correctionVisible);
	}

	@Test
	public void testThatWhenQuestionHasBeenRequestedUISwitchesToQuestionMode() {
		questionPresenter.showQuestion();
		questionPresenter.submitAnswer();
		questionPresenter.showQuestion();

		assertTrue(questionView.inQuestionMode);
	}

	private class FakeConjugator extends Conjugator {

		private Persons.Person personMatchingAnswer;
		private InfinitiveVerb verbMatchingAnswer;
		private ConjugatedVerbWithPronoun correctAnswer;


		public FakeConjugator(Persons.Person personMatchingAnswer, InfinitiveVerb verbMatchingAnswer, ConjugatedVerbWithPronoun correctAnswer) {
			super(null, null);
			this.personMatchingAnswer = personMatchingAnswer;
			this.verbMatchingAnswer = verbMatchingAnswer;
			this.correctAnswer = correctAnswer;
		}

		@Override
		public ConjugatedVerbWithPronoun getPresentConjugationOf(InfinitiveVerb infinitive, Persons.Person person) {
			if (personMatchingAnswer == person && verbMatchingAnswer == infinitive) {
				return correctAnswer;
			}
			return new ConjugatedVerbWithPronoun("gibberish");
		}
	}
}
