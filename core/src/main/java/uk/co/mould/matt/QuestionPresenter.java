package uk.co.mould.matt;

public class QuestionPresenter {
	private QuestionView questionView;
	private QuestionGenerator fakeQuestionGenerator;

	public QuestionPresenter(QuestionView questionView, QuestionGenerator fakeQuestionGenerator) {
		this.questionView = questionView;
		this.fakeQuestionGenerator = fakeQuestionGenerator;
	}

	public void showQuestion() {
		questionView.setPerson(fakeQuestionGenerator.getRandomPerson());
		questionView.setVerb(fakeQuestionGenerator.getRandomVerb());
	}
}
