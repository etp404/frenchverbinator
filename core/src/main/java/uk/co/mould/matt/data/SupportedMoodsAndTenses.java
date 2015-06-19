package uk.co.mould.matt.data;

import java.util.ArrayList;
import java.util.List;

public final class SupportedMoodsAndTenses {
    public static final List<VerbMoodsAndTenses.VerbMoodAndTense> ALL = new ArrayList<VerbMoodsAndTenses.VerbMoodAndTense>() {{
        add(VerbMoodsAndTenses.PRESENT_INDICATIVE);
        add(VerbMoodsAndTenses.IMPERFECT_INDICATIVE);
        add(VerbMoodsAndTenses.FUTURE_INDICATIVE);
    }};
}
