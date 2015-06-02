package uk.co.mould.matt.parser;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.VerbTemplate;

public class VerbListParser {

	private final Map<InfinitiveVerb, VerbTemplate> verbToTemplate;

	public VerbListParser(InputSource verbsFile) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(verbsFile);
		doc.getDocumentElement().normalize();
		NodeList verbsList = doc.getElementsByTagName("v");

		verbToTemplate = new HashMap<>();
		for (int temp = 0; temp < verbsList.getLength(); temp++) {
			Element item = (Element)verbsList.item(temp);
			verbToTemplate.put(
					InfinitiveVerb.fromString(item.getElementsByTagName("i").item(0).getTextContent()),
					VerbTemplate.fromString(item.getElementsByTagName("t").item(0).getTextContent()));
		}
	}

	public VerbTemplate getTemplateForVerb(InfinitiveVerb verb) {
		return verbToTemplate.get(verb);
	}

	public List<InfinitiveVerb> getVerbs() {
		return new ArrayList<InfinitiveVerb>(verbToTemplate.keySet());
	}
}
