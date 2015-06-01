package uk.co.mould.matt.data;

public final class ConjugatedVerbWithPronoun {
	private String conjugatedVerb;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ConjugatedVerbWithPronoun that = (ConjugatedVerbWithPronoun) o;

		return !(conjugatedVerb != null ? !conjugatedVerb.equals(that.conjugatedVerb) : that.conjugatedVerb != null);

	}

	@Override
	public int hashCode() {
		return conjugatedVerb != null ? conjugatedVerb.hashCode() : 0;
	}

	public ConjugatedVerbWithPronoun(String conjugatedVerb) {
		this.conjugatedVerb = conjugatedVerb;
	}
}
