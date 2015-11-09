package uk.co.mould.matt.frenchverbinator.settings;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.questions.Question;

//To much complexity in here: need to split out some stuff.
public class SharedPrefsUserSettings implements StoredUserSettings {
    public static final String SETTINGS = "verbinator_prefs";
    private static final String INCLUDED_TENSES = "included_tenses";

    private static final Set<String> DEFAULT_TENSES = new HashSet<String>(){{
        add(new PresentIndicative().toString());
    }};

    private SharedPreferences sharedPreferences;

    public SharedPrefsUserSettings(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void addToIncludedTenses(MoodAndTense moodAndTense) {
        List<MoodAndTense> includedTensesList = includedTenses();
        includedTensesList.add(moodAndTense);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(INCLUDED_TENSES, convertToStringSet(includedTensesList));
        editor.apply();
    }

    @Override
    public void removeFromIncludedTenses(MoodAndTense moodAndTense) {
        List<MoodAndTense> includedTensesList = includedTenses();
        includedTensesList.remove(moodAndTense);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(INCLUDED_TENSES, convertToStringSet(includedTensesList));
        editor.apply();
    }

    @Override
    public List<MoodAndTense> includedTenses() {
        Set<String> includedTensesAsString = sharedPreferences.getStringSet(INCLUDED_TENSES,
                DEFAULT_TENSES);
        return convertToMoodAndTenseList(includedTensesAsString);
    }

    private List<MoodAndTense> convertToMoodAndTenseList(Set<String> includedTensesAsString) {
        List<MoodAndTense> tenseList = new ArrayList<>();
        for (String tense : includedTensesAsString) {
            tenseList.add(new MoodAndTenseFactory().createFromString(tense.toString()));
        }
        return tenseList;
    }


    private static Set<String> convertToStringSet(List<MoodAndTense> tenseList) {
        Set<String> tenseListAsStringSet = new HashSet<>();
        for (MoodAndTense tense : tenseList) {
            tenseListAsStringSet.add(tense.toString());
        }
        return tenseListAsStringSet;
    }

}
