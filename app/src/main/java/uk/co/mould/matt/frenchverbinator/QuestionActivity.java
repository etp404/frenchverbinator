package uk.co.mould.matt.frenchverbinator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.xml.sax.InputSource;

import uk.co.mould.matt.frenchverbinator.settings.SharedPrefsUserSettings;
import uk.co.mould.matt.parser.VerbListParser;
import uk.co.mould.matt.questions.QuestionGenerator;
import uk.co.mould.matt.questions.RandomQuestionGenerator;
import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbTemplateParser;

public class QuestionActivity extends AppCompatActivity {

    private SharedPrefsUserSettings storedUserSettings;
    private VerbTemplateParser verbTemplateParser;
    private VerbListParser verbListParser;
    private ConjugationParser conjugationParser;
    private Conjugator conjugator;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_layout);
		storedUserSettings = new SharedPrefsUserSettings(getSharedPreferences(SharedPrefsUserSettings.SETTINGS, 0));
        verbTemplateParser = new VerbTemplateParser(new InputSource(getResources().openRawResource(
                R.raw.verbs_fr)));
        conjugationParser = new ConjugationParser(new InputSource(getResources().openRawResource(R.raw.conjugation_fr)));
        verbListParser = new VerbListParser(new InputSource(getResources().openRawResource(R.raw.verb_list)));
        conjugator = new Conjugator(verbTemplateParser, conjugationParser);
        QuestionGenerator questionGenerator = new RandomQuestionGenerator(
                verbListParser,
                storedUserSettings.includedTenses());

        LayoutInflater layoutInflater = getLayoutInflater();
        AndroidQuestionView questionView = (AndroidQuestionView) layoutInflater.inflate(R.layout.question_layout, (ViewGroup)findViewById(R.id.android_question_view));
        new QuestionPresenter(
                questionView,
                questionGenerator,
                conjugator);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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
