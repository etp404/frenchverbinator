package uk.co.mould.matt.questions;
import java.util.List;
import java.util.Random;

import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.SupportedPersons;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.data.tenses.VerbMoodsAndTenses;
import uk.co.mould.matt.parser.VerbListParser;

public final class RandomQuestionGenerator implements uk.co.mould.matt.questions.QuestionGenerator {
	private VerbListParser verbListParser;

	public RandomQuestionGenerator(VerbListParser verbListParser) {
		this.verbListParser = verbListParser;
	}

	public Persons.Person getRandomPerson() {
		return SupportedPersons.ALL.get(randomNumber(0, SupportedPersons.ALL.size()));
	}

	public InfinitiveVerb getRandomVerb() {
		List<InfinitiveVerb> verbs = verbListParser.getVerbs();
		return verbs.get(randomNumber(0,verbs.size()));
	}

    public VerbMoodsAndTenses getRandomVerbMoodAndTense() {
        return new PresentIndicative();
        //return SupportedMoodsAndTenses.ALL.get(randomNumber(0, SupportedMoodsAndTenses.ALL.size()));
    }

	private int randomNumber(int from, int to) {
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		return r.nextInt(to-from) + from;
	}
}
