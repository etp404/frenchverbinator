package uk.co.mould.matt.parser;

import org.junit.Test;
import org.xml.sax.InputSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import uk.co.mould.matt.data.QuestionVerb;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class VerbListParserTest {

    @Test
    public void testThatVerbsAreReturnedAsExpected() throws FileNotFoundException {
        VerbListParser verbListParser = new VerbListParser(new InputSource(new FileInputStream("res/verb_list.csv")));

        List<QuestionVerb> expectedList = new ArrayList<QuestionVerb>(){{
            add(new QuestionVerb("être", "to be"));
            add(new QuestionVerb("avoir", "to have"));
        }};

        List<QuestionVerb> actual = verbListParser.getVerbs();
        assertThat(actual, is(expectedList));
    }
}