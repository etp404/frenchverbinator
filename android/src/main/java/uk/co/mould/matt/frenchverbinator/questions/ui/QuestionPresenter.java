package uk.co.mould.matt.frenchverbinator.questions.ui;

import uk.co.mould.matt.CantConjugateException;
import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.marking.AnswerChecker;
import uk.co.mould.matt.questions.QuestionGenerator;

public class QuestionPresenter {
	private final Conjugator conjugator;
	private AnswerChecker answerChecker;
	private QuestionView questionView;
	private QuestionGenerator fakeQuestionGenerator;
    private InfinitiveVerb infinitiveVerb;
    private Persons.Person questionPerson;
    private MoodAndTense verbMoodAndTense;

    public QuestionPresenter(QuestionView questionView,
							 QuestionGenerator questionGenerator,
							 Conjugator conjugator) {
		this.questionView = questionView;
		this.fakeQuestionGenerator = questionGenerator;
		this.conjugator = conjugator;
		this.answerChecker = new AnswerChecker(conjugator);
	}

	public void showQuestion() {
        questionView.hideNoTensesSelected();

		questionPerson = fakeQuestionGenerator.getRandomPerson();
		infinitiveVerb = fakeQuestionGenerator.getRandomVerb();
        verbMoodAndTense = fakeQuestionGenerator.getRandomVerbMoodAndTense();
		questionView.setQuestion(questionPerson, infinitiveVerb, verbMoodAndTense);
        questionView.showQuestionBox();
		questionView.hideCorrection();

        questionView.showAnswerBox();
		questionView.enableAnswerBox();
		questionView.clearAnswerBox();

		questionView.showSubmitButton();
		questionView.enableSubmitButton();

		questionView.hideNextQuestionButton();
		questionView.disableNextQuestionButton();

		questionView.hideResultBox();

		this.answerChecker.setQuestion(questionPerson, infinitiveVerb, verbMoodAndTense);
	}

	public void submitAnswer() {
		answerChecker.check(questionView.getAnswer(), new AnswerChecker.Callback() {
            @Override
            public void correct() {
                questionView.setResultToCorrect();
            }

            @Override
            public void incorrect() {
                questionView.setResultToIncorrect();
                questionView.showCorrection();
                try {
                    questionView.setCorrection(conjugator.getConjugationOf(
                            infinitiveVerb,
                            questionPerson,
                            verbMoodAndTense));
                } catch (CantConjugateException ignored) {

                }
            }
        });
		questionView.disableAnswerBox();
		questionView.showResultBox();

		questionView.hideSubmitButton();
		questionView.disableSubmitButton();

		questionView.showNextQuestionButton();
		questionView.enableNextQuestionButton();
	}

    public void showNoneSelectedWarning() {
        questionView.hideAnswerBox();
        questionView.hideQuestionBox();
        questionView.hideNextQuestionButton();
        questionView.hideSubmitButton();
        questionView.hideResultBox();
        questionView.hideCorrection();

        questionView.showNoTensesSelected();
    }
}
