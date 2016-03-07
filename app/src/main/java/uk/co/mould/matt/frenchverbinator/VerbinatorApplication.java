package uk.co.mould.matt.frenchverbinator;

import android.app.Application;

import org.xml.sax.InputSource;

import java.util.List;

import uk.co.mould.matt.FailedQuestionStore;
import uk.co.mould.matt.ShouldUseFailedQuestion;
import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.SupportedPersons;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.frenchverbinator.failedquestions.AndroidFailedQuestionStore;
import uk.co.mould.matt.frenchverbinator.settings.SharedPrefsUserSettings;
import uk.co.mould.matt.marking.AnswerChecker;
import uk.co.mould.matt.marking.AnswerChecking;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbListParser;
import uk.co.mould.matt.parser.VerbTemplateParser;
import uk.co.mould.matt.questions.IncludedTensesProvider;
import uk.co.mould.matt.questions.QuestionGenerator;
import uk.co.mould.matt.questions.RandomNumberGenerator;
import uk.co.mould.matt.questions.RandomQuestionGenerator;
import uk.co.mould.matt.questions.SystemRandomNumberGenerator;

/**
 * Copyright © 2016 Media Applications Technologies. All rights reserved.
 */
public class VerbinatorApplication  extends Application {

    private AnswerChecker answerChecking;
    private RandomQuestionGenerator randomQuestionGenerator;

    @Override
    public void onCreate() {
        super.onCreate();
        VerbTemplateParser verbTemplateParser = new VerbTemplateParser(new InputSource(getResources().openRawResource(
                R.raw.verbs_fr)));
        ConjugationParser conjugationParser = new ConjugationParser(new InputSource(getResources().openRawResource(R.raw.conjugation_fr)));
        Conjugator conjugator = new Conjugator(verbTemplateParser, conjugationParser);

        FailedQuestionStore failedQuestionStore = new AndroidFailedQuestionStore(this);

        answerChecking = new AnswerChecker(conjugator, failedQuestionStore);
        final SharedPrefsUserSettings storedUserSettings = new SharedPrefsUserSettings(getSharedPreferences(SharedPrefsUserSettings.SETTINGS, 0));
        VerbListParser verbListParser = new VerbListParser(new InputSource(getResources().openRawResource(R.raw.verb_list)));


        ShouldUseFailedQuestion shouldUseFailedQuestion25PercentOfTime = new ShouldUseFailedQuestion() {
            RandomNumberGenerator randomNumberGenerator = new SystemRandomNumberGenerator();

            @Override
            public boolean invoke() {
                return randomNumberGenerator.randomNumber(0, 100) > 75;
            }
        };

        randomQuestionGenerator = new RandomQuestionGenerator(
                new SystemRandomNumberGenerator(),
                verbListParser.getVerbs(),
                SupportedPersons.ALL,
                new IncludedTensesProvider() {
                    @Override
                    public List<MoodAndTense> getIncludedTenses() {
                        return storedUserSettings.includedTenses();
                    }
                },
                failedQuestionStore,
                shouldUseFailedQuestion25PercentOfTime);
    }

    public QuestionGenerator getRandomQuestionGenerator() {
        return randomQuestionGenerator;
    }

    public AnswerChecking getAnswerChecking() {
        return answerChecking;
    }
}
