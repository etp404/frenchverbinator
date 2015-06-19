package uk.co.mould.matt.data;

import java.util.ArrayList;
import java.util.List;

import uk.co.mould.matt.data.tenses.FutureIndicative;
import uk.co.mould.matt.data.tenses.ImperfectIndicative;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.data.tenses.MoodAndTense;

public final class SupportedMoodsAndTenses {
    public static final List<MoodAndTense> ALL = new ArrayList<MoodAndTense>() {{
        add(new PresentIndicative());
        add(new ImperfectIndicative());
        add(new FutureIndicative());
    }};
}
