package uk.co.mould.matt.frenchverbinator.showcase;

import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.mould.matt.frenchverbinator.AndroidQuestionView;
import uk.co.mould.matt.frenchverbinator.R;

public class QuestionViewShowcaser implements View.OnClickListener {
    private final Iterator<Runnable> runnablesIterator;

    public QuestionViewShowcaser(final ShowcaseViewAdapter showcaseViewAdapter, final AndroidQuestionView androidQuestionView) {
        showcaseViewAdapter.overrideButtonClick(this);
        runnablesIterator = createRunnablesIterator(showcaseViewAdapter, androidQuestionView);
    }

    public void start() {
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
                            androidQuestionView.findViewById(R.id.toolbar));
                }
            });
            add(new Runnable() {
                @Override
                public void run() {
                    showcaseViewAdapter.setContentTextForView(
                            "Verbinator will repeat questions that you get wrong.",
                            androidQuestionView.findViewById(R.id.question));
                }
            });
            add(new Runnable() {
                @Override
                public void run() {
                    showcaseViewAdapter.setContentTextForView(
                            "Give your answer in the form 'tu regardes'.",
                            androidQuestionView.findViewById(R.id.answer_box));
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
