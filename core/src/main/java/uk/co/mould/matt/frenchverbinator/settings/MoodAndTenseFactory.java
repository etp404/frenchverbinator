package uk.co.mould.matt.frenchverbinator.settings;

import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentIndicative;

public class MoodAndTenseFactory {

    public MoodAndTense createFromString(String moodAndTenseAsString) {
        return new PresentIndicative();
    }
}
