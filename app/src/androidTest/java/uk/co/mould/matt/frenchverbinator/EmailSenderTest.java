package uk.co.mould.matt.frenchverbinator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EmailSenderTest extends AndroidTestCase {
    public void testThatEmailIntentIsLaunchedAsIntended() {
        FakeContext fakeContext = new FakeContext();
        FeedbackEmailLauncher.launch(fakeContext);
        Intent intent = fakeContext.startActivityCalledWith;
        assertThat(intent.getAction(), is(Intent.ACTION_SENDTO));
        assertThat(intent.getData(), is(Uri.fromParts("mailto", "matthewsimonmould@gmail.com", null)));
        assertThat(intent.getStringExtra(Intent.EXTRA_SUBJECT), is("Feedback for Verbinator"));
    }

    private class FakeContext extends MockContext {
        public Intent startActivityCalledWith;

        @Override
        public void startActivity(Intent intent) {
            startActivityCalledWith = intent;
        }
    }

    private static class FeedbackEmailLauncher {

        public static void launch(Context context) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "matthewsimonmould@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Verbinator");
            context.startActivity(emailIntent);
        }
    }
}
