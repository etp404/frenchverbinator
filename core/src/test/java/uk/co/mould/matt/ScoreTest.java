package uk.co.mould.matt;

import org.junit.Test;

import uk.co.mould.matt.marking.Score;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public final class ScoreTest {
    @Test
    public void testThatInitialScoreIsZeroOfZero() {
        assertThat(new Score().toString(), is("0/0"));
    }

    @Test
    public void testThatScoringAnIncorrectAnswerGivesExpectedScore() {
        Score score = new Score();
        score.addIncorrect();
        assertThat(score.toString(), is("0/1"));
    }

    @Test
    public void testThatScoringTwoCorrectAnswersGivesExpectedScore() {
        Score score = new Score();
        score.addCorrect();
        score.addCorrect();
        assertThat(score.toString(), is("2/2"));
    }

    @Test
    public void testThatScoringTwoCorrectAndOneIncorrectAnswersGivesExpectedScore() {
        Score score = new Score();
        score.addCorrect();
        score.addCorrect();
        score.addIncorrect();
        assertThat(score.toString(), is("2/3"));
    }

}
