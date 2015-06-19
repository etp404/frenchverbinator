package uk.co.mould.matt.data.tenses;

public abstract class VerbMoodsAndTenses {
    abstract public String getMood();
    abstract public String getTense();
    @Override
    public String toString() {
        return String.format("%s %s", getTense(), getMood());
    }
}
