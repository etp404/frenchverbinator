package uk.co.mould.matt.questions;
import java.util.List;
import java.util.Random;

import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.SupportedPersons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.parser.VerbListParser;

public final class RandomQuestionGenerator implements QuestionGenerator {
	private VerbListParser verbListParser;
    private List<MoodAndTense> moodsAndTensesToSelectFrom;

    public RandomQuestionGenerator(VerbListParser verbListParser, List<MoodAndTense> moodsAndTensesToSelectFrom) {
		this.verbListParser = verbListParser;
        this.moodsAndTensesToSelectFrom = moodsAndTensesToSelectFrom;
    }

	public Persons.Person getRandomPerson() {
		return SupportedPersons.ALL.get(randomNumber(0, SupportedPersons.ALL.size()));
	}

	public InfinitiveVerb getRandomVerb() {
		List<InfinitiveVerb> verbs = verbListParser.getVerbs();
		return verbs.get(randomNumber(0,verbs.size()));
	}

    public MoodAndTense getRandomVerbMoodAndTense() {
        return moodsAndTensesToSelectFrom.get(randomNumber(0, moodsAndTensesToSelectFrom.size()));
    }

	private int randomNumber(int from, int to) {
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		return r.nextInt(to-from) + from;
	}
}
