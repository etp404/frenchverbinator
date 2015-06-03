package uk.co.mould.matt.frenchverbinator;

import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.mould.matt.QuestionView;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;

public final class AndroidQuestionView implements QuestionView {
	private ViewGroup questionViewGroup;
	private TextView answerBox;

	public AndroidQuestionView(ViewGroup questionViewGroup) {
		this.questionViewGroup = questionViewGroup;
		answerBox = ((TextView) questionViewGroup.findViewById(R.id.answerBox));
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
		((TextView)questionViewGroup.findViewById(R.id.result_box)).setText("Correct");
	}

	@Override
	public void showIncorrect() {
		((TextView)questionViewGroup.findViewById(R.id.result_box)).setText("Incorrect");
	}

	@Override
	public String getAnswer() {
		return answerBox.getText().toString();
	}

	@Override
	public void answerMode() {
		answerBox.setEnabled(false);
		questionViewGroup.findViewById(R.id.submitButton).setEnabled(false);
	}
}
