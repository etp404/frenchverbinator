package uk.co.mould.matt.data.tenses;

public final class PresentIndicative extends VerbMoodAndTenseAbstract {
        @Override
        public String getMood() {
            return "indicative";
        }

        @Override
        public String getTense() {
            return "present";
        }
}
