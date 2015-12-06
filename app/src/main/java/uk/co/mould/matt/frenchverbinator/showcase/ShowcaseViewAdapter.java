package uk.co.mould.matt.frenchverbinator.showcase;

import android.view.View;

import com.github.amlcurran.showcaseview.targets.Target;

public interface ShowcaseViewAdapter {
    void show();

    void hide();

    void overrideButtonClick(View.OnClickListener listener);

    void setContentTextForView(String contentTitle, Target viewTarget);
}
