package uk.co.mould.matt.frenchverbinator;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.marking.AnswerChecking;
import uk.co.mould.matt.marking.Score;
import uk.co.mould.matt.questions.Callback;
import uk.co.mould.matt.questions.Question;
import uk.co.mould.matt.questions.QuestionGenerator;

public class QuestionPresenter {
	private QuestionView questionView;
    private Score score = new Score();
	private QuestionGenerator randomQuestionGenerator;
    private Question question;

    public QuestionPresenter(final QuestionView questionView,
							 QuestionGenerator randomQuestionGenerator,
                             final AnswerChecking answerChecking) {
		this.questionView = questionView;
		this.randomQuestionGenerator = randomQuestionGenerator;
        questionView.addSubmitListener(new QuestionView.SubmitListener() {
            @Override
            public void submitAnswer(String answer) {
                                        questionView.setResultToCorrect();
//                answerChecking.check(question, answer, new AnswerChecking.Callback() {
//                    @Override
//                    public void correct() {
//                        score.addCorrect();
//                        questionView.setResultToCorrect();
//                    }
//
//                    @Override
//                    public void incorrect(ConjugatedVerbWithPronoun corrrection) {
//                        score.addIncorrect();
//                        questionView.setResultToIncorrect(corrrection);
//                    }
//                });
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
        randomQuestionGenerator.getQuestion(new Callback() {
            @Override
            public void questionProvided(Question question) {
                questionView.showScore(score);
                questionView.setQuestion(question);
                QuestionPresenter.this.question = question;
            }

            @Override
            public void noTensesSelected() {
                QuestionPresenter.this.questionView.showNoTensesSelected();
            }
        });


	}
}
