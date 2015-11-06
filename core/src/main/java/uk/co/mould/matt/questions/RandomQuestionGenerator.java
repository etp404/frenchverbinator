package uk.co.mould.matt.questions;
import java.util.List;

import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.SupportedPersons;
import uk.co.mould.matt.data.tenses.MoodAndTense;

public final class RandomQuestionGenerator implements QuestionGenerator {
	private RandomNumberGenerator randomNumberGenerator;
	private List<InfinitiveVerb> verbList;
	private List<Persons.Person> personList;
	private List<MoodAndTense> moodsAndTensesToSelectFrom;

    public RandomQuestionGenerator(RandomNumberGenerator randomNumberGenerator,
								   List<InfinitiveVerb> verbList,
								   List<Persons.Person> personList,
								   List<MoodAndTense> moodsAndTensesToSelectFrom) {
		this.randomNumberGenerator = randomNumberGenerator;
		this.verbList = verbList;
		this.personList = personList;
		this.moodsAndTensesToSelectFrom = moodsAndTensesToSelectFrom;
    }

	@Override
	public void getQuestion(Callback callback) {
        if (moodsAndTensesToSelectFrom.size()==0) {
            callback.noTensesSelected();
        }
        else {
            callback.questionProvided(new Question(getRandomPerson(), getRandomVerb(), getRandomVerbMoodAndTense()));
        }
    }

	@Override
	public void repeatFailedQuestionAfter(Question failedQuestion, int repeatAfter) {

	}

	private Persons.Person getRandomPerson() {
		return personList.get(randomNumberGenerator.randomNumber(0, SupportedPersons.ALL.size()));
	}

	private InfinitiveVerb getRandomVerb() {
		return verbList.get(randomNumberGenerator.randomNumber(0, verbList.size()));
	}

	private MoodAndTense getRandomVerbMoodAndTense() {
		return moodsAndTensesToSelectFrom.get(randomNumberGenerator.randomNumber(0, moodsAndTensesToSelectFrom.size()));
	}



}
