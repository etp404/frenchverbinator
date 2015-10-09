package uk.co.mould.matt.frenchverbinator;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.exceptions.CantConjugateException;
import uk.co.mould.matt.marking.AnswerChecker;
import uk.co.mould.matt.marking.Score;
import uk.co.mould.matt.questions.Callback;
import uk.co.mould.matt.questions.Question;
import uk.co.mould.matt.questions.QuestionGenerator;

public class QuestionPresenter {
	private final Conjugator conjugator;
	private AnswerChecker answerChecker;
	private QuestionView questionView;
    private Score score = new Score();
	private QuestionGenerator questionGenerator;

    public QuestionPresenter(final QuestionView questionView,
							 QuestionGenerator questionGenerator,
							 Conjugator conjugator) {
		this.questionView = questionView;
		this.questionGenerator = questionGenerator;
		this.conjugator = conjugator;
		this.answerChecker = new AnswerChecker(conjugator);
        questionView.addSubmitListener(new QuestionView.SubmitListener() {
            @Override
            public void submitAnswer(String answer) {
                answerChecker.check(answer, new AnswerChecker.Callback() {
                    @Override
                    public void correct() {
                        score.addCorrect();
                        questionView.setResultToCorrect();
                    }

                    @Override
                    public void incorrect(ConjugatedVerbWithPronoun corrrection) {
                        score.addIncorrect();
                        questionView.setResultToIncorrect(corrrection);
                    }
                });
            }
        });

        questionView.addNextQuestionListener(new QuestionView.NextQuestionListener() {
            @Override
            public void requestNextQuestion() {
                showQuestion();
            }
        });

        showQuestion();
    }

	public void showQuestion() {
        questionGenerator.getQuestion(new Callback() {
            @Override
            public void questionProvided(Question question) {
                questionView.showScore(score);
                questionView.setQuestion(question);
                QuestionPresenter.this.answerChecker.setQuestion(question);
            }

            @Override
            public void noTensesSelected() {
                QuestionPresenter.this.questionView.showNoTensesSelected();
            }
        });


	}
}
