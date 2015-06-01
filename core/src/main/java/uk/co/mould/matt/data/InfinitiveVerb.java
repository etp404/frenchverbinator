package uk.co.mould.matt.data;

public final class InfinitiveVerb {
	private String infinitiveAsString;

	public InfinitiveVerb(String infinitiveAsString) {
		this.infinitiveAsString = infinitiveAsString;
	}

	@Override
	public String toString() {
		return infinitiveAsString;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		InfinitiveVerb that = (InfinitiveVerb) o;

		return !(infinitiveAsString != null ? !infinitiveAsString.equals(that.infinitiveAsString) : that.infinitiveAsString != null);

	}

	@Override
	public int hashCode() {
		return infinitiveAsString != null ? infinitiveAsString.hashCode() : 0;
	}

}
