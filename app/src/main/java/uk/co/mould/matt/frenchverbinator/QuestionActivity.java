package uk.co.mould.matt.frenchverbinator;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import uk.co.mould.matt.frenchverbinator.showcase.QuestionActivityShowcaserBuilder;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        AndroidQuestionView questionView = (AndroidQuestionView) findViewById(R.id.android_question_view);

        uk.co.mould.matt.frenchverbinator.databinding.QuestionLayoutBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.question_layout);
        viewDataBinding.setQuestionViewModel(questionView);
        viewDataBinding.setResultBox(questionView.resultBox);
        QuestionPresenterFactory.create(getApplicationContext(), questionView);

        QuestionActivityShowcaserBuilder.build(this, questionView);
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
        else if (id == R.id.feedback_form_menu_button) {
            startActivity(new Intent(this, FeedbackActivity.class));
        }

        overridePendingTransition(R.anim.subactivity_open_translate, R.anim.activity_close_translate);

        return super.onOptionsItemSelected(item);
    }


    public static class ToolbarActionItemTarget implements Target {

        private final Toolbar toolbar;
        private final int menuItemId;

        public ToolbarActionItemTarget(Toolbar toolbar, int itemId) {
            this.toolbar = toolbar;
            this.menuItemId = itemId;
        }

        @Override
        public Point getPoint() {
            return new ViewTarget(toolbar.findViewById(menuItemId)).getPoint();
        }

    }
}
