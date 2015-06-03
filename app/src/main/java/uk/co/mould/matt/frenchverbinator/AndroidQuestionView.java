package uk.co.mould.matt.frenchverbinator;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.mould.matt.ui.QuestionView;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;

public final class AndroidQuestionView implements QuestionView {
	private ViewGroup questionViewGroup;
	private TextView answerBox;
	private View nextButton;
	private View submitButton;
	private TextView resultBox;

	public AndroidQuestionView(ViewGroup questionViewGroup) {
		this.questionViewGroup = questionViewGroup;
		answerBox = ((TextView) questionViewGroup.findViewById(R.id.answerBox));
		submitButton = questionViewGroup.findViewById(R.id.submitButton);
		nextButton = questionViewGroup.findViewById(R.id.next);
		resultBox = ((TextView)questionViewGroup.findViewById(R.id.result_box));
	}

	@Override
	public void setPerson(Persons.Person randomPerson) {
		((TextView)questionViewGroup.findViewById(R.id.person)).setText(randomPerson.getEnglishPronoun());
	}

	@Override
	public void setVerb(InfinitiveVerb randomVerb) {
		((TextView)questionViewGroup.findViewById(R.id.verb)).setText(randomVerb.toString());
	}

	@Override
	public void showCorrect() {
		resultBox.setText("Correct");
	}

	@Override
	public void showIncorrect() {
		resultBox.setText("Incorrect");
	}

	@Override
	public String getAnswer() {
		return answerBox.getText().toString();
	}

	@Override
	public void answerMode() {
		answerBox.setEnabled(false);

		resultBox.setVisibility(View.VISIBLE);

		submitButton.setVisibility(View.GONE);
		submitButton.setEnabled(false);

		nextButton.setVisibility(View.VISIBLE);
		nextButton.setEnabled(true);
	}

	@Override
	public void enterQuestionMode() {
		answerBox.setText("");
		answerBox.setEnabled(true);

		resultBox.setVisibility(View.GONE);

		submitButton.setVisibility(View.VISIBLE);
		submitButton.setEnabled(true);

		nextButton.setVisibility(View.GONE);
		nextButton.setEnabled(false);
	}
}
