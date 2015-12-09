package uk.co.mould.matt.frenchverbinator.feedback;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import uk.co.mould.matt.frenchverbinator.R;
import uk.co.mould.matt.frenchverbinator.Toaster;

public class FeedbackEmailLauncher implements FeedbackLauncher {
    private Context context;
    private Toaster toaster;

    public FeedbackEmailLauncher(Context context, Toaster toaster) {

        this.context = context;
        this.toaster = toaster;
    }

    public void launch() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "matthewsimonmould@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Verbinator");
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(emailIntent);
        }
        catch (ActivityNotFoundException ex) {
            toaster.toast(context.getString(R.string.email_client_not_available));
        }
    }

}
