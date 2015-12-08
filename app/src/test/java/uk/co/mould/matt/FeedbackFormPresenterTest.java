package uk.co.mould.matt;

import org.junit.Test;

import uk.co.mould.matt.frenchverbinator.feedback.FeedbackFormPresenter;
import uk.co.mould.matt.frenchverbinator.feedback.FeedbackLauncher;
import uk.co.mould.matt.frenchverbinator.settings.ui.FeedbackView;

import static org.junit.Assert.assertTrue;

public class FeedbackFormPresenterTest {

    @Test
    public void testThatFeedbackSenderIsInvokedWhenButtonIsPressed() {
        FakeFeedbackLauncher fakeFeedbackLauncher = new FakeFeedbackLauncher();
        FakeFeedbackView fakeFeedbackView = new FakeFeedbackView();
        new FeedbackFormPresenter(fakeFeedbackLauncher, fakeFeedbackView);
        fakeFeedbackView.sendFeedbackListener.sendFeedback();
        assertTrue(fakeFeedbackLauncher.launched);
    }

    private class FakeFeedbackLauncher implements FeedbackLauncher {
        private boolean launched = false;

        @Override
        public void launch() {
            launched = true;
        }
    }

    private class FakeFeedbackView implements FeedbackView {
        private SendFeedbackListener sendFeedbackListener;

        @Override
        public void addSendFeedbackListener(SendFeedbackListener sendFeedbackListener) {
            this.sendFeedbackListener = sendFeedbackListener;
        }
    }
}
