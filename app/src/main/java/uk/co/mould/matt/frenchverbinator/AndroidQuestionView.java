package uk.co.mould.matt.frenchverbinator;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.ui.QuestionView;
import uk.co.mould.matt.data.Persons;

public final class AndroidQuestionView implements QuestionView {
    private static final String QUESTION_TEMPLATE = "What is the '%s' form of %s (%s) in the %s?";

    private ViewGroup questionViewGroup;
    private TextView answerBox;
    private View nextButton;
    private View submitButton;
    private TextView resultBox;
    private final TextView correctionBox;

    public AndroidQuestionView(ViewGroup questionViewGroup) {
        this.questionViewGroup = questionViewGroup;
        answerBox = ((TextView) questionViewGroup.findViewById(R.id.answerBox));
        submitButton = questionViewGroup.findViewById(R.id.submitButton);
        nextButton = questionViewGroup.findViewById(R.id.next);
        resultBox = ((TextView) questionViewGroup.findViewById(R.id.result_box));
        correctionBox = ((TextView) questionViewGroup.findViewById(R.id.correction_box));
    }

    @Override
    public void setQuestion(Persons.Person person, InfinitiveVerb verb, MoodAndTense moodAndTense) {
        TextView questionBox = (TextView) questionViewGroup.findViewById(R.id.question);
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
    public void showCorrection() {
        correctionBox.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCorrection() {
        correctionBox.setVisibility(View.GONE);
    }
}
