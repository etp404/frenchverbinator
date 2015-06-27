package uk.co.mould.matt.frenchverbinator.settings;

import java.util.HashMap;
import java.util.Map;

import uk.co.mould.matt.data.tenses.FutureIndicative;
import uk.co.mould.matt.data.tenses.ImperfectIndicative;
import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentConditional;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.data.tenses.PresentSubjunctive;

public class MoodAndTenseFactory {

    private Map<String, ParticularTenseFactory> tenseStringToBuilder = new HashMap<>();
    public MoodAndTenseFactory() {
        tenseStringToBuilder.put(new PresentIndicative().toString(), new ParticularTenseFactory(){

            @Override
            public MoodAndTense create() {
                return new PresentIndicative();
            }
        });

        tenseStringToBuilder.put(new PresentConditional().toString(), new ParticularTenseFactory(){

            @Override
            public MoodAndTense create() {
                return new PresentConditional();
            }
        });

        tenseStringToBuilder.put(new PresentSubjunctive().toString(), new ParticularTenseFactory(){

            @Override
            public MoodAndTense create() {
                return new PresentSubjunctive();
            }
        });

        tenseStringToBuilder.put(new ImperfectIndicative().toString(), new ParticularTenseFactory(){

            @Override
            public MoodAndTense create() {
                return new ImperfectIndicative();
            }
        });

        tenseStringToBuilder.put(new FutureIndicative().toString(), new ParticularTenseFactory(){

            @Override
            public MoodAndTense create() {
                return new FutureIndicative();
            }
        });
    }

    public MoodAndTense createFromString(String moodAndTenseAsString) {
        return tenseStringToBuilder.get(moodAndTenseAsString).create();
    }

    private interface ParticularTenseFactory {
        MoodAndTense create();
    }
}
