package uk.co.mould.matt.frenchverbinator.settings.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import uk.co.mould.matt.frenchverbinator.R;

public class AndroidFeedbackView extends LinearLayout implements FeedbackView {
    public AndroidFeedbackView(Context context) {
        super(context);
    }

    public AndroidFeedbackView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AndroidFeedbackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void addSendFeedbackListener(final SendFeedbackListener sendFeedbackListener) {
        findViewById(R.id.feedback_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedbackListener.sendFeedback();
            }
        });
    }
}
