package uk.co.mould.matt.data;

public class Persons {
	public interface Person {
		String getPronoun(String conjugatedVerb);
		String getEnglishPronoun();
	}

	public static Person FIRST_PERSON_SINGULAR = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			if (conjugatedVerb.matches("[a,e,i,o,u].+")) {
				return "J'";
			}
			return "Je ";
		}

		@Override
		public String getEnglishPronoun() {
			return "I";
		}
	};
	public static Person SECOND_PERSON_SINGULAR = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Tu ";
		}

		@Override
		public String getEnglishPronoun() {
			return "You (singular)";
		}
	};
	public static Person THIRD_PERSON_SINGULAR = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Il ";
		}

		@Override
		public String getEnglishPronoun() {
			return "He";
		}
	};
	public static Person FIRST_PERSON_PLURAL = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Nous ";
		}

		@Override
		public String getEnglishPronoun() {
			return "We";
		}
	};
	public static Person SECOND_PERSON_PLURAL = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Vous ";
		}

		@Override
		public String getEnglishPronoun() {
			return "You (plural)";
		}
	};
	public static Person THIRD_PERSON_PLURAL = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Ils ";
		}

		@Override
		public String getEnglishPronoun() {
			return "They";
		}
	};

}
