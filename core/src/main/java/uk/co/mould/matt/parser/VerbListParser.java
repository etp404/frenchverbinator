package uk.co.mould.matt.parser;

import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import uk.co.mould.matt.data.InfinitiveVerb;

public final class VerbListParser {

    private final List<InfinitiveVerb> infinitiveVerbs = new ArrayList<>();

    public VerbListParser(InputSource inputSource) {
        InputStream inputStream = inputSource.getByteStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(",");
                infinitiveVerbs.add(new InfinitiveVerb(splitLine[0], splitLine[1]));
            }
        } catch (IOException ignored) {

        }
    }

    public List<InfinitiveVerb> getVerbs() {
        return infinitiveVerbs;
    }
}
