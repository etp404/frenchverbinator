package uk.co.mould.matt.frenchverbinator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.marking.Score;

public final class AndroidQuestionView extends FrameLayout implements QuestionView  {
    private static final String QUESTION_TEMPLATE = "What is the '%s' form of %s (%s) in the %s?";

    private TextView answerBox;
    private View nextButton;
    private Button submitButton;
    private TextView resultBox;
    private TextView noTensesSelectedWarning;
    private TextView scoreBox;

    public AndroidQuestionView(Context context) {
        super(context);
    }

    public AndroidQuestionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AndroidQuestionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        answerBox = ((TextView) findViewById(R.id.answer_box));
        submitButton = (Button)findViewById(R.id.submit_button);
        nextButton = findViewById(R.id.next_button);
        resultBox = ((TextView) findViewById(R.id.result_box));
        noTensesSelectedWarning = (TextView) findViewById(R.id.no_tenses_selected);
    }

    @Override
    public void setQuestion(Persons.Person person, InfinitiveVerb verb, MoodAndTense moodAndTense) {
        hideNoTensesSelected();
        hideCorrection();
        showSubmitButton();
        hideNextQuestionButton();
        showAnswerBox();
        enableAnswerBox();
        ((TextView)findViewById(R.id.question)).setText(
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

        findViewById(R.id.correction_box).setVisibility(View.GONE);

        nextButton.setVisibility(View.VISIBLE);
        nextButton.setEnabled(true);

        submitButton.setVisibility(View.GONE);
    }

    @Override
    public void setResultToIncorrect() {
        resultBox.setText("Incorrect");
    }

    @Override
    public void setResultToIncorrect(ConjugatedVerbWithPronoun correctAnswer) {
        answerBox.setEnabled(false);
        setResultToIncorrect();
        setCorrection(correctAnswer);
        nextButton.setVisibility(View.VISIBLE);
        nextButton.setEnabled(true);

        submitButton.setVisibility(View.GONE);
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
        ((TextView)findViewById(R.id.correction_box)).setText(presentConjugationOf.toString());
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
        findViewById(R.id.question).setVisibility(View.GONE);
    }

    @Override
    public void showNoTensesSelected() {
        hideQuestionBox();
        hideAnswerBox();
        hideSubmitButton();
        hideNextQuestionButton();
        hideCorrection();
        hideResultBox();
        noTensesSelectedWarning.setVisibility(View.VISIBLE);
    }

    @Override
    public void showQuestionBox() {
        findViewById(R.id.question).setVisibility(View.VISIBLE);
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
        scoreBox = (TextView) findViewById(R.id.score);
        scoreBox.setVisibility(VISIBLE);
        scoreBox.setText(score.toString());
    }

    @Override
    public void addSubmitListener(final SubmitListener submitListener) {
        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                submitListener.submitAnswer(answerBox.getText().toString());
            }
        });
    }

    @Override
    public void addNextQuestionListener(final NextQuestionListener nextQuestionListener) {
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestionListener.requestNextQuestion();
            }
        });
    }

    @Override
    public void showCorrection() {
        findViewById(R.id.correction_box).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCorrection() {
        findViewById(R.id.correction_box).setVisibility(View.GONE);
    }
}
