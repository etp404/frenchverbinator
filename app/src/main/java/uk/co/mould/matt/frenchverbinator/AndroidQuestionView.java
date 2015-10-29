package uk.co.mould.matt.frenchverbinator;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.marking.Score;
import uk.co.mould.matt.questions.Question;

public final class AndroidQuestionView extends FrameLayout implements QuestionView  {
    private static final String QUESTION_TEMPLATE = "What is the '%s' form of %s (%s) in the %s?";

    private View nextButton;
    private ImageView greenTick;
    private ImageView redCross;
    private Button submitButton;
    private TextView resultBox;
    private TextView noTensesSelectedWarning;
    private TextView scoreBox;
    private TextView correctionBox;
    private TextView questionBox;
    private TextView answerBox;

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
        correctionBox = (TextView) findViewById(R.id.correction_box);
        questionBox = (TextView)findViewById(R.id.question);
        greenTick = (ImageView)findViewById(R.id.green_tick);
        redCross = (ImageView) findViewById(R.id.red_cross);
    }

    @Override
    public void setQuestion(Question question) {
        noTensesSelectedWarning.setVisibility(View.GONE);
        correctionBox.setVisibility(View.GONE);
        resultBox.setVisibility(View.GONE);
        submitButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.GONE);
        answerBox.setVisibility(View.VISIBLE);
        answerBox.setEnabled(true);
        answerBox.setText("");
        redCross.setVisibility(GONE);
        redCross.setImageDrawable(null);

        greenTick.setVisibility(GONE);
        questionBox.setText(
                String.format(
                        QUESTION_TEMPLATE,
                        question.person.getPerson(),
                        question.verb.frenchVerb,
                        question.verb.englishVerb,
                        question.moodAndTense.toString()));
        clearAnimation();
    }

    @Override
    public void setResultToCorrect() {
        resultBox.setText("Correct");
        resultBox.setVisibility(VISIBLE);
        answerBox.setEnabled(false);
        correctionBox.setVisibility(GONE);
        nextButton.setVisibility(VISIBLE);
        nextButton.setEnabled(true);
        submitButton.setVisibility(GONE);
        showGreenTick();
    }

    private void showGreenTick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AnimatedVectorDrawable drawableGreenTick = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.drawable_green_tick);
            greenTick.setImageDrawable(drawableGreenTick);
            greenTick.setVisibility(VISIBLE);
            drawableGreenTick.start();
        }
        else {
            greenTick.setVisibility(VISIBLE);
        }
    }

    private void showRedCross() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AnimatedVectorDrawable drawableRedCross = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.drawable_red_cross);
            redCross.setImageDrawable(drawableRedCross);
            redCross.setVisibility(VISIBLE);
            drawableRedCross.start();
        }
        else {
            redCross.setVisibility(VISIBLE);
        }
    }

    @Override
    public void setResultToIncorrect(ConjugatedVerbWithPronoun correctAnswer) {
        answerBox.setEnabled(false);
        resultBox.setText("Incorrect");
        resultBox.setVisibility(VISIBLE);
        correctionBox.setText(correctAnswer.toString());
        correctionBox.setVisibility(VISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        nextButton.setEnabled(true);
        submitButton.setVisibility(View.GONE);
        greenTick.setVisibility(View.GONE);
        showRedCross();
    }

    @Override
    public void showNoTensesSelected() {
        questionBox.setVisibility(View.GONE);
        answerBox.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
        correctionBox.setVisibility(View.GONE);
        resultBox.setVisibility(View.GONE);
        noTensesSelectedWarning.setVisibility(View.VISIBLE);
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

}
