package uk.co.mould.matt.frenchverbinator;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import uk.co.mould.matt.frenchverbinator.feedback.FeedbackEmailLauncher;
import uk.co.mould.matt.frenchverbinator.feedback.FeedbackFormPresenter;
import uk.co.mould.matt.frenchverbinator.settings.ui.AndroidFeedbackView;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Drawable upArrow = ContextCompat.getDrawable(getApplicationContext(), R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icons), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AndroidFeedbackView feedbackView = (AndroidFeedbackView) findViewById(R.id.android_feedback_form);
        new FeedbackFormPresenter(feedbackView, new FeedbackEmailLauncher(getApplicationContext(), new AndroidT));
    }
}
