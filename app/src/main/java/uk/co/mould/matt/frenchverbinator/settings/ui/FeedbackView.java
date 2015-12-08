package uk.co.mould.matt.frenchverbinator.settings.ui;

public interface FeedbackView {

    void addSendFeedbackListener(SendFeedbackListener sendFeedbackListener);

    interface SendFeedbackListener {
        void sendFeedback();
    }
}
