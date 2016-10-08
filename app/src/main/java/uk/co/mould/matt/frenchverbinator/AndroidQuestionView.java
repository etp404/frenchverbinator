package uk.co.mould.matt.frenchverbinator;

import android.content.Context;
import android.databinding.ObservableField;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.frenchverbinator.databinding.AnswerBox;
import uk.co.mould.matt.frenchverbinator.databinding.ResultBox;
import uk.co.mould.matt.marking.Score;
import uk.co.mould.matt.questions.Question;

public final class AndroidQuestionView extends LinearLayout implements QuestionView  {
    private static final String QUESTION_TEMPLATE = "What is the '%s' form of %s (%s) in the %s?";

    public final ObservableField<String> question = new ObservableField<>();
    public final ResultBox resultBox = new ResultBox();
    public final AnswerBox answerBox = new AnswerBox();

    private View nextButton;
    private ImageView greenTick;
    private ImageView redCross;
    private Button submitButton;
    private TextView resultBoxLegacy;
    private TextView noTensesSelectedWarning;
    private TextView scoreBox;
    private TextView correctionBox;
    private TextView questionBox;
    private SubmitListener submitListener;

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
        submitButton = (Button)findViewById(R.id.submit_button);
        nextButton = findViewById(R.id.next_button);
        resultBoxLegacy = ((TextView) findViewById(R.id.result_box));
        noTensesSelectedWarning = (TextView) findViewById(R.id.no_tenses_selected);
        correctionBox = (TextView) findViewById(R.id.correction_box);
        questionBox = (TextView)findViewById(R.id.question);
        greenTick = (ImageView)findViewById(R.id.green_tick);
        redCross = (ImageView) findViewById(R.id.red_cross);
        scoreBox = (TextView) findViewById(R.id.score);
    }

    @Override
    public void setQuestion(Question question) {
        noTensesSelectedWarning.setVisibility(View.GONE);
        correctionBox.setVisibility(View.GONE);
        resultBoxLegacy.setVisibility(View.GONE);
        submitButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.GONE);
        answerBox.setVisibility(true);
        answerBox.setEnabled(true);
        answerBox.setText("");
        redCross.setVisibility(GONE);

        greenTick.setVisibility(GONE);
        this.question.set(
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
        resultBoxLegacy.setVisibility(VISIBLE);
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
            showTickOrCross(greenTick);
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
            showTickOrCross(redCross);
        }
    }

    private void showTickOrCross(final View tickOrCross) {
        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.abc_popup_enter);
        fadeIn.setDuration(500);
        Animation fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.abc_popup_exit);
        fadeOut.setDuration(500);
        fadeOut.setStartOffset(700);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tickOrCross.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tickOrCross.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        AnimationSet s = new AnimationSet(false);
        s.addAnimation(fadeIn);
        s.addAnimation(fadeOut);
        s.setFillAfter(true);
        tickOrCross.startAnimation(s);
    }

    @Override
    public void setResultToIncorrect(ConjugatedVerbWithPronoun correctAnswer) {
        answerBox.setEnabled(false);
        resultBox.setText("Incorrect");
        resultBoxLegacy.setVisibility(VISIBLE);
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
        answerBox.setVisibility(false);
        submitButton.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
        correctionBox.setVisibility(View.GONE);
        resultBoxLegacy.setVisibility(View.GONE);
        scoreBox.setVisibility(View.GONE);
        noTensesSelectedWarning.setVisibility(View.VISIBLE);
    }

    @Override
    public void showScore(Score score) {
        scoreBox.setVisibility(VISIBLE);
        scoreBox.setText(score.toString());
    }

    public void onSubmitAnswer() {
        submitListener.submitAnswer(answerBox.getText().toString());
    }

    @Override
    public void addSubmitListener(final SubmitListener submitListener) {
        this.submitListener = submitListener;
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
