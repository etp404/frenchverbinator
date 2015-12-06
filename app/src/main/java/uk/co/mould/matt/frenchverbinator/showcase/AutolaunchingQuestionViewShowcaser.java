package uk.co.mould.matt.frenchverbinator.showcase;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.mould.matt.frenchverbinator.AndroidQuestionView;
import uk.co.mould.matt.frenchverbinator.R;

public class AutolaunchingQuestionViewShowcaser implements View.OnClickListener {
    private final ToolbarTargetFactory toolbarTargetFactory;
    private ViewTargetFactory viewTargetFactory;
    private final ShowcaseViewAdapter showcaseViewAdapter;
    private final AndroidQuestionView androidQuestionView;

    public interface ToolbarTargetFactory {
        Target createToolbarTarget(Toolbar toolbar, int targetId);
    }

    public interface ViewTargetFactory {
        Target createTarget(View targetView);
    }

    private final Iterator<Runnable> runnablesIterator;

    public AutolaunchingQuestionViewShowcaser(ToolbarTargetFactory toolbarTargetFactory, ViewTargetFactory viewTargetFactory, final ShowcaseViewAdapter showcaseViewAdapter, final AndroidQuestionView androidQuestionView) {
        this.toolbarTargetFactory = toolbarTargetFactory;
        this.viewTargetFactory = viewTargetFactory;
        this.showcaseViewAdapter = showcaseViewAdapter;
        this.androidQuestionView = androidQuestionView;
        showcaseViewAdapter.overrideButtonClick(this);
        runnablesIterator = createRunnablesIterator(showcaseViewAdapter, androidQuestionView);
        runnablesIterator.next().run();
    }

    @Override
    public void onClick(View v) {
        runnablesIterator.next().run();
    }

    private Iterator<Runnable> createRunnablesIterator(final ShowcaseViewAdapter showcaseViewAdapter, final AndroidQuestionView androidQuestionView) {
        List<Runnable> runnables = new ArrayList<Runnable>() {{
            add(new Runnable() {
                @Override
                public void run() {
                    showcaseViewAdapter.show();
                    showcaseViewAdapter.setContentTextForView(
                            "You can select which tenses you wish to practise here.",
                            toolbarTargetFactory.createToolbarTarget((Toolbar)androidQuestionView.findViewById(R.id.toolbar), R.id.action_settings));
                }
            });
            add(new Runnable() {
                @Override
                public void run() {
                    showcaseViewAdapter.setContentTextForView(
                            "Verbinator will repeat questions that you get wrong.",
                            viewTargetFactory.createTarget(androidQuestionView.findViewById(R.id.question)));
                }
            });
            add(new Runnable() {
                @Override
                public void run() {
                    showcaseViewAdapter.setContentTextForView(
                            "Give your answer in the form 'tu regardes'.",
                    viewTargetFactory.createTarget(androidQuestionView.findViewById(R.id.answer_box)));
                }
            });

            add(new Runnable() {
                @Override
                public void run() {
                    showcaseViewAdapter.hide();
                }
            });

        }};
        return runnables.iterator();
    }

}
