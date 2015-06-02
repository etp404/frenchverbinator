package uk.co.mould.matt.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import uk.co.mould.matt.data.Conjugation;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.VerbTemplate;

public final class ConjugationParser {

	private final static Map<Persons.Person, Integer> PERSON_TO_INDEX = new HashMap<Persons.Person, Integer>(){{
		put(Persons.FIRST_PERSON_SINGULAR, 0);
		put(Persons.SECOND_PERSON_SINGULAR, 1);
		put(Persons.THIRD_PERSON_SINGULAR, 2);
		put(Persons.FIRST_PERSON_PLURAL, 3);
		put(Persons.SECOND_PERSON_PLURAL, 4);
		put(Persons.THIRD_PERSON_PLURAL, 5);
	}};

	private final HashMap<VerbTemplate, Element> templateToNode;

	public ConjugationParser(InputSource conjugationFile) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(conjugationFile);
		doc.getDocumentElement().normalize();

		NodeList templateList = doc.getElementsByTagName("template");

		templateToNode = new HashMap<>();
		for (int ind = 0; ind < templateList.getLength(); ind++) {
			Element item = (Element)templateList.item(ind);
			templateToNode.put(
					VerbTemplate.fromString(item.getAttribute("name")),
					item);
		}
	}

	public Conjugation getConjugation(InfinitiveVerb infinitiveVerb, VerbTemplate template, Persons.Person person) {
		Element element = templateToNode.get(template);
		String conjugatedEnding = ((Element) element.getElementsByTagName("indicative").item(0)).getElementsByTagName("i").item(PERSON_TO_INDEX.get(person)).getTextContent();

		return new Conjugation(infinitiveVerb.toString().replace(template.getEndingAsString(), conjugatedEnding));
	}
}
