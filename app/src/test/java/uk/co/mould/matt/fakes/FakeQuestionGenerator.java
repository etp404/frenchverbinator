package uk.co.mould.matt.fakes;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.questions.Callback;
import uk.co.mould.matt.questions.Question;
import uk.co.mould.matt.questions.QuestionGenerator;
import uk.co.mould.matt.data.Persons;

public class FakeQuestionGenerator implements QuestionGenerator {
	private Question question;

	public FakeQuestionGenerator(Question question) {
		this.question = question;
    }

	@Override
	public void getQuestion(Callback callback) {
		callback.questionProvided(question);
	}
}
