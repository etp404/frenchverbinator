package uk.co.mould.matt.data.tenses;

public final class PresentIndicative extends VerbMoodsAndTenses {
        @Override
        public String getMood() {
            return "indicative";
        }

        @Override
        public String getTense() {
            return "present";
        }
}
