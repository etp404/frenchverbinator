package uk.co.mould.matt;

public class Persons {
	public interface Person {
		String getPronoun(String conjugatedVerb);
	}

	public static Person FIRST_PERSON_SINGULAR = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			if (conjugatedVerb.matches("[a,e,i,o,u].+")) {
				return "J'";
			}
			return "Je ";
		}
	};
	public static Person SECOND_PERSON_SINGULAR = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Tu ";
		}
	};
	public static Person THIRD_PERSON_SINGULAR = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Il ";
		}
	};
	public static Person FIRST_PERSON_PLURAL = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Nous ";
		}
	};
	public static Person SECOND_PERSON_PLURAL = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Vous ";
		}
	};
	public static Person THIRD_PERSON_PLURAL = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Ils ";
		}
	};

}
