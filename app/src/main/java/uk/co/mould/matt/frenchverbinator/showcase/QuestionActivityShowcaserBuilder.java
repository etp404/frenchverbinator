package uk.co.mould.matt.frenchverbinator.showcase;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import uk.co.mould.matt.frenchverbinator.AndroidQuestionView;
import uk.co.mould.matt.frenchverbinator.QuestionActivity;
import uk.co.mould.matt.frenchverbinator.R;

public class QuestionActivityShowcaserBuilder {
    public static AutolaunchingQuestionViewShowcaser build(Activity activity, AndroidQuestionView questionView) {
        ShowcaseView showcaseView = new ShowcaseView.Builder(activity)
                .setStyle(R.style.CustomShowcaseTheme)
                .singleShot((long)R.id.question_activity_showcase_id)
                .build();
        showcaseView.setDetailTextAlignment(Layout.Alignment.ALIGN_CENTER);
        AutolaunchingQuestionViewShowcaser.ToolbarTargetFactory toolbarTargetFactory = new AutolaunchingQuestionViewShowcaser.ToolbarTargetFactory() {
            @Override
            public Target createToolbarTarget(Toolbar toolbar, int targetId) {
                return new QuestionActivity.ToolbarActionItemTarget(toolbar, targetId);
            }
        };
        AutolaunchingQuestionViewShowcaser.ViewTargetFactory viewTargetFactory = new AutolaunchingQuestionViewShowcaser.ViewTargetFactory() {
            @Override
            public Target createTarget(View targetView) {
                return new ViewTarget(targetView);
            }
        };
        return new AutolaunchingQuestionViewShowcaser(toolbarTargetFactory, viewTargetFactory, new AMLShowcaseViewAdapter(showcaseView), questionView);
    }
}
