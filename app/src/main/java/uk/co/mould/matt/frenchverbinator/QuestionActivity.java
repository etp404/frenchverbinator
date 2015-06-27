package uk.co.mould.matt.frenchverbinator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.xml.sax.InputSource;

import java.util.ArrayList;

import uk.co.mould.matt.data.SupportedMoodsAndTenses;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.frenchverbinator.settings.SettingsActivity;
import uk.co.mould.matt.frenchverbinator.settings.SharedPrefsUserSettings;
import uk.co.mould.matt.parser.VerbListParser;
import uk.co.mould.matt.questions.QuestionGenerator;
import uk.co.mould.matt.questions.RandomQuestionGenerator;
import uk.co.mould.matt.ui.QuestionPresenter;
import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbTemplateParser;

public class QuestionActivity extends Activity {

    private SharedPrefsUserSettings storedUserSettings;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_layout);
		storedUserSettings = new SharedPrefsUserSettings(getSharedPreferences(SharedPrefsUserSettings.SETTINGS, 0));
	}

    @Override
    protected void onResume() {
        super.onResume();
        VerbTemplateParser verbTemplateParser = new VerbTemplateParser(new InputSource(getResources().openRawResource(R.raw.verbs_fr)));
        ConjugationParser conjugationParser = new ConjugationParser(new InputSource(getResources().openRawResource(R.raw.conjugation_fr)));
        VerbListParser verbListParser = new VerbListParser(new InputSource(getResources().openRawResource(R.raw.verb_list)));


        QuestionGenerator questionGenerator = new RandomQuestionGenerator(
                verbListParser,
                storedUserSettings.includedTenses());
        Conjugator conjugator = new Conjugator(verbTemplateParser, conjugationParser);

        final QuestionPresenter questionPresenter = new QuestionPresenter(
                new AndroidQuestionView((ViewGroup)findViewById(R.id.question_view_group)),
                questionGenerator,
                conjugator);

        setUpSubmitButton(questionPresenter);

        setUpNextQuestionButton(questionPresenter);

        questionPresenter.showQuestion();    }

    private void setUpNextQuestionButton(final QuestionPresenter questionPresenter) {
		findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				questionPresenter.showQuestion();
			}
		});
	}

	private void setUpSubmitButton(final QuestionPresenter questionPresenter) {
		findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionPresenter.submitAnswer();
            }
        });
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
