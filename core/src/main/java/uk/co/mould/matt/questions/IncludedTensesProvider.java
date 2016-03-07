package uk.co.mould.matt.questions;

import java.util.List;

import uk.co.mould.matt.data.tenses.MoodAndTense;

/**
 * Copyright © 2016 Media Applications Technologies. All rights reserved.
 */
public interface IncludedTensesProvider {
    List<MoodAndTense> getIncludedTenses();
}
