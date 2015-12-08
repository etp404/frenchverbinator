package uk.co.mould.matt.frenchverbinator.feedback;

import uk.co.mould.matt.frenchverbinator.settings.ui.FeedbackView;

public class FeedbackFormPresenter {
    public FeedbackFormPresenter(FeedbackView feedbackView, final FeedbackLauncher feedbackLauncher) {
        feedbackView.addSendFeedbackListener(new FeedbackView.SendFeedbackListener() {
            @Override
            public void sendFeedback() {
                feedbackLauncher.launch();
            }
        });
    }
}
