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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.frenchverbinator.databinding.AnswerBox;
import uk.co.mould.matt.frenchverbinator.databinding.ResultBox;
import uk.co.mould.matt.marking.Score;
import uk.co.mould.matt.questions.Question;

public final class AndroidQuestionView extends LinearLayout implements QuestionView {
    private static final String QUESTION_TEMPLATE = "What is the '%s' form of %s (%s) in the %s?";

    public final ObservableField<String> questionText = new ObservableField<>();
    public final ObservableField<Integer> questionVisibility = new ObservableField<>();
    public final ObservableField<Boolean> submitButtonVisibility = new ObservableField<>();
    public final ObservableField<String> scoreBoxText = new ObservableField<>();
    public final ObservableField<Integer> scoreBoxVisibility = new ObservableField<>();
    public final ObservableField<Integer> nextButtonVisibility = new ObservableField<>();
    public final ObservableField<Integer> noTensesSelectedWarningVisibility = new ObservableField<>();
    public final ObservableField<String> correctionBoxText = new ObservableField<>();
    public final ObservableField<Integer> correctionBoxVisibility = new ObservableField<>();
    public final ObservableField<Integer> greenTickVisibility = new ObservableField<>();
    public final ObservableField<Integer> redCrossVisibility = new ObservableField<>();

    public final ResultBox resultBox = new ResultBox();
    public final AnswerBox answerBox = new AnswerBox();
    private ImageView greenTick;
    private ImageView redCross;
    private SubmitListener submitListener;
    private NextQuestionListener nextQuestionListener;

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
        greenTick = (ImageView)findViewById(R.id.green_tick);
        redCross = (ImageView) findViewById(R.id.red_cross);
    }

    @Override
    public void setQuestion(Question question) {
        noTensesSelectedWarningVisibility.set(View.GONE);
        correctionBoxVisibility.set(View.GONE);
        resultBox.setVisible(false);
        submitButtonVisibility.set(true);
        nextButtonVisibility.set(View.GONE);
        answerBox.setVisibility(true);
        answerBox.setEnabled(true);
        answerBox.setText("");
        redCrossVisibility.set(GONE);

        greenTickVisibility.set(GONE);
        this.questionText.set(
                String.format(
                        QUESTION_TEMPLATE,
                        question.person.getPerson(),
                        question.verb.frenchVerb,
                        question.verb.englishVerb,
                        question.moodAndTense.toString()));
        this.questionVisibility.set(VISIBLE);
        clearAnimation();
    }

    @Override
    public void setResultToCorrect() {
        resultBox.setText("Correct");
        resultBox.setVisible(true);
        answerBox.setEnabled(false);
        correctionBoxVisibility.set(GONE);
        nextButtonVisibility.set(VISIBLE);
        submitButtonVisibility.set(false);
        showGreenTick();
    }

    private void showGreenTick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AnimatedVectorDrawable drawableGreenTick = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.drawable_green_tick);
            greenTick.setImageDrawable(drawableGreenTick);
            greenTickVisibility.set(VISIBLE);
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
            redCrossVisibility.set(VISIBLE);
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
        resultBox.setVisible(true);
        correctionBoxText.set(correctAnswer.toString());
        correctionBoxVisibility.set(VISIBLE);
        nextButtonVisibility.set(View.VISIBLE);
        submitButtonVisibility.set(false);
        greenTick.setVisibility(View.GONE);
        showRedCross();
    }

    @Override
    public void showNoTensesSelected() {
        questionVisibility.set(View.GONE);
        answerBox.setVisibility(false);
        submitButtonVisibility.set(false);
        nextButtonVisibility.set(View.GONE);
        correctionBoxVisibility.set(GONE);
        resultBox.setVisible(false);
        scoreBoxVisibility.set(GONE);
        noTensesSelectedWarningVisibility.set(View.VISIBLE);
    }

    @Override
    public void showScore(Score score) {
        scoreBoxVisibility.set(VISIBLE);
        scoreBoxText.set(score.toString());
    }

    public void onSubmitAnswer() {
        submitListener.submitAnswer(answerBox.getText());
    }

    public void onRequestNextQuestion() {
        nextQuestionListener.requestNextQuestion();
    }

    @Override
    public void addSubmitListener(final SubmitListener submitListener) {
        this.submitListener = submitListener;
    }

    @Override
    public void addNextQuestionListener(NextQuestionListener nextQuestionListener) {
        this.nextQuestionListener = nextQuestionListener;
    }
}
