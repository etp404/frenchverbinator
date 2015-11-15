package uk.co.mould.matt.helpers;

import uk.co.mould.matt.questions.RandomNumberGenerator;

public class FakeRandomNumberGenerator implements RandomNumberGenerator {

    private int randomNumber;

    public FakeRandomNumberGenerator(int randomNumber) {
        this.randomNumber = randomNumber;
    }

    @Override
    public int randomNumber(int from, int to) {
        return randomNumber;
    }
}
