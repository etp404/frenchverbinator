package uk.co.mould.matt.data;

import java.util.ArrayList;
import java.util.List;

import uk.co.mould.matt.data.tenses.PresentIndicative;

public final class SupportedMoodsAndTenses {
    public static final List<VerbMoodsAndTenses.VerbMoodAndTense> ALL = new ArrayList<VerbMoodsAndTenses.VerbMoodAndTense>() {{
        add(new PresentIndicative());
        add(VerbMoodsAndTenses.IMPERFECT_INDICATIVE);
        add(VerbMoodsAndTenses.FUTURE_INDICATIVE);
    }};
}
