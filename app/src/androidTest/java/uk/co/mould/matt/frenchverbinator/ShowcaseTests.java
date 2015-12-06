package uk.co.mould.matt.frenchverbinator;

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toolbar;

import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ShowcaseTests  extends AndroidTestCase {

    public void testShowcase() {
        Context context = getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        AndroidQuestionView questionView = (AndroidQuestionView) layoutInflater.inflate(R.layout.question_layout, null);
//
//        AndroidQuestionShowcaser showcase = new AndroidQuestionShowcaser();
//        showcase.runShowcase(R.id.action_settings, "You can select the tenses you wish to be tested on here.");
//
//        ShowcaseViewAdapter showcaseAdapter = new ShowcaseViewAdapter();

        FakeShowcaseViewAdapter fakeShowcaseAdapter = new FakeShowcaseViewAdapter();

        QuestionViewShowcaser questionViewShowcaser = new QuestionViewShowcaser(fakeShowcaseAdapter, questionView);

        questionViewShowcaser.start();

        assertTrue(fakeShowcaseAdapter.showInvoked);

        fakeShowcaseAdapter.onClickListener.onClick(null);
        assertThat(fakeShowcaseAdapter.viewsTargeted.get(0).getId(), is(R.id.toolbar));
        assertThat(fakeShowcaseAdapter.contentTitles.get(0), is("You can select which tenses you wish to practise here."));

        fakeShowcaseAdapter.onClickListener.onClick(null);
        assertThat(fakeShowcaseAdapter.viewsTargeted.get(1).getId(), is(R.id.question));
        assertThat(fakeShowcaseAdapter.contentTitles.get(1), is("Verbinator will repeat questions that you get wrong."));

        fakeShowcaseAdapter.onClickListener.onClick(null);
        assertThat(fakeShowcaseAdapter.viewsTargeted.get(2).getId(), is(R.id.answer_box));
        assertThat(fakeShowcaseAdapter.contentTitles.get(2), is("Give your answer in the form 'tu regardes'."));

        fakeShowcaseAdapter.onClickListener.onClick(null);
        assertTrue(fakeShowcaseAdapter.hideInvoked);
    }

    private class QuestionViewShowcaser implements View.OnClickListener {
        private ShowcaseViewAdapter showcaseViewAdapter;
        private AndroidQuestionView androidQuestionView;
        private int showcaseCount = 0;

        public QuestionViewShowcaser(ShowcaseViewAdapter showcaseViewAdapter,  AndroidQuestionView androidQuestionView) {
            this.showcaseViewAdapter = showcaseViewAdapter;
            this.androidQuestionView = androidQuestionView;
        }

        public void start() {
            showcaseViewAdapter.setContentTextForView(
                    "You can select which tenses you wish to practise here.",
                    androidQuestionView.findViewById(R.id.toolbar));
            showcaseViewAdapter.show();
            showcaseViewAdapter.overrideButtonClick(this);
        }

        @Override
        public void onClick(View v) {
            switch (showcaseCount) {
                case 0:
                    showcaseViewAdapter.setContentTextForView(
                            "Verbinator will repeat questions that you get wrong.",
                            androidQuestionView.findViewById(R.id.question));
                    break;
                case 1:
                    showcaseViewAdapter.setContentTextForView(
                            "Give your answer in the form 'tu regardes'.",
                            androidQuestionView.findViewById(R.id.answer_box));
                    break;
                case 2:
                    showcaseViewAdapter.hide();
                    break;
            }
            showcaseCount++;

        }
    }

    private class FakeShowcaseViewAdapter implements ShowcaseViewAdapter {
        public boolean showInvoked;
        private View.OnClickListener onClickListener;
        public List<View> viewsTargeted = new ArrayList<>();
        public List<String> contentTitles = new ArrayList<>();
        public boolean hideInvoked;

        @Override
        public void show() {
            showInvoked = true;
        }

        @Override
        public void hide() {
            hideInvoked = true;
        }

        @Override
        public void overrideButtonClick(View.OnClickListener listener) {
            this.onClickListener = listener;
        }

        @Override
        public void setContentTextForView(String contentTitle, View view) {
            viewsTargeted.add(view);
            contentTitles.add(contentTitle);

        }
    }

    private interface ShowcaseViewAdapter {
        void show();

        void hide();

        void overrideButtonClick(View.OnClickListener listener);

        void setContentTextForView(String contentTitle, View view);
    }
}
