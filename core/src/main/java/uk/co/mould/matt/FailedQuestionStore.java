package uk.co.mould.matt;

import java.util.List;

import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.questions.Question;

public interface FailedQuestionStore {

    void store(Question question);

    void getFailedQuestion(Callback capturingCallback, List<MoodAndTense> moodAndTenses);

    void clear();

    interface Callback {
        void success(Question question);
        void failure();
    }

}
