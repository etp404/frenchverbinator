package uk.co.mould.matt.frenchverbinator;

import android.content.Context;

import org.xml.sax.InputSource;

import uk.co.mould.matt.FailedQuestionStore;
import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.SupportedPersons;
import uk.co.mould.matt.frenchverbinator.settings.SharedPrefsUserSettings;
import uk.co.mould.matt.marking.AnswerChecker;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbListParser;
import uk.co.mould.matt.parser.VerbTemplateParser;
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
        RandomQuestionGenerator randomQuestionGenerator = new RandomQuestionGenerator(
                new SystemRandomNumberGenerator(),
                verbListParser.getVerbs(),
                SupportedPersons.ALL,
                storedUserSettings.includedTenses());

        new QuestionPresenter(
                questionView,
                randomQuestionGenerator,
                new AnswerChecker(conjugator, FailedQuestionStore.NULL));
    }
}
