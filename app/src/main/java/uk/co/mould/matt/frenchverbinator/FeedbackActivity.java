package uk.co.mould.matt.frenchverbinator;

import android.os.Bundle;
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

        AndroidFeedbackView feedbackView = (AndroidFeedbackView) findViewById(R.id.feedback_form);
        new FeedbackFormPresenter(feedbackView, new FeedbackEmailLauncher(getApplicationContext()));
    }
}
