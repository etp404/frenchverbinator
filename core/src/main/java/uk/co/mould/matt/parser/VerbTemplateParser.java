package uk.co.mould.matt.parser;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import uk.co.mould.matt.data.FrenchInfinitiveVerb;
import uk.co.mould.matt.data.VerbTemplate;

public class VerbTemplateParser {

	private final Map<FrenchInfinitiveVerb, VerbTemplate> verbToTemplate;

	public VerbTemplateParser(InputSource verbsFile) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(verbsFile);
			doc.getDocumentElement().normalize();
			NodeList verbsList = doc.getElementsByTagName("v");

			verbToTemplate = new HashMap<>();
			for (int temp = 0; temp < verbsList.getLength(); temp++) {
				Element item = (Element)verbsList.item(temp);
				verbToTemplate.put(
						FrenchInfinitiveVerb.fromString(item.getElementsByTagName("i").item(0).getTextContent()),
						VerbTemplate.fromString(item.getElementsByTagName("t").item(0).getTextContent()));
			}
		} catch (ParserConfigurationException|SAXException|IOException e) {
			throw new RuntimeException("Could not initiate verbs list parser.");
		}
	}

	public VerbTemplate getTemplateForVerb(FrenchInfinitiveVerb verb) {
		return verbToTemplate.get(verb);
	}

	public List<FrenchInfinitiveVerb> getVerbs() {
		return new ArrayList<FrenchInfinitiveVerb>(verbToTemplate.keySet());
	}
}
