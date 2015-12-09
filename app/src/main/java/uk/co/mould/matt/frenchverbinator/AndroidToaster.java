package uk.co.mould.matt.frenchverbinator;

import android.widget.Toast;

public class AndroidToaster implements Toaster {

    public void toast(String text) {
        Toast toast = new Toast(context);
        toast.setText(text);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
