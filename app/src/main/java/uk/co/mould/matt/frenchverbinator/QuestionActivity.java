package uk.co.mould.matt.frenchverbinator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final boolean ANIMATE_SHOWCASE = true;

    private ShowcaseView showcaseView;
    private int showcaseCount = 0;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_layout);

        QuestionPresenterFactory.create(getApplicationContext(), ((AndroidQuestionView) findViewById(R.id.android_question_view)));

        showcaseView = new ShowcaseView.Builder(this)
                .setStyle(R.style.CustomShowcaseTheme2)
                .setTarget(new ViewTarget(findViewById(R.id.question)))
                .setContentText("Verbinator will repeat questions that you get wrong")
                .setOnClickListener(this)
                .build();
        showcaseView.setDetailTextAlignment(Layout.Alignment.ALIGN_CENTER);
        showcaseView.show();
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

    @Override
    public void onClick(View v) {
        switch (showcaseCount) {
            case 0:
                showcaseView.setContentText("Give your answer in the form 'tu regardes'");
                showcaseView.setShowcase(new ViewTarget(findViewById(R.id.answer_box)), ANIMATE_SHOWCASE);
                break;
            case 1:
                showcaseView.hide();
                break;
        }
        showcaseCount++;
    }
}
