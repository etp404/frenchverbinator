package uk.co.mould.matt.data.tenses;

import uk.co.mould.matt.data.VerbMoodsAndTenses;

public abstract class VerbMoodAndTenseAbstract implements VerbMoodsAndTenses.VerbMoodAndTense {

    @Override
    public String toString() {
        return String.format("%s %s", getTense(), getMood());
    }
}
