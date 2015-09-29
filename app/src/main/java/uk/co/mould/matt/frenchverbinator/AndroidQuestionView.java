package uk.co.mould.matt.frenchverbinator;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.marking.Score;

public final class AndroidQuestionView implements QuestionView {
    private static final String QUESTION_TEMPLATE = "What is the '%s' form of %s (%s) in the %s?";

    private TextView answerBox;
    private View nextButton;
    private View submitButton;
    private TextView resultBox;
    private final TextView correctionBox;
    private final TextView questionBox;
    private TextView noTensesSelectedWarning;
    private final TextView scoreBox;

    public AndroidQuestionView(ViewGroup questionViewGroup) {
        answerBox = ((TextView) questionViewGroup.findViewById(R.id.answer_box));
        submitButton = questionViewGroup.findViewById(R.id.submit_button);
        nextButton = questionViewGroup.findViewById(R.id.next_button);
        resultBox = ((TextView) questionViewGroup.findViewById(R.id.result_box));
        correctionBox = ((TextView) questionViewGroup.findViewById(R.id.correction_box));
        questionBox = (TextView) questionViewGroup.findViewById(R.id.question);
        noTensesSelectedWarning = (TextView) questionViewGroup.findViewById(R.id.no_tenses_selected);
        scoreBox = (TextView) questionViewGroup.findViewById(R.id.score);
    }

    @Override
    public void setQuestion(Persons.Person person, InfinitiveVerb verb, MoodAndTense moodAndTense) {
        questionBox.setText(
                String.format(
                        QUESTION_TEMPLATE,
                        person.getPerson(),
                        verb.frenchVerb,
                        verb.englishVerb,
                        moodAndTense.toString()));
    }

    @Override
    public void setResultToCorrect() {
        resultBox.setText("Correct");
        answerBox.setEnabled(false);

        correctionBox.setVisibility(View.GONE);

        nextButton.setVisibility(View.VISIBLE);
        nextButton.setEnabled(true);

        submitButton.setVisibility(View.GONE);
    }

    @Override
    public void setResultToIncorrect() {
        resultBox.setText("Incorrect");
    }

    @Override
    public String getAnswer() {
        return answerBox.getText().toString();
    }

    @Override
    public void enableAnswerBox() {
        answerBox.setEnabled(true);
    }

    @Override
    public void showResultBox() {
        resultBox.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSubmitButton() {
        submitButton.setVisibility(View.GONE);
    }

    @Override
    public void enableSubmitButton() {
        submitButton.setEnabled(true);
    }

    @Override
    public void disableSubmitButton() {
        submitButton.setEnabled(false);
    }

    @Override
    public void setCorrection(ConjugatedVerbWithPronoun presentConjugationOf) {
        correctionBox.setText(presentConjugationOf.toString());
    }

    @Override
    public void disableAnswerBox() {
        answerBox.setEnabled(false);
    }

    @Override
    public void showNextQuestionButton() {
        nextButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void enableNextQuestionButton() {
        nextButton.setEnabled(true);
    }

    @Override
    public void disableNextQuestionButton() {
        nextButton.setEnabled(false);
    }

    @Override
    public void showSubmitButton() {
        submitButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNextQuestionButton() {
        nextButton.setVisibility(View.GONE);
    }

    @Override
    public void hideResultBox() {
        resultBox.setVisibility(View.GONE);
    }

    @Override
    public void clearAnswerBox() {
        answerBox.setText("");
    }

    @Override
    public void hideAnswerBox() {
        answerBox.setVisibility(View.GONE);
    }

    @Override
    public void hideQuestionBox() {
        questionBox.setVisibility(View.GONE);
    }

    @Override
    public void showNoTensesSelected() {
        noTensesSelectedWarning.setVisibility(View.VISIBLE);
    }

    @Override
    public void showQuestionBox() {
        questionBox.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAnswerBox() {
        answerBox.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoTensesSelected() {
        noTensesSelectedWarning.setVisibility(View.GONE);
    }

    @Override
    public void showScore(Score score) {
        scoreBox.setText(score.toString());
    }

    @Override
    public void showCorrection() {
        correctionBox.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCorrection() {
        correctionBox.setVisibility(View.GONE);
    }
}
