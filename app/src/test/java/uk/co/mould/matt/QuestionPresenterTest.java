package uk.co.mould.matt;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.*;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.fakes.FakeQuestionView;
import uk.co.mould.matt.frenchverbinator.QuestionPresenter;
import uk.co.mould.matt.marking.Score;
import uk.co.mould.matt.questions.Question;
import uk.co.mould.matt.questions.QuestionGenerator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public final class QuestionPresenterTest {

	private final Persons.Person person = Persons.FIRST_PERSON_PLURAL;
	private final InfinitiveVerb verb = new InfinitiveVerb("regarder", "to watch", null);
    private MoodAndTense verbMoodAndTense = new PresentIndicative();

	private final String correctAnswer = "correct answer";
    private final String wrongAnswer = "wrong answer";

	private final String correctAnswerWithTrailingSpace = "correct answer ";
	private FakeQuestionView questionView;
    private Question question;
    private QuestionGenerator questionGenerator;

    @Before
	public void setup() {
		questionView = new FakeQuestionView();
        question = new Question(person, verb, verbMoodAndTense);
        questionGenerator = new QuestionGenerator(new FakeRandomQuestionGenerator(),
                Collections.singletonList(verb),
                Collections.singletonList(person),
                Collections.singletonList(verbMoodAndTense));
        new QuestionPresenter(
				questionView,
                questionGenerator,
				new FakeConjugator(person, verb, verbMoodAndTense, new ConjugatedVerbWithPronoun(correctAnswer)));
	}
	@Test
	public void testThatViewCanBeToldToShowAQuestion() {
		assertEquals(questionView.setQuestionCalledWithQuestion, question);
        assertThat(questionView.updatedScore, is(new Score()));
    }

	@Test
	public void testThatViewCanBeToldToShowWhenAnswerIsIncorrectAnswer() {
        questionView.submitListener.submitAnswer(wrongAnswer);
        Score score = new Score();
        score.addIncorrect();

        assertEquals(questionView.toldToShowIncorrectWithCorrection.toString(), correctAnswer);
        assertThat(questionView.updatedScore, is(score));
    }

	@Test
	public void testThatCorrectAnswerWithTrailingSpaceSetsViewToCorrect() {
        questionView.submitListener.submitAnswer(correctAnswerWithTrailingSpace);
		assertTrue(questionView.toldToShowCorrectAnswer);
	}

	@Test
	public void testThatQuestionIsShownInResponseToNextQuestion() {
        questionView.nextQuestionListener.requestNextQuestion();
        assertEquals(questionView.setQuestionCalledWithQuestion, question);
    }

    @Test
    public void testThatNoTensesSelectedWarningIsShownIfNoTensesAreSelected() {
        QuestionPresenter questionPresenter = new QuestionPresenter(
                questionView,
                new QuestionGenerator(null, null, null, new ArrayList<MoodAndTense>()),
                new FakeConjugator(person, verb, verbMoodAndTense, new ConjugatedVerbWithPronoun(correctAnswer)));
        questionPresenter.showQuestion();
        assertTrue(questionView.noTensesSelectedIsShown);
    }

    @Test
    public void testThatScoreIsSetToTwoOfTwoIfTwoCorrectAnswersGiven() {
        questionView.submitListener.submitAnswer(correctAnswer);
        questionView.submitListener.submitAnswer(correctAnswer);

        Score score = new Score();
        score.addCorrect();
        score.addCorrect();
        assertThat(questionView.updatedScore, is(score));
    }

    @Test
    public void testThatScoreIsSetToOneOfTwoIfOneCorrectAndTwoIncorrectAnswersGiven() {
        questionView.submitListener.submitAnswer("wrong answer");
        questionView.submitListener.submitAnswer("wrong answer");
        questionView.submitListener.submitAnswer(correctAnswer);

        Score score = new Score();
        score.addCorrect();
        score.addIncorrect();
        score.addIncorrect();
        assertThat(questionView.updatedScore, is(score));
    }

    private class FakeConjugator extends Conjugator {
		private Persons.Person personMatchingAnswer;
		private InfinitiveVerb verbMatchingAnswer;
        private MoodAndTense verbMoodAndTestMatchingAnswer;
        private ConjugatedVerbWithPronoun correctAnswer;


        public FakeConjugator(Persons.Person personMatchingAnswer,
                              InfinitiveVerb verbMatchingAnswer,
                              MoodAndTense verbMoodAndTestMatchingAnswer,
                              ConjugatedVerbWithPronoun correctAnswer) {
			super(null, null);
			this.personMatchingAnswer = personMatchingAnswer;
			this.verbMatchingAnswer = verbMatchingAnswer;
            this.verbMoodAndTestMatchingAnswer = verbMoodAndTestMatchingAnswer;
			this.correctAnswer = correctAnswer;
		}

		@Override
		public ConjugatedVerbWithPronoun getConjugationOf(InfinitiveVerb infinitive, Persons.Person person, MoodAndTense verbMoodAndTense) {
			if (personMatchingAnswer == person && verbMatchingAnswer == infinitive && verbMoodAndTestMatchingAnswer == verbMoodAndTense) {
				return correctAnswer;
			}
			return null;
		}
	}

}
