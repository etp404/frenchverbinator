package uk.co.mould.matt;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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

	public ConjugationParser(File conjugationFile) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(conjugationFile);
		doc.getDocumentElement().normalize();

		NodeList templateList = doc.getElementsByTagName("template");

		templateToNode = new HashMap<>();
		for (int ind = 0; ind < templateList.getLength(); ind++) {
			Element item = (Element)templateList.item(ind);
			templateToNode.put(
					new VerbTemplate(item.getAttribute("name")),
					item);
		}
	}

	public Conjugation getConjugator(VerbTemplate template, Persons.Person person) {
		Element element = templateToNode.get(template);
		String infinitive = ((Element) element.getElementsByTagName("infinitive-present").item(0)).getElementsByTagName("i").item(0).getTextContent();
		String ending = ((Element) element.getElementsByTagName("indicative").item(0)).getElementsByTagName("i").item(PERSON_TO_INDEX.get(person)).getTextContent();

		return new Conjugation(infinitive, ending);
	}
}
