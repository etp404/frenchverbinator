package uk.co.mould.matt.data;

public class Persons {
	public interface Person {
		String getPronoun(String conjugatedVerb);
		String getPerson();
	}

	public static Person FIRST_PERSON_SINGULAR = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			if (conjugatedVerb.matches("[a,e,i,o,u].+")) {
				return "J'";
			}
			return "Je ";
		}

		public String getPerson() {
			return "first person singular (je)";
		}
	};
	public static Person SECOND_PERSON_SINGULAR = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Tu ";
		}

		@Override
		public String getPerson() {
			return "second person singular (tu)";
		}
	};
	public static Person THIRD_PERSON_SINGULAR = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "third person singular (il)";
		}

		@Override
		public String getPerson() {
			return "third person singular (il)";
		}
	};
	public static Person FIRST_PERSON_PLURAL = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Nous ";
		}

		@Override
		public String getPerson() {
			return "first person plural (nous)";
		}
	};
	public static Person SECOND_PERSON_PLURAL = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Vous ";
		}

		@Override
		public String getPerson() {
			return "second person plural (vous)";
		}
	};
	public static Person THIRD_PERSON_PLURAL = new Person() {
		@Override
		public String getPronoun(String conjugatedVerb) {
			return "Ils ";
		}

		@Override
		public String getPerson() {
			return "third person plural (ils)";
		}
	};

}
