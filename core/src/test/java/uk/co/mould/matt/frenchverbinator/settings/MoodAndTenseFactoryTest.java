package uk.co.mould.matt.frenchverbinator.settings;


import org.junit.Test;

import uk.co.mould.matt.data.tenses.MoodAndTense;
import uk.co.mould.matt.data.tenses.PresentIndicative;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class MoodAndTenseFactoryTest {
    private MoodAndTenseFactory moodAndTenseFactory = new MoodAndTenseFactory();

    @Test
    public void testThatPresentIndicativeCanBeCreatedFromString() {
        MoodAndTense moodAndTense = moodAndTenseFactory.createFromString(new PresentIndicative().toString());
        assertThat(moodAndTense, instanceOf(PresentIndicative.class));
    }
}