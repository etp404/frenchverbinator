package uk.co.mould.matt;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uk.co.mould.matt.data.*;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.fakes.FakeQuestionView;
import uk.co.mould.matt.frenchverbinator.QuestionPresenter;
import uk.co.mould.matt.marking.AnswerChecking;
import uk.co.mould.matt.marking.Score;
import uk.co.mould.matt.questions.Callback;
import uk.co.mould.matt.questions.Question;
import uk.co.mould.matt.questions.QuestionGenerator;
import uk.co.mould.matt.questions.RandomQuestionGenerator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public final class QuestionPresenterTest {

	private final Persons.Person person = Persons.FIRST_PERSON_PLURAL;
	private final InfinitiveVerb verb = new InfinitiveVerb("regarder", "to watch", null);
    private MoodAndTense verbMoodAndTense = new PresentIndicative();

	private final ConjugatedVerbWithPronoun correctAnswer = new ConjugatedVerbWithPronoun("correct answer");
    private final String wrongAnswer = "wrong answer";

	private final String correctAnswerWithTrailingSpace = "correct answer ";
	private FakeQuestionView questionView;
    private Question question;

    @Before
	public void setup() {
		questionView = new FakeQuestionView();
        question = new Question(person, verb, verbMoodAndTense);

        AnswerChecking answerChecker = new FakeAnswerChecker(
                new HashMap<Question, ConjugatedVerbWithPronoun>(){{
                    put(question, correctAnswer);
                }});

        QuestionGenerator fakeQuestionGenerator = new FakeQuestionGenerator(question);
        new QuestionPresenter(
				questionView,
                fakeQuestionGenerator,
                answerChecker);
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

        assertEquals(questionView.toldToShowIncorrectWithCorrection, correctAnswer);
        assertThat(questionView.updatedScore, is(score));
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
                new RandomQuestionGenerator(null, null, null, new ArrayList<MoodAndTense>()), null);
                questionPresenter.showQuestion();
        assertTrue(questionView.noTensesSelectedIsShown);
    }

    @Test
    public void testThatScoreIsSetToTwoOfTwoIfTwoCorrectAnswersGiven() {
        questionView.submitListener.submitAnswer(correctAnswer.toString());
        questionView.submitListener.submitAnswer(correctAnswer.toString());

        Score score = new Score();
        score.addCorrect();
        score.addCorrect();
        assertThat(questionView.updatedScore, is(score));
    }

    @Test
    public void testThatScoreIsSetToOneOfTwoIfOneCorrectAndTwoIncorrectAnswersGiven() {
        questionView.submitListener.submitAnswer("wrong answer");
        questionView.submitListener.submitAnswer("wrong answer");
        questionView.submitListener.submitAnswer(correctAnswer.toString());

        Score score = new Score();
        score.addCorrect();
        score.addIncorrect();
        score.addIncorrect();
        assertThat(questionView.updatedScore, is(score));
    }

    public class FakeQuestionGenerator implements QuestionGenerator {
        private Question question;

        public FakeQuestionGenerator(Question question) {
            this.question = question;
        }

        @Override
        public void getQuestion(Callback callback) {
            callback.questionProvided(question);
        }

        @Override
        public void repeatFailedQuestionAfter(Question failedQuestion, int repeatAfter) {

        }
    }

    public class FakeAnswerChecker implements AnswerChecking {

        private Question question;
        private Map<Question, ConjugatedVerbWithPronoun> questionToAnswer;

        public FakeAnswerChecker(Map<Question, ConjugatedVerbWithPronoun> questionToAnswer) {
            this.questionToAnswer = questionToAnswer;
        }

        @Override
        public void setQuestion(Question question) {
            this.question = question;
        }

        @Override
        public void check(String answer, Callback callback) {
            ConjugatedVerbWithPronoun rightAnswer = questionToAnswer.get(question);
            if (answer.equals(rightAnswer.toString())) {
                callback.correct();
            }
            else {
                callback.incorrect(rightAnswer);
            }
        }
    }

}
