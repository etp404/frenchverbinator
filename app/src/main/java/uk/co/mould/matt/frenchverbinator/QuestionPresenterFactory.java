package uk.co.mould.matt.frenchverbinator;

import android.content.Context;

class QuestionPresenterFactory {

    public static void create(Context context, AndroidQuestionView questionView) {
        VerbinatorApplication verbinatorApplication = (VerbinatorApplication)context.getApplicationContext();

        new QuestionPresenter(
                questionView,
                verbinatorApplication.getRandomQuestionGenerator(),
                verbinatorApplication.getAnswerChecking());
    }
}
