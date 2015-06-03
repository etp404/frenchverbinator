package uk.co.mould.matt;

public class QuestionPresenter {
	private QuestionView questionView;
	private QuestionGenerator fakeQuestionGenerator;

	public QuestionPresenter(QuestionView questionView, QuestionGenerator questionGenerator) {
		this.questionView = questionView;
		this.fakeQuestionGenerator = questionGenerator;
	}

	public void showQuestion() {
		questionView.setPerson(fakeQuestionGenerator.getRandomPerson());
		questionView.setVerb(fakeQuestionGenerator.getRandomVerb());
	}

	public void submitAnswer(String answer) {
		questionView.showCorrect();
	}
}
