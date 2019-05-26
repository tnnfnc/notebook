package it.tnnfnc.mynotebook.plugins.mindjet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import it.tnnfnc.mynotebook.Book;
import it.tnnfnc.mynotebook.Element;
import it.tnnfnc.mynotebook.plugins.I_NotesParser;

/**
 * Parser for the MindJet <code>file.txt</code> file. It extracts the list of
 * notes. The format is as follows:
 * <ol>
 * <li>Record line = 1.1.1...1, content</li>
 * </ol>
 * The chapter level separator is "." and the character separating the chapter
 * level from the content is the ",".
 * 
 * @author Franco Toninato
 * 
 */
public class MindJetParser implements I_NotesParser {
	// private SimpleDateFormat sdf;
	private String LEVEL_SEPARATOR;
	private String CONTENT_SEPARATOR;
	private HashMap<String, Book> library;
	private Book book;
	// private Element element;

	private int startPosition;
	// private int endPosition;

	private ArrayList<Element> hierarchy;

	public Map<String, Book> parse(InputStream input, Properties param) {
		// Initializes
		book = null;
		library = null;
		hierarchy = new ArrayList<Element>();

		LEVEL_SEPARATOR = param.getProperty(MindJetConstants.PREF_LEVEL_SEPARATOR);
		CONTENT_SEPARATOR = param.getProperty(MindJetConstants.PREF_CONTENT_SEPARATOR);
		// sdf = new
		// SimpleDateFormat(param.getProperty(MindJetConstants.PREF_DATE_PATTERN),
		// Locale.getDefault());

		BufferedReader inputStream = null;
		try {
			inputStream = new BufferedReader(new InputStreamReader(input, "UTF8"));
			String line;
			if ((line = inputStream.readLine()) != null) {
				/*
				 * Create the library, the book and a node with the root
				 */
				beginOfBook(line);
			}
			while ((line = inputStream.readLine()) != null) {
				parseLine(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return library;
	}

	/**
	 * Parse a line. The chapter level separator is "." and the character
	 * separating the chapter level from the content is the ",".
	 * 
	 * @param line
	 *            the line of text.
	 */
	public void parseLine(String line) {
		try {
			String hierarchyLevel = line.substring(0, line.indexOf(CONTENT_SEPARATOR, 0)).trim();
			// Split
			String[] levels = hierarchyLevel.split(LEVEL_SEPARATOR);

			// The first level hasn't any dot!
			if (levels.length == 0) {
				return;
			}

			// New element
			Element element = new Element();
			String content = line
					.substring(line.indexOf(CONTENT_SEPARATOR, 0) > -1 ? line.indexOf(CONTENT_SEPARATOR, 0) + 1 : 0)
					.trim();
			element.setContent(content);
			element.setLevel(hierarchyLevel);
			// if (levels.length == 1) {
			// element.setType(Element.Annotation.CHAPTER);
			// } else {

			element.setType(Element.Annotation.EMPHASIZED);
			// }

			element.setStartPosition(startPosition++);
			element.setEndPosition(startPosition);
			// element.setNumber(hierarchyLevel);
			// Element element = new Element();
			// element.setType(type);
			// element.setStartPosition(startPosition);
			// element.setEndPosition(endPosition);
			// element.setContent(note);
			// element.setComment(comment);
			// element.setDate(date);
			// element.setLevel(level);
			// element.setNumber(elementNumber);

			/*
			 * Add an element when new or add a child
			 */
			try {
				hierarchy.get(levels.length); // Element 0 is the book
				// The level exists, update the last hierarchy entry
				hierarchy.set(levels.length, element);
				// Delete further levels
				while (hierarchy.size() > levels.length) {
					hierarchy.remove(hierarchy.size());
				}
			} catch (Exception err) {
				hierarchy.add(element);
				// Add the element to its parent
				hierarchy.get(levels.length - 1).add(element);
			}
		} catch (Exception err) {
			// Wrong lines
		}

	}

	private void beginOfBook(String line) {
		if (book == null) {
			String content = line
					.substring(line.indexOf(CONTENT_SEPARATOR, 0) > -1 ? line.indexOf(CONTENT_SEPARATOR, 0) + 1 : 0)
					.trim();
			book = new Book();
			book.setTitle(content);
			library = new HashMap<String, Book>();
			library.put(content, book);

			// New element
			Element element = new Element();

			element.setContent(content);
			element.setLevel("0");
			element.setType(Element.Annotation.CHAPTER);
			element.setStartPosition(startPosition++);
			element.setEndPosition(startPosition);
			element.setNumber("0");
			// Set the root
			hierarchy.add(null);
			hierarchy.add(element);
			//
			book.add(element);
		}
	}

}
