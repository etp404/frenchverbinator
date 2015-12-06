package uk.co.mould.matt.frenchverbinator;

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uk.co.mould.matt.frenchverbinator.showcase.QuestionViewShowcaser;
import uk.co.mould.matt.frenchverbinator.showcase.ShowcaseViewAdapter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ShowcaseTests  extends AndroidTestCase {

    public void testShowcase() {
        Context context = getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        AndroidQuestionView questionView = (AndroidQuestionView) layoutInflater.inflate(R.layout.question_layout, null);

        FakeShowcaseViewAdapter fakeShowcaseAdapter = new FakeShowcaseViewAdapter();

        QuestionViewShowcaser questionViewShowcaser = new QuestionViewShowcaser(fakeShowcaseAdapter, questionView);

        questionViewShowcaser.start();

        assertTrue(fakeShowcaseAdapter.showInvoked);
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

}
