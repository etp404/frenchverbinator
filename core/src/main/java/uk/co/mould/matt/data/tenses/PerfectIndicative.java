package uk.co.mould.matt.data.tenses;

public final class PerfectIndicative extends MoodAndTense {
    @Override
    public String getMood() {
        return "indicative";
    }

    @Override
    public String getTense() {
        return "perfect";
    }
}
