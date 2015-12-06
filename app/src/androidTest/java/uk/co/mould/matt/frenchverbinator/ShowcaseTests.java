package uk.co.mould.matt.frenchverbinator;

import android.test.AndroidTestCase;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.amlcurran.showcaseview.ShowcaseViewApi;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ShowcaseTests  extends AndroidTestCase {

    public void testShowcase() {
//        Context context = getContext();
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        AndroidQuestionView questionView = (AndroidQuestionView) layoutInflater.inflate(R.layout.question_layout, null);
//
//        AndroidQuestionShowcaser showcase = new AndroidQuestionShowcaser();
//        showcase.runShowcase(R.id.action_settings, "You can select the tenses you wish to be tested on here.");
//
//        ShowcaseAdapter showcaseAdapter = new ShowcaseAdapter();

        FakeShowcaseView fakeShowcaseView = new FakeShowcaseView();

        QuestionViewShowcaser questionViewShowcaser = new QuestionViewShowcaser(fakeShowcaseView);

        questionViewShowcaser.start();
        assertTrue(fakeShowcaseView.showInvoked);
    }

    private class QuestionViewShowcaser {
        private ShowcaseViewApi showcaseView;

        public QuestionViewShowcaser(ShowcaseViewApi showcaseView) {

            this.showcaseView = showcaseView;
        }

        public void start() {
            showcaseView.show();
        }
    }

    private class FakeShowcaseView implements ShowcaseViewApi {
        Map<View, String> showcaseMap = new HashMap<>();
        public boolean showInvoked;

        @Override
        public void hide() {

        }

        @Override
        public void show() {
            showInvoked = true;
        }

        @Override
        public void setContentTitle(CharSequence title) {

        }

        @Override
        public void setContentText(CharSequence text) {

        }

        @Override
        public void setButtonPosition(RelativeLayout.LayoutParams layoutParams) {

        }

        @Override
        public void setHideOnTouchOutside(boolean hideOnTouch) {

        }

        @Override
        public void setBlocksTouches(boolean blockTouches) {

        }

        @Override
        public void setStyle(int theme) {

        }

        @Override
        public boolean isShowing() {
            return false;
        }
    }

}
