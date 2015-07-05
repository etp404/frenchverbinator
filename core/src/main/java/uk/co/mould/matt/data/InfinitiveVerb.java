package uk.co.mould.matt.data;

public final class InfinitiveVerb {

    public final FrenchInfinitiveVerb frenchVerb;
    public final String englishVerb;
    public final String auxiliary;

    public InfinitiveVerb(String frenchVerb, String englishVerb) {
        this.frenchVerb = FrenchInfinitiveVerb.fromString(frenchVerb);
        this.englishVerb = englishVerb;
        this.auxiliary  = "avoir";
    }

    public InfinitiveVerb(String frenchVerb, String englishVerb, String auxiliary) {
        this.frenchVerb = FrenchInfinitiveVerb.fromString(frenchVerb);
        this.englishVerb = englishVerb;
        this.auxiliary = auxiliary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfinitiveVerb that = (InfinitiveVerb) o;

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
