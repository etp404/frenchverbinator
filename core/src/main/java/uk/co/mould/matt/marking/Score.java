package uk.co.mould.matt.marking;

public class Score {
    int correct = 0;
    int incorrect = 0;
    @Override
    public String toString() {
        return String.format("You've scored %d out of %d correct", correct, correct+incorrect);
    }

    public void addIncorrect() {
        incorrect++;
    }

    public void addCorrect() {
        correct++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score = (Score) o;

        if (correct != score.correct) return false;
        return incorrect == score.incorrect;

    }

    @Override
    public int hashCode() {
        int result = correct;
        result = 31 * result + incorrect;
        return result;
    }
}
