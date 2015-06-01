package uk.co.mould.matt;

public final class Conjugation {
	public final String infinitiveEnding;
	public final String ending;

	public Conjugation(String infinitiveEnding, String ending) {
		this.infinitiveEnding = infinitiveEnding;
		this.ending = ending;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Conjugation that = (Conjugation) o;

		if (infinitiveEnding != null ? !infinitiveEnding.equals(that.infinitiveEnding) : that.infinitiveEnding != null)
			return false;
		return !(ending != null ? !ending.equals(that.ending) : that.ending != null);

	}

	@Override
	public int hashCode() {
		int result = infinitiveEnding != null ? infinitiveEnding.hashCode() : 0;
		result = 31 * result + (ending != null ? ending.hashCode() : 0);
		return result;
	}
}
