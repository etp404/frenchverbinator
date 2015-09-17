package uk.co.mould.matt.marking;

public class Score {
    int correct = 0;
    int incorrect = 0;
    @Override
    public String toString() {
        return String.format("%d/%d", correct, correct+incorrect);
    }

    public void addIncorrect() {
        incorrect++;
    }

    public void addCorrect() {
        correct++;
    }
}
