package uk.co.mould.matt.frenchverbinator;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.marking.Score;
import uk.co.mould.matt.questions.Question;

public interface QuestionView {
	void setQuestion(Question question);

	void setResultToCorrect();

	void setResultToIncorrect(ConjugatedVerbWithPronoun presentConjugationOf);

    void showNoTensesSelected();

    void showScore(Score score);

    void addSubmitListener(SubmitListener submitListener);

    void addNextQuestionListener(NextQuestionListener nextQuestionListener);

    interface SubmitListener {

        void submitAnswer(String answer);
    }

    interface NextQuestionListener {

        void requestNextQuestion();
    }

}
