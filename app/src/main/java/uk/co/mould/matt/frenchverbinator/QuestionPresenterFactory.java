package uk.co.mould.matt.frenchverbinator;

import android.content.Context;

import org.xml.sax.InputSource;

import uk.co.mould.matt.FailedQuestionStore;
import uk.co.mould.matt.ShouldUseFailedQuestion;
import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.SupportedPersons;
import uk.co.mould.matt.frenchverbinator.failedquestions.AndroidFailedQuestionStore;
import uk.co.mould.matt.frenchverbinator.settings.SharedPrefsUserSettings;
import uk.co.mould.matt.marking.AnswerChecker;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbListParser;
import uk.co.mould.matt.parser.VerbTemplateParser;
import uk.co.mould.matt.questions.RandomNumberGenerator;
import uk.co.mould.matt.questions.RandomQuestionGenerator;
import uk.co.mould.matt.questions.SystemRandomNumberGenerator;

class QuestionPresenterFactory {

    public static void create(Context context, AndroidQuestionView questionView) {
        SharedPrefsUserSettings storedUserSettings = new SharedPrefsUserSettings(context.getSharedPreferences(SharedPrefsUserSettings.SETTINGS, 0));
        VerbTemplateParser verbTemplateParser = new VerbTemplateParser(new InputSource(context.getResources().openRawResource(
                R.raw.verbs_fr)));
        ConjugationParser conjugationParser = new ConjugationParser(new InputSource(context.getResources().openRawResource(R.raw.conjugation_fr)));
        VerbListParser verbListParser = new VerbListParser(new InputSource(context.getResources().openRawResource(R.raw.verb_list)));
        Conjugator conjugator = new Conjugator(verbTemplateParser, conjugationParser);

        FailedQuestionStore failedQuestionStore = new AndroidFailedQuestionStore(context);
        ShouldUseFailedQuestion returnTrue75PercentOfTime = new ShouldUseFailedQuestion() {
            RandomNumberGenerator randomNumberGenerator = new SystemRandomNumberGenerator();

            @Override
            public boolean invoke() {
                return randomNumberGenerator.randomNumber(0, 100) > 75;
            }
        };
        RandomQuestionGenerator randomQuestionGenerator = new RandomQuestionGenerator(
                new SystemRandomNumberGenerator(),
                verbListParser.getVerbs(),
                SupportedPersons.ALL,
                storedUserSettings.includedTenses(),
                failedQuestionStore,
                returnTrue75PercentOfTime);

        new QuestionPresenter(
                questionView,
                randomQuestionGenerator,
                new AnswerChecker(conjugator, failedQuestionStore));
    }
}
