package uk.co.mould.matt.frenchverbinator;

import android.content.Context;
import android.graphics.Point;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;

import com.github.amlcurran.showcaseview.targets.Target;

import java.util.ArrayList;
import java.util.List;

import uk.co.mould.matt.frenchverbinator.showcase.AutolaunchingQuestionViewShowcaser;
import uk.co.mould.matt.frenchverbinator.showcase.AutolaunchingQuestionViewShowcaser.ViewTargetFactory;
import uk.co.mould.matt.frenchverbinator.showcase.ShowcaseViewAdapter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ShowcaseTests  extends AndroidTestCase {

    public void testShowcase() {
        Context context = getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        AndroidQuestionView questionView = (AndroidQuestionView) layoutInflater.inflate(R.layout.question_layout, null);

        FakeToolbarTargetFactory fakeTargetFactory = new FakeToolbarTargetFactory();
        FakeViewTargetFactory fakeViewTargetFactory = new FakeViewTargetFactory();
        FakeShowcaseViewAdapter fakeShowcaseAdapter = new FakeShowcaseViewAdapter();

        new AutolaunchingQuestionViewShowcaser(getContext(), fakeTargetFactory, fakeViewTargetFactory, fakeShowcaseAdapter, questionView);

        assertTrue(fakeShowcaseAdapter.viewsTargeted.get(0) instanceof FakeToolbarTarget);
        FakeToolbarTarget fakeToolbarTarget = (FakeToolbarTarget)fakeShowcaseAdapter.viewsTargeted.get(0);
        assertThat(fakeToolbarTarget.toolbar, is(questionView.findViewById(R.id.toolbar)));
        assertThat(fakeToolbarTarget.targetId, is(R.id.action_settings));

        fakeShowcaseAdapter.onClickListener.onClick(null);
        assertTrue(fakeShowcaseAdapter.viewsTargeted.get(1) instanceof FakeViewTarget);
        FakeViewTarget fakeViewTarget1 = (FakeViewTarget)fakeShowcaseAdapter.viewsTargeted.get(1);
        assertThat(fakeViewTarget1.targetView.getId(), is(R.id.question));
        assertThat(fakeShowcaseAdapter.contentTitles.get(1), is("Verbinator will repeat questions that you get wrong."));

        fakeShowcaseAdapter.onClickListener.onClick(null);
        assertTrue(fakeShowcaseAdapter.viewsTargeted.get(2) instanceof FakeViewTarget);
        FakeViewTarget fakeViewTarget2 = (FakeViewTarget)fakeShowcaseAdapter.viewsTargeted.get(2);
        assertThat(fakeViewTarget2.targetView.getId(), is(R.id.answer_box));
        assertThat(fakeShowcaseAdapter.contentTitles.get(2), is("Give your answer in the form 'tu regardes'."));

        fakeShowcaseAdapter.onClickListener.onClick(null);
        assertTrue(fakeShowcaseAdapter.viewsTargeted.get(3) instanceof FakeToolbarTarget);
        FakeToolbarTarget fakeToolbarTarget2 = (FakeToolbarTarget)fakeShowcaseAdapter.viewsTargeted.get(3);
        assertThat(fakeToolbarTarget2.toolbar, is(questionView.findViewById(R.id.toolbar)));
        assertThat(fakeToolbarTarget2.targetId, is(R.id.feedback_form_menu_button));
        assertThat(fakeShowcaseAdapter.contentTitles.get(3), is(getContext().getString(R.string.feedback_blurb)));

        fakeShowcaseAdapter.onClickListener.onClick(null);
        assertTrue(fakeShowcaseAdapter.hideInvoked);
    }

    private class FakeShowcaseViewAdapter implements ShowcaseViewAdapter {
        private View.OnClickListener onClickListener;
        public List<Target> viewsTargeted = new ArrayList<>();
        public List<String> contentTitles = new ArrayList<>();
        public boolean hideInvoked;

        @Override
        public void hide() {
            hideInvoked = true;
        }

        @Override
        public void overrideButtonClick(View.OnClickListener listener) {
            this.onClickListener = listener;
        }

        @Override
        public void setContentTextForView(String contentTitle, Target viewTarget) {
            viewsTargeted.add(viewTarget);
            contentTitles.add(contentTitle);

        }
    }

    private class FakeToolbarTargetFactory implements AutolaunchingQuestionViewShowcaser.ToolbarTargetFactory {

        @Override
        public Target createToolbarTarget(android.support.v7.widget.Toolbar toolbar, int targetId) {
            return new FakeToolbarTarget(toolbar, targetId);
        }
    }

    private class FakeToolbarTarget implements Target {

        private final android.support.v7.widget.Toolbar toolbar;
        private final int targetId;

        public FakeToolbarTarget(android.support.v7.widget.Toolbar toolbar, int targetId) {

            this.toolbar = toolbar;
            this.targetId = targetId;
        }

        @Override
        public Point getPoint() {
            return null;
        }
    }


    private class FakeViewTargetFactory implements ViewTargetFactory {
        public Target createTarget(View targetView) {
            return new FakeViewTarget(targetView);
        }
    }

    private class FakeViewTarget implements Target {

        private View targetView;

        public FakeViewTarget(View targetView) {
            this.targetView = targetView;
        }

        @Override
        public Point getPoint() {
            return null;
        }
    }
}
