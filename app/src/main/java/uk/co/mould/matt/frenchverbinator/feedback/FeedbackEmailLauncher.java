package uk.co.mould.matt.frenchverbinator.feedback;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class FeedbackEmailLauncher implements FeedbackLauncher {
    private Context context;

    public FeedbackEmailLauncher(Context context) {

        this.context = context;
    }

    public void launch() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "matthewsimonmould@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Verbinator");
        context.startActivity(emailIntent);
    }

}
