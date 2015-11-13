package uk.co.mould.matt.frenchverbinator.failedquestions;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.mould.matt.data.InfinitiveVerb;

public class VerbSerialiser {

    private static final String FRENCH_VERB_KEY = "frenchVerb";
    private static final String ENGLISH_VERB_KEY = "englishVerb";
    private static final String AUXILIARY_KEY = "auxiliary";

    public static String serialiseVerb(InfinitiveVerb verb) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(FRENCH_VERB_KEY, verb.frenchVerb.toString());
        jsonObject.put(ENGLISH_VERB_KEY, verb.englishVerb);
        jsonObject.put(AUXILIARY_KEY, verb.auxiliary.toString());
        return jsonObject.toString();
    }

    public static InfinitiveVerb deserialiseVerb(String serialisedVerb) throws JSONException {
        JSONObject jsonVerb = new JSONObject(serialisedVerb);
        return new InfinitiveVerb(jsonVerb.getString(FRENCH_VERB_KEY), jsonVerb.getString(ENGLISH_VERB_KEY), jsonVerb.getString(AUXILIARY_KEY));
    }
}
