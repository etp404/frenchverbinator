package uk.co.mould.matt.data;

import java.text.Normalizer;

public class Persons {


	public interface Person {
		String getPronoun(String conjugatedVerb);
		String getPerson();
	}

	public static final Person FIRST_PERSON_SINGULAR = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
            String verbsWithAccentSplitOut = Normalizer.normalize(conjugatedVerb,
                    Normalizer.Form.NFD);
            if (verbsWithAccentSplitOut.matches("[a,e,i,o,u].+")) {
				return "J'";
			}
			return "Je ";
		}

		public String getPerson() {
			return "je";
		}
	};
	public static final Person SECOND_PERSON_SINGULAR = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Tu ";
		}

		@Override
		public String getPerson() {
			return "tu";
		}
	};
	public static final Person THIRD_PERSON_SINGULAR = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Il ";
		}

		@Override
		public String getPerson() {
			return "il";
		}
	};
	public static final Person FIRST_PERSON_PLURAL = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Nous ";
		}

		@Override
		public String getPerson() {
			return "nous";
		}
	};
	public static final Person SECOND_PERSON_PLURAL = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Vous ";
		}

		@Override
		public String getPerson() {
			return "vous";
		}
	};
	public static final Person THIRD_PERSON_PLURAL = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Ils ";
		}

		@Override
		public String getPerson() {
			return "ils";
		}
	};

}
