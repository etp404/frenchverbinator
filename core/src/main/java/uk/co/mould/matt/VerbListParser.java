package uk.co.mould.matt;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class VerbListParser {

	private final Map<InfinitiveVerb, VerbTemplate> verbToTemplate;

	public VerbListParser(File verbsFile) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(verbsFile);
		doc.getDocumentElement().normalize();
		NodeList verbsList = doc.getElementsByTagName("v");

		verbToTemplate = new HashMap<>();
		for (int temp = 0; temp < verbsList.getLength(); temp++) {
			Element item = (Element)verbsList.item(temp);
			verbToTemplate.put(
					new InfinitiveVerb(item.getElementsByTagName("i").item(0).getTextContent()),
					new VerbTemplate(item.getElementsByTagName("t").item(0).getTextContent()));
		}
	}

	public VerbTemplate getTemplateForVerb(InfinitiveVerb verb) {
		return verbToTemplate.get(verb);
	}
}
