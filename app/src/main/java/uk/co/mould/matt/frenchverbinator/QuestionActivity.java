package uk.co.mould.matt.frenchverbinator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import org.xml.sax.InputSource;

import uk.co.mould.matt.data.SupportedPersons;
import uk.co.mould.matt.frenchverbinator.settings.SharedPrefsUserSettings;
import uk.co.mould.matt.parser.VerbListParser;
import uk.co.mould.matt.questions.QuestionGenerator;
import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbTemplateParser;
import uk.co.mould.matt.questions.SystemRandomNumberGenerator;

public class QuestionActivity extends AppCompatActivity {

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_layout);
        QuestionPresenterFactory.create(getApplicationContext(), (AndroidQuestionView) getLayoutInflater().inflate(R.layout.question_layout, (ViewGroup) findViewById(R.id.android_question_view)));
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

    private static class QuestionPresenterFactory {

        public static void create(Context context, AndroidQuestionView questionView) {
            SharedPrefsUserSettings storedUserSettings = new SharedPrefsUserSettings(context.getSharedPreferences(SharedPrefsUserSettings.SETTINGS, 0));
            VerbTemplateParser verbTemplateParser = new VerbTemplateParser(new InputSource(context.getResources().openRawResource(
                    R.raw.verbs_fr)));
            ConjugationParser conjugationParser = new ConjugationParser(new InputSource(context.getResources().openRawResource(R.raw.conjugation_fr)));
            VerbListParser verbListParser = new VerbListParser(new InputSource(context.getResources().openRawResource(R.raw.verb_list)));
            Conjugator conjugator = new Conjugator(verbTemplateParser, conjugationParser);
            QuestionGenerator questionGenerator = new QuestionGenerator(
                    new SystemRandomNumberGenerator(),
                    verbListParser.getVerbs(),
                    SupportedPersons.ALL,
                    storedUserSettings.includedTenses());

            new QuestionPresenter(
                    questionView,
                    questionGenerator,
                    conjugator);
        }
    }

}
