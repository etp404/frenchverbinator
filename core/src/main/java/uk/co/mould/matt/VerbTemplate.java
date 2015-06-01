package uk.co.mould.matt;

public final class VerbTemplate {
	private String verbTemplate;

	public VerbTemplate(String verbTemplate) {
		this.verbTemplate = verbTemplate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		VerbTemplate that = (VerbTemplate) o;

		return !(verbTemplate != null ? !verbTemplate.equals(that.verbTemplate) : that.verbTemplate != null);

	}

	@Override
	public int hashCode() {
		return verbTemplate != null ? verbTemplate.hashCode() : 0;
	}
}
