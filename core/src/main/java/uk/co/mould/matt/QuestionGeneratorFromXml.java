package uk.co.mould.matt;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.parser.VerbListParser;

public final class QuestionGeneratorFromXml implements QuestionGenerator {
	private final VerbListParser verbParser;
	private List<Persons.Person> persons = new ArrayList<Persons.Person>(){{
		add(Persons.FIRST_PERSON_SINGULAR);
		add(Persons.SECOND_PERSON_SINGULAR);
		add(Persons.THIRD_PERSON_SINGULAR);
		add(Persons.FIRST_PERSON_PLURAL);
		add(Persons.SECOND_PERSON_PLURAL);
		add(Persons.THIRD_PERSON_PLURAL);

	}};

	public QuestionGeneratorFromXml() throws IOException, SAXException, ParserConfigurationException {
		this.verbParser = new VerbListParser(new File("res/verbs-fr.xml"));
	}

	public Persons.Person getRandomPerson() {
		return persons.get(randomNumber(0, persons.size()));
	}

	public InfinitiveVerb getRandomVerb() {
		List<InfinitiveVerb> verbs = verbParser.getVerbs();
		return verbs.get(randomNumber(0,verbs.size()));
	}

	private int randomNumber(int from, int to) {
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		return r.nextInt(to-from) + from;
	}
}
