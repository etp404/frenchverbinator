package uk.co.mould.matt.data;

public final class VerbTemplate {
	private String root;
	private String infiniteEnding;

	private VerbTemplate(String root, String infiniteEnding) {
		this.root = root;
		this.infiniteEnding = infiniteEnding;
	}

	public static VerbTemplate fromString(String verbTemplateAsString) {
		String[] verbTemplateSplit = verbTemplateAsString.split(":");
		return new VerbTemplate(verbTemplateSplit[0], verbTemplateSplit[1]);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		VerbTemplate that = (VerbTemplate) o;

		if (root != null ? !root.equals(that.root) : that.root != null) return false;
		return !(infiniteEnding != null ? !infiniteEnding.equals(that.infiniteEnding) : that.infiniteEnding != null);

	}

	@Override
	public int hashCode() {
		int result = root != null ? root.hashCode() : 0;
		result = 31 * result + (infiniteEnding != null ? infiniteEnding.hashCode() : 0);
		return result;
	}

	public String getRootAsString() {
		return root;
	}


	public String getEndingAsString() {
		return infiniteEnding;
	}
}
