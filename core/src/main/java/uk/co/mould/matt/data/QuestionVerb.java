package uk.co.mould.matt.data;

public final class QuestionVerb {

    public final FrenchInfinitiveVerb frenchVerb;
    private final String englishVerb;

    public QuestionVerb(String frenchVerb, String englishVerb) {
        this.frenchVerb = FrenchInfinitiveVerb.fromString(frenchVerb);
        this.englishVerb = englishVerb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionVerb that = (QuestionVerb) o;

        if (frenchVerb != null ? !frenchVerb.equals(that.frenchVerb) : that.frenchVerb != null)
            return false;
        return !(englishVerb != null ? !englishVerb.equals(that.englishVerb) : that.englishVerb != null);

    }

    @Override
    public int hashCode() {
        int result = frenchVerb != null ? frenchVerb.hashCode() : 0;
        result = 31 * result + (englishVerb != null ? englishVerb.hashCode() : 0);
        return result;
    }
}
