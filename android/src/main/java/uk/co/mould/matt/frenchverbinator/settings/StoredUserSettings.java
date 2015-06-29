package uk.co.mould.matt.frenchverbinator.settings;

import java.util.List;

import uk.co.mould.matt.data.tenses.MoodAndTense;

public interface StoredUserSettings {
    void addToIncludedTenses(MoodAndTense moodAndTense);
    void removeFromIncludedTenses(MoodAndTense moodAndTense);
    List<MoodAndTense> includedTenses();
}
