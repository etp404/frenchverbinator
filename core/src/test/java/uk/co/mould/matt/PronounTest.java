package uk.co.mould.matt;

import org.junit.Test;

import uk.co.mould.matt.conjugators.PronounHandler;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.Conjugation;
import uk.co.mould.matt.data.Persons;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public final class PronounTest {

    @Test
    public void testThatCorrectPronounIsAddedForFirstPersonWithAccent() {
        PronounHandler pronounHandler = new PronounHandler();
        ConjugatedVerbWithPronoun verbWithPronoun = pronounHandler.addPronoun(
                new Conjugation("étais"), Persons.FIRST_PERSON_SINGULAR);

        assertThat(verbWithPronoun.toString(), is("J'étais"));
    }
}
