package uk.co.mould.matt.frenchverbinator;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;

import uk.co.mould.matt.frenchverbinator.feedback.FeedbackEmailLauncher;
import uk.co.mould.matt.frenchverbinator.settings.ui.AndroidFeedbackView;
import uk.co.mould.matt.frenchverbinator.settings.ui.FeedbackView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FeedbackFormTests extends AndroidTestCase {

    private AndroidFeedbackView feedbackLayout;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        Context context = getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        feedbackLayout = (AndroidFeedbackView) layoutInflater.inflate(R.layout.feedback_layout, null);
    }

    public void testThatEmailIntentIsLaunchedAsIntended() {
        FakeContext fakeContext = new FakeContext();
        new FeedbackEmailLauncher(fakeContext, null).launch();
        Intent intent = fakeContext.startActivityCalledWith;
        assertThat(intent.getAction(), is(Intent.ACTION_SENDTO));
        assertThat(intent.getData(), is(Uri.fromParts("mailto", "matthewsimonmould@gmail.com", null)));
        assertThat(intent.getStringExtra(Intent.EXTRA_SUBJECT), is("Feedback for Verbinator"));
        assertThat(intent.getFlags(), is(Intent.FLAG_ACTIVITY_NEW_TASK));

    }

    public void testThatToastIsShownIfEmailIsNotEnabled() {
        FakeToaster fakeToaster = new FakeToaster();
        new FeedbackEmailLauncher(new FakeContextWithNoEmail(), fakeToaster).launch();
        assertThat(fakeToaster.toastText, is(getContext().getString(R.string.email_client_not_available)));
    }

    public void testThatDetailsAreIncludedAsIntended() {
        TextView feedbackDetails = (TextView)feedbackLayout.findViewById(R.id.feedback_details);
        assertThat(feedbackDetails.getText().toString(),
                is(getContext().getResources().getString(R.string.feedback_blurb)));
    }

    public void testThatListenersAreToldWhenFeedbackButtonPressed() {
        Button feedbackButton = (Button)feedbackLayout.findViewById(R.id.feedback_button);
        CapturingSendFeedbackListener sendFeedbackListener = new CapturingSendFeedbackListener();
        feedbackLayout.addSendFeedbackListener(sendFeedbackListener);
        feedbackButton.performClick();
        assertTrue(sendFeedbackListener.wasInvoked);
    }

    private static class CapturingSendFeedbackListener implements FeedbackView.SendFeedbackListener {
        public boolean wasInvoked = false;
        @Override
        public void sendFeedback() {
            wasInvoked = true;
        }
    }

    private class FakeContext extends MockContext {

        public Intent startActivityCalledWith;

        @Override
        public void startActivity(Intent intent) {
            startActivityCalledWith = intent;
        }
    }

    private class FakeContextWithNoEmail extends MockContext {

        public FakeContextWithNoEmail() {
        }

        @Override
        public void startActivity(Intent intent) {
            throw new ActivityNotFoundException();
        }
    }

    private class FakeToaster implements Toaster {

        private String toastText;

        public FakeToaster() {
        }

        @Override
        public void toast(String text) {
            this.toastText = text;
        }
    }

}
