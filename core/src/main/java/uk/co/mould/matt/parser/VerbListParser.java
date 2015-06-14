package uk.co.mould.matt.parser;

import org.xml.sax.InputSource;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import uk.co.mould.matt.data.QuestionVerb;

public final class VerbListParser {

    private final List<QuestionVerb> questionVerbs = new ArrayList<>();

    public VerbListParser(InputSource inputSource) {
        InputStream inputStream = inputSource.getByteStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(",");
                questionVerbs.add(new QuestionVerb(splitLine[0], splitLine[1]));
            }
        } catch (IOException ignored) {

        }
    }

    public List<QuestionVerb> getVerbs() {
        return questionVerbs;
    }
}
