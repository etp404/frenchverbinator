package uk.co.mould.matt.data.tenses;

public final class PresentConditional extends MoodAndTense {
    @Override
    public String getMood() {
        return "conditional";
    }

    @Override
    public String getTense() {
        return "present";
    }
}
