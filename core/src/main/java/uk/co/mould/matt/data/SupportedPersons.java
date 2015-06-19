package uk.co.mould.matt.data;

import java.util.ArrayList;

public final class SupportedPersons {
    public static final ArrayList<Persons.Person> ALL = new ArrayList<Persons.Person>() {{
        add(Persons.FIRST_PERSON_SINGULAR);
        add(Persons.SECOND_PERSON_SINGULAR);
        add(Persons.THIRD_PERSON_SINGULAR);
        add(Persons.FIRST_PERSON_PLURAL);
        add(Persons.SECOND_PERSON_PLURAL);
        add(Persons.THIRD_PERSON_PLURAL);
    }};
}
