package uk.co.mould.matt.questions;
import java.util.List;

import uk.co.mould.matt.FailedQuestionStore;
import uk.co.mould.matt.ShouldUseFailedQuestion;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.SupportedPersons;
import uk.co.mould.matt.data.tenses.MoodAndTense;

public final class RandomQuestionGenerator implements QuestionGenerator {
    private final FailedQuestionStore failedQuestionStore;
    private ShouldUseFailedQuestion shouldUseFailedQuestion = new ShouldUseFailedQuestion() {
        @Override
        public boolean invoke() {
            return false;
        }
    };
    private RandomNumberGenerator randomNumberGenerator;
	private List<InfinitiveVerb> verbList;
	private List<Persons.Person> personList;
	private IncludedTensesProvider moodsAndTensesToSelectFromProvider;

    public RandomQuestionGenerator(RandomNumberGenerator randomNumberGenerator,
                                   List<InfinitiveVerb> verbList,
                                   List<Persons.Person> personList,
                                   IncludedTensesProvider moodsAndTensesToSelectFromProvider,
                                   FailedQuestionStore failedQuestionStore,
                                   ShouldUseFailedQuestion shouldUseFailedQuestion) {
        this.randomNumberGenerator = randomNumberGenerator;
        this.verbList = verbList;
        this.personList = personList;
        this.moodsAndTensesToSelectFromProvider = moodsAndTensesToSelectFromProvider;
        this.failedQuestionStore = failedQuestionStore;
        this.shouldUseFailedQuestion = shouldUseFailedQuestion;
    }


	@Override
	public void getQuestion(final Callback callback) {
        if (moodsAndTensesToSelectFromProvider.includedTensesCount() == 0) {
            callback.noTensesSelected();
        }
        else {
            //TODO: refactor this once moved over to this style.
            if (shouldUseFailedQuestion.invoke()) {
                tryToUseOldQuestion(callback);
            }
            else {
                callback.questionProvided(new Question(getRandomPerson(), getRandomVerb(), getRandomVerbMoodAndTense()));
            }
        }
    }

    private void tryToUseOldQuestion(final Callback callback) {
        FailedQuestionStore.Callback failedQuestionStoreCallback = new FailedQuestionStore.Callback() {
            @Override
            public void success(Question question) {
                callback.questionProvided(question);
            }

            @Override
            public void failure() {
                callback.questionProvided(new Question(getRandomPerson(), getRandomVerb(), getRandomVerbMoodAndTense()));
            }
        };
        failedQuestionStore.getFailedQuestion(failedQuestionStoreCallback, moodsAndTensesToSelectFromProvider.getIncludedTenses());
    }

    private Persons.Person getRandomPerson() {
		return personList.get(randomNumberGenerator.randomNumber(0, SupportedPersons.ALL.size()));
	}

	private InfinitiveVerb getRandomVerb() {
		return verbList.get(randomNumberGenerator.randomNumber(0, verbList.size()));
	}

	private MoodAndTense getRandomVerbMoodAndTense() {
		return moodsAndTensesToSelectFromProvider.getIncludedTenses().get(randomNumberGenerator.randomNumber(0, moodsAndTensesToSelectFromProvider.includedTensesCount()));
	}



}
