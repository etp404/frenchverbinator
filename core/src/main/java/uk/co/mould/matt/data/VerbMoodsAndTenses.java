package uk.co.mould.matt.data;

public class VerbMoodsAndTenses {

    public interface VerbMoodAndTense {
        String getMood();
        String getTense();
    }

    public static final VerbMoodAndTense PRESENT_INDICATIVE = new VerbMoodAndTense() {
        @Override
        public String getMood() {
            return "indicative";
        }

        @Override
        public String getTense() {
            return "present";
        }
    };

    public static final VerbMoodAndTense IMPERFECT_INDICATIVE = new VerbMoodAndTense() {
        @Override
        public String getMood() {
            return "indicative";
        }

        @Override
        public String getTense() {
            return "imperfect";
        }
    };

    public static final VerbMoodAndTense FUTURE_INDICATIVE = new VerbMoodAndTense() {
        @Override
        public String getMood() {
            return "indicative";
        }

        @Override
        public String getTense() {
            return "future";
        }
    };

    public static final VerbMoodAndTense PRESENT_SUBJUNCTIVE =  new VerbMoodAndTense() {
        @Override
        public String getMood() {
            return "subjunctive";
        }

        @Override
        public String getTense() {
            return "present";
        }
    };

}
