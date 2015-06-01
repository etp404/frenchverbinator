package uk.co.mould.matt;

public final class Conjugation {
	public final String conjugatedVerb;

	public Conjugation(String conjugatedVerb) {
		this.conjugatedVerb = conjugatedVerb;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Conjugation that = (Conjugation) o;

		return !(conjugatedVerb != null ? !conjugatedVerb.equals(that.conjugatedVerb) : that.conjugatedVerb != null);

	}

	@Override
	public int hashCode() {
		return conjugatedVerb != null ? conjugatedVerb.hashCode() : 0;
	}
}
