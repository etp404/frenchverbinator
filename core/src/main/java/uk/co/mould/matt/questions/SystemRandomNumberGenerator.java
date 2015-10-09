package uk.co.mould.matt.questions;

import java.util.Random;

public class SystemRandomNumberGenerator implements RandomNumberGenerator {
    public int randomNumber(int from, int to) {
        Random r = new Random();
        r.setSeed(System.currentTimeMillis());
        return r.nextInt(to-from) + from;
    }
}
