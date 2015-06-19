package uk.co.mould.matt.ui;

import uk.co.mould.matt.CantConjugateException;
import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.VerbMoodsAndTenses;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.marking.AnswerChecker;
import uk.co.mould.matt.questions.QuestionGenerator;

public class QuestionPresenter {
	private final Conjugator conjugator;
	private AnswerChecker answerChecker;
	private QuestionView questionView;
	private QuestionGenerator fakeQuestionGenerator;
    private InfinitiveVerb infinitiveVerb;
    private Persons.Person questionPerson;
    private VerbMoodsAndTenses.VerbMoodAndTense verbMoodAndTense;

    public QuestionPresenter(QuestionView questionView,
							 QuestionGenerator questionGenerator,
							 Conjugator conjugator) {
		this.questionView = questionView;
		this.fakeQuestionGenerator = questionGenerator;
		this.conjugator = conjugator;
		this.answerChecker = new AnswerChecker(conjugator);
	}

	public void showQuestion() {
		questionPerson = fakeQuestionGenerator.getRandomPerson();
		infinitiveVerb = fakeQuestionGenerator.getRandomVerb();
        verbMoodAndTense = fakeQuestionGenerator.getRandomVerbMoodAndTense();
		questionView.setQuestion(questionPerson, infinitiveVerb, verbMoodAndTense);
		questionView.hideCorrection();

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
					questionView.setCorrection(conjugator.getPresentConjugationOf(
                            infinitiveVerb,
                            questionPerson,
                            new PresentIndicative()));
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

}
