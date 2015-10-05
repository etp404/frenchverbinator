package uk.co.mould.matt;

import org.junit.Before;
import org.junit.Test;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.*;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.fakes.FakeQuestionGenerator;
import uk.co.mould.matt.fakes.FakeQuestionView;
import uk.co.mould.matt.frenchverbinator.QuestionPresenter;
import uk.co.mould.matt.marking.Score;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public final class PresenterTest {

	private final Persons.Person person = Persons.FIRST_PERSON_PLURAL;
	private final InfinitiveVerb verb = new InfinitiveVerb("regarder", "to watch", null);
    private MoodAndTense verbMoodAndTense = new PresentIndicative();

	private final String correctAnswer = "Vous regardez";
	private final String correctAnswerWithTrailingSpace = "Vous regardez ";
	private FakeQuestionView questionView;
	private QuestionPresenter questionPresenter;

    @Before
	public void setup() {
		questionView = new FakeQuestionView();
        questionPresenter = new QuestionPresenter(
				questionView,
				new FakeQuestionGenerator(verb, person, verbMoodAndTense),
				new FakeConjugator(person, verb, new ConjugatedVerbWithPronoun(correctAnswer)));
		questionPresenter.showQuestion();
	}
	@Test
	public void testThatViewCanBeToldToShowAQuestion() {
		assertEquals(questionView.setQuestionCalledWithPerson, person);
		assertEquals(questionView.setQuestionCalledWithVerb, verb);
		assertEquals(questionView.setQuestionCalledWithVerbMoodAndTense, verbMoodAndTense);
	}

	@Test
	public void testThatViewCanBeToldToShowCorrectAnswer() {
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
	public void testThatCorrectAnswerWithTrailingSpaceSetsViewToCorrect() {
		questionView.answer = correctAnswerWithTrailingSpace;
		questionPresenter.submitAnswer();

		assertTrue(questionView.resultBoxShowingCorrect);
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

    @Test
    public void testThatNoTensesSelectedWarningCanBeIsGiven() {
        questionPresenter.showNoneSelectedWarning();
        assertFalse(questionView.answerBoxIsVisible);
        assertFalse(questionView.questionBoxIsVisible);
        assertFalse(questionView.submitButtonVisible);
        assertFalse(questionView.nextQuestionButtonVisible);
        assertFalse(questionView.correctionVisible);
        assertFalse(questionView.resultBoxVisible);

        assertTrue(questionView.noTensesSelectedIsShown);
    }

    @Test
    public void testThatLayoutIsReinstatedAfterNoTensesSelectedWarning() {
        questionPresenter.showNoneSelectedWarning();
        questionPresenter.showQuestion();
        assertTrue(questionView.answerBoxIsVisible);
        assertTrue(questionView.questionBoxIsVisible);
        assertTrue(questionView.submitButtonVisible);
        assertFalse(questionView.nextQuestionButtonVisible);
        assertFalse(questionView.correctionVisible);
        assertFalse(questionView.resultBoxVisible);

        assertFalse(questionView.noTensesSelectedIsShown);
    }

    @Test
    public void testThatScoreIsSetToZeroOfZeroAtStart() {
        assertThat(questionView.hasBeenToldToShowScore, is(new Score()));
    }

    @Test
    public void testThatScoreIsSetToOneOfOneIfCorrectAnswerGiven() {
        questionView.answer = correctAnswer;
        questionPresenter.submitAnswer();
        Score score = new Score();
        score.addCorrect();
        assertThat(questionView.hasBeenToldToShowScore, is(score));
    }

    @Test
    public void testThatScoreIsSetToTwoOfTwoIfTwoCorrectAnswersGiven() {
        questionView.answer = correctAnswer;
        questionPresenter.submitAnswer();
        questionView.answer = correctAnswer;
        questionPresenter.submitAnswer();

        Score score = new Score();
        score.addCorrect();
        score.addCorrect();
        assertThat(questionView.hasBeenToldToShowScore, is(score));
    }

    @Test
    public void testThatScoreIsSetToOneOfTwoIfOneCorrectAndTwoIncorrectAnswersGiven() {
        questionView.answer = "wrong answer";
        questionPresenter.submitAnswer();
        questionView.answer = "wrong answer";
        questionPresenter.submitAnswer();
        questionView.answer = correctAnswer;
        questionPresenter.submitAnswer();

        Score score = new Score();
        score.addCorrect();
        score.addIncorrect();
        score.addIncorrect();
        assertThat(questionView.hasBeenToldToShowScore, is(score));
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
		public ConjugatedVerbWithPronoun getConjugationOf(InfinitiveVerb infinitive, Persons.Person person, MoodAndTense verbMoodAndTense) {
			if (personMatchingAnswer == person && verbMatchingAnswer == infinitive) {
				return correctAnswer;
			}
			return new ConjugatedVerbWithPronoun("gibberish");
		}
	}
}
