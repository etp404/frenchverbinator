package uk.co.mould.matt.data;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Persons {

    private static final Map<String, Person> STRING_TO_PERSON = new HashMap<String, Person>() {{
        put(FIRST_PERSON_SINGULAR.toString(), FIRST_PERSON_SINGULAR);
        put(SECOND_PERSON_SINGULAR.toString(), SECOND_PERSON_SINGULAR);
        put(THIRD_PERSON_SINGULAR.toString(), THIRD_PERSON_SINGULAR);
        put(FIRST_PERSON_PLURAL.toString(), FIRST_PERSON_PLURAL);
        put(SECOND_PERSON_PLURAL.toString(), SECOND_PERSON_PLURAL);
        put(THIRD_PERSON_PLURAL.toString(), THIRD_PERSON_PLURAL);
    }};

    public static Person fromString(String person) {
        return STRING_TO_PERSON.get(person);
    }

    public static List<Person> getAllSupportedPersons() {
        return new ArrayList<>(STRING_TO_PERSON.values());
    }

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
