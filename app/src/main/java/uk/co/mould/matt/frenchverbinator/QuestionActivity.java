package uk.co.mould.matt.frenchverbinator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import org.xml.sax.InputSource;
import uk.co.mould.matt.QuestionGeneratorFromXml;
import uk.co.mould.matt.QuestionPresenter;

public class QuestionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_layout);
		try {
			QuestionPresenter questionPresenter = new QuestionPresenter(
					new AndroidQuestionView((ViewGroup)findViewById(R.id.question_view_group)),
					new QuestionGeneratorFromXml(new InputSource(getResources().openRawResource(R.raw.verbs_fr))));
			questionPresenter.showQuestion();

		} catch (Exception ignored) {}
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
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
