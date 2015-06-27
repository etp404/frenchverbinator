package uk.co.mould.matt.data.tenses;

public abstract class MoodAndTense {
    abstract public String getMood();
    abstract public String getTense();
    @Override
    public String toString() {
        return String.format("%s %s", getTense(), getMood());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            return true;
        }
        return false;
    }
}
