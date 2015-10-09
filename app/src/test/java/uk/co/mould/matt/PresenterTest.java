package uk.co.mould.matt;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.*;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.fakes.FakeQuestionGenerator;
import uk.co.mould.matt.fakes.FakeQuestionView;
import uk.co.mould.matt.frenchverbinator.QuestionPresenter;
import uk.co.mould.matt.marking.Score;
import uk.co.mould.matt.questions.QuestionGenerator;
import uk.co.mould.matt.questions.RandomQuestionGenerator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public final class PresenterTest {

	private final Persons.Person person = Persons.FIRST_PERSON_PLURAL;
	private final InfinitiveVerb verb = new InfinitiveVerb("regarder", "to watch", null);
    private MoodAndTense verbMoodAndTense = new PresentIndicative();

	private final String correctAnswer = "correct answer";
    private final String wrongAnswer = "wrong answer";

	private final String correctAnswerWithTrailingSpace = "correct answer ";
	private FakeQuestionView questionView;
	private QuestionPresenter questionPresenter;
    ;

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
        assertThat(questionView.hasBeenToldToShowScore, is(new Score()));
    }

	@Test
	public void testThatViewCanBeToldToShowWhenAnswerIsIncorrectAnswer() {
        questionView.submitListener.submitAnswer(wrongAnswer);
        Score score = new Score();
        score.addIncorrect();

        assertEquals(questionView.toldToShowIncorrectWithCorrection.toString(), correctAnswer);
        assertThat(questionView.hasBeenToldToShowScore, is(score));
    }

	@Test
	public void testThatCorrectAnswerWithTrailingSpaceSetsViewToCorrect() {
        questionView.submitListener.submitAnswer(correctAnswerWithTrailingSpace);
		assertTrue(questionView.toldToShowCorrectAnswer);
	}

	@Test
	public void testThatQuestionIsShownInResponseToNextQuestion() {
        questionView.nextQuestionListener.requestNextQuestion();
        assertTrue(questionView.setQuestionCalled);
    }

    @Test
    public void testThatNoTensesSelectedWarningIsShownIfNoTensesAreSelected() {
        QuestionPresenter questionPresenter = new QuestionPresenter(
                questionView,
                new RandomQuestionGenerator(null, new ArrayList<MoodAndTense>()),
                new FakeConjugator(person, verb, new ConjugatedVerbWithPronoun(correctAnswer)));
        questionPresenter.showQuestion();
        assertTrue(questionView.noTensesSelectedIsShown);
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
			return correctAnswer;
		}
	}
}
