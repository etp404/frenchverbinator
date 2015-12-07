package uk.co.mould.matt.frenchverbinator.showcase;

import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;

import uk.co.mould.matt.frenchverbinator.showcase.ShowcaseViewAdapter;

public class AMLShowcaseViewAdapter implements ShowcaseViewAdapter {
    private ShowcaseView showcaseView;

    public AMLShowcaseViewAdapter(ShowcaseView showcaseView) {
        this.showcaseView = showcaseView;
    }

    @Override
    public void hide() {
        showcaseView.hide();
    }

    @Override
    public void overrideButtonClick(View.OnClickListener listener) {
        showcaseView.overrideButtonClick(listener);
    }

    @Override
    public void setContentTextForView(String contentText, Target view) {
        showcaseView.setContentText(contentText);
        showcaseView.setTarget(view);
    }
}
