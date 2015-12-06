package uk.co.mould.matt.frenchverbinator.showcase;

import android.view.View;

public interface ShowcaseViewAdapter {
    void show();

    void hide();

    void overrideButtonClick(View.OnClickListener listener);

    void setContentTextForView(String contentTitle, View view);
}
