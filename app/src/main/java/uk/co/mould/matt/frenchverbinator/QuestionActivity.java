package uk.co.mould.matt.frenchverbinator;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class QuestionActivity extends AppCompatActivity {

    private static final boolean ANIMATE_SHOWCASE = true;
    private AndroidQuestionView androidQuestionView;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        androidQuestionView = (AndroidQuestionView)findViewById(R.id.android_question_view);
        QuestionPresenterFactory.create(getApplicationContext(), androidQuestionView);

        QuestionActivityShowcaserBuilder.build(QuestionActivity.this).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }



    private static class QuestionActivityShowcaserBuilder {
        static QuestionActivityShowcaser build(QuestionActivity activityToShowcase) {
            ShowcaseView showcaseView = new ShowcaseView.Builder(activityToShowcase)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .build();
            showcaseView.setDetailTextAlignment(Layout.Alignment.ALIGN_CENTER);
            return new QuestionActivityShowcaser(showcaseView, activityToShowcase);
        }
    }

    private static class QuestionActivityShowcaser implements View.OnClickListener {

        private ShowcaseView showcaseView;
        private int showcaseCount = 0;
        private QuestionActivity activity;

        public QuestionActivityShowcaser(ShowcaseView showcaseView, QuestionActivity activity) {
            this.activity = activity;
            this.showcaseView = showcaseView;
        }

        public void start() {
            showcaseView.overrideButtonClick(this);
            showcaseView.show();
            showcaseView.setContentText("You can select which tenses you wish to be tested on here.");
            Toolbar toolbar = (Toolbar)activity.findViewById(R.id.toolbar);
            showcaseView.setShowcase(new ToolbarActionItemTarget(toolbar, R.id.action_settings), ANIMATE_SHOWCASE);

        }
        public class ToolbarActionItemTarget implements Target {

            private final Toolbar toolbar;
            private final int menuItemId;

            public ToolbarActionItemTarget(Toolbar toolbar, @IdRes int itemId) {
                this.toolbar = toolbar;
                this.menuItemId = itemId;
            }

            @Override
            public Point getPoint() {
                return new ViewTarget(toolbar.findViewById(menuItemId)).getPoint();
            }

        }

        @Override
        public void onClick(View v) {
            switch (showcaseCount) {
                case 0:
                    showcaseView.setContentText("Verbinator will repeat questions that you get wrong.");
                    showcaseView.setShowcase(new ViewTarget(activity.findViewById(R.id.question)), ANIMATE_SHOWCASE);
                    break;
                case 1:
                    showcaseView.setContentText("Give your answer in the form 'tu regardes'.");
                    showcaseView.setShowcase(new ViewTarget(activity.findViewById(R.id.answer_box)), ANIMATE_SHOWCASE);
                    break;
                case 2:
                    showcaseView.hide();
                    break;
            }
            showcaseCount++;
        }
    }
}
