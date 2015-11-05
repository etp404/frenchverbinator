package uk.co.mould.matt;

import uk.co.mould.matt.questions.RandomNumberGenerator;

public class FakeRandomQuestionGenerator implements RandomNumberGenerator {
    @Override
    public int randomNumber(int from, int to) {
        return 0;
    }
}
