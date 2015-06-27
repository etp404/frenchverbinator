package uk.co.mould.matt.frenchverbinator.settings;


import org.junit.Test;

import uk.co.mould.matt.data.SupportedMoodsAndTenses;
import uk.co.mould.matt.data.tenses.MoodAndTense;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class MoodAndTenseFactoryTest {
    private MoodAndTenseFactory moodAndTenseFactory = new MoodAndTenseFactory();

    @Test
    public void testThatAllSupportedTensesCanBeCreatedFromString() {
        for (MoodAndTense moodAndTenseExpected : SupportedMoodsAndTenses.ALL) {
            MoodAndTense moodAndTenseCreated = null;
            try {
                moodAndTenseCreated = moodAndTenseFactory.createFromString(
                        moodAndTenseExpected.toString());
            } catch(Exception ignored){}

            assertThat(moodAndTenseCreated, instanceOf(moodAndTenseExpected.getClass()));
        }
    }

}