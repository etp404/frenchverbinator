package uk.co.mould.matt.frenchverbinator;

import android.content.Context;
import android.widget.Toast;

public class AndroidToaster implements Toaster {

    private Context context;

    public AndroidToaster(Context context) {
        this.context = context.getApplicationContext();
    }

    public void toast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
