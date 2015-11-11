package uk.co.mould.matt.questions;
import java.util.List;

import uk.co.mould.matt.FailedQuestionStore;
import uk.co.mould.matt.ShouldUseFailedQuestion;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.tenses.MoodAndTense;

public final class RandomQuestionGenerator implements QuestionGenerator {
    private FailedQuestionStore failedQuestionStore = new FailedQuestionStore() {
        @Override
        public Question pop() {
            return null;
        }

        @Override
        public boolean hasFailedQuestions() {
            return false;
        }

        @Override
        public void store(Question question) {

        }
    };
    private ShouldUseFailedQuestion shouldUseFailedQuestion = new ShouldUseFailedQuestion() {
        @Override
        public boolean invoke() {
            return false;
        }
    };
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

	public RandomQuestionGenerator(RandomNumberGenerator randomNumberGenerator,
                                   List<InfinitiveVerb> verbList,
                                   List<Persons.Person> personList,
                                   List<MoodAndTense> moodsAndTensesToSelectFrom,
                                   FailedQuestionStore failedQuestionStore,
                                   ShouldUseFailedQuestion shouldUseFailedQuestion) {
        this(randomNumberGenerator,
                verbList,
                personList,
                moodsAndTensesToSelectFrom);
        this.failedQuestionStore = failedQuestionStore;
        this.shouldUseFailedQuestion = shouldUseFailedQuestion;
    }

	@Override
	public void getQuestion(Callback callback) {
        if (moodsAndTensesToSelectFrom.size()==0) {
            callback.noTensesSelected();
        }
        else {
            //TODO: refactor this once moved over to this style.
            if (failedQuestionStore.hasFailedQuestions() && shouldUseFailedQuestion.invoke()) {
                callback.questionProvided(failedQuestionStore.pop());
            }
            else {
                callback.questionProvided(new Question(getRandomPerson(), getRandomVerb(), getRandomVerbMoodAndTense()));
            }
        }
    }

	@Override
	public void repeatFailedQuestionAfter(Question failedQuestion, int repeatAfter) {

	}

	private Persons.Person getRandomPerson() {
		return personList.get(randomNumberGenerator.randomNumber(0, Persons.getAllSupportedPersons().size()));
	}

	private InfinitiveVerb getRandomVerb() {
		return verbList.get(randomNumberGenerator.randomNumber(0, verbList.size()));
	}

	private MoodAndTense getRandomVerbMoodAndTense() {
		return moodsAndTensesToSelectFrom.get(randomNumberGenerator.randomNumber(0, moodsAndTensesToSelectFrom.size()));
	}



}
