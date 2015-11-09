package uk.co.mould.matt.questions;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.MoodAndTense;

public class Question {
    public final Persons.Person person;
    public final InfinitiveVerb verb;
    public final MoodAndTense moodAndTense;

    public Question(Persons.Person person, InfinitiveVerb verb, MoodAndTense moodAndTense) {
        this.person = person;
        this.verb = verb;
        this.moodAndTense = moodAndTense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (person != null ? !person.equals(question.person) : question.person != null)
            return false;
        if (verb != null ? !verb.equals(question.verb) : question.verb != null) return false;
        return !(moodAndTense != null ? !moodAndTense.equals(question.moodAndTense) : question.moodAndTense != null);

    }

    @Override
    public int hashCode() {
        int result = person != null ? person.hashCode() : 0;
        result = 31 * result + (verb != null ? verb.hashCode() : 0);
        result = 31 * result + (moodAndTense != null ? moodAndTense.hashCode() : 0);
        return result;
    }

}
