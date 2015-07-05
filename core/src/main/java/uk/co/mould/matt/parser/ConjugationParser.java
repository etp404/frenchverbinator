package uk.co.mould.matt.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import uk.co.mould.matt.CantConjugateException;
import uk.co.mould.matt.data.Conjugation;
import uk.co.mould.matt.data.FrenchInfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.VerbTemplate;
import uk.co.mould.matt.data.tenses.MoodAndTense;

public final class ConjugationParser {

	private final static Map<Persons.Person, Integer> PERSON_TO_INDEX = new HashMap<Persons.Person, Integer>(){{
		put(Persons.FIRST_PERSON_SINGULAR, 0);
		put(Persons.SECOND_PERSON_SINGULAR, 1);
		put(Persons.THIRD_PERSON_SINGULAR, 2);
		put(Persons.FIRST_PERSON_PLURAL, 3);
		put(Persons.SECOND_PERSON_PLURAL, 4);
		put(Persons.THIRD_PERSON_PLURAL, 5);
	}};

	private final HashMap<VerbTemplate, Element> templateToNode = new HashMap<>();

	public ConjugationParser(InputSource conjugationFile) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(conjugationFile);
			doc.getDocumentElement().normalize();

			NodeList templateList = doc.getElementsByTagName("template");

			for (int ind = 0; ind < templateList.getLength(); ind++) {
				Element item = (Element)templateList.item(ind);
				templateToNode.put(
						VerbTemplate.fromString(item.getAttribute("name")),
						item);
			}
		} catch (ParserConfigurationException|SAXException|IOException e) {
			throw new RuntimeException("Could not initiate conjugation stream");
		}
	}

    public Conjugation getConjugation(FrenchInfinitiveVerb frenchInfinitiveVerb, VerbTemplate template, Persons.Person person, MoodAndTense verbMoodAndTense) throws CantConjugateException {
        Element element = templateToNode.get(template);
        if (element == null) {
            throw new CantConjugateException("Could not conjugate " + frenchInfinitiveVerb.toString());
        }
        Element indicativeVerbNode = (Element) element.getElementsByTagName(verbMoodAndTense.getMood()).item(0);
        Element verbEndings = (Element) indicativeVerbNode.getElementsByTagName(verbMoodAndTense.getTense()).item(0);

        String conjugatedEnding = verbEndings.getElementsByTagName("i").item(PERSON_TO_INDEX.get(person)).getTextContent();

        return new Conjugation(frenchInfinitiveVerb.toString().replace(template.getEndingAsString(), conjugatedEnding));
    }

    public Conjugation getPerfectParticiple(FrenchInfinitiveVerb frenchInfinitiveVerb, VerbTemplate template) throws CantConjugateException {
        Element element = templateToNode.get(template);
        if (element == null) {
            throw new CantConjugateException("Could not conjugate " + frenchInfinitiveVerb.toString());
        }
        Element indicativeVerbNode = (Element) element.getElementsByTagName("participle").item(0);
        Element verbEndings = (Element) indicativeVerbNode.getElementsByTagName("past-participle").item(0);

        String conjugatedEnding = verbEndings.getElementsByTagName("i").item(0).getTextContent();

        return new Conjugation(frenchInfinitiveVerb.toString().replace(template.getEndingAsString(), conjugatedEnding));
    }
}
