package uk.co.mould.matt.frenchverbinator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import org.xml.sax.InputSource;

import uk.co.mould.matt.parser.VerbListParser;
import uk.co.mould.matt.questions.QuestionGenerator;
import uk.co.mould.matt.questions.RandomQuestionGenerator;
import uk.co.mould.matt.ui.QuestionPresenter;
import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbTemplateParser;

public class QuestionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_layout);
		VerbTemplateParser verbTemplateParser = new VerbTemplateParser(new InputSource(getResources().openRawResource(R.raw.verbs_fr)));
		ConjugationParser conjugationParser = new ConjugationParser(new InputSource(getResources().openRawResource(R.raw.conjugation_fr)));
		VerbListParser verbListParser = new VerbListParser(new InputSource(getResources().openRawResource(R.raw.verb_list)));

		QuestionGenerator questionGenerator = new RandomQuestionGenerator(verbListParser);
		Conjugator conjugator = new Conjugator(verbTemplateParser, conjugationParser);

		final QuestionPresenter questionPresenter = new QuestionPresenter(
				new AndroidQuestionView((ViewGroup)findViewById(R.id.question_view_group)),
				questionGenerator,
				conjugator);

		setUpSubmitButton(questionPresenter);

		setUpNextQuestionButton(questionPresenter);

		questionPresenter.showQuestion();
	}

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

}
