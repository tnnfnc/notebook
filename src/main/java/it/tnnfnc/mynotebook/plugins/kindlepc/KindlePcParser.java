package it.tnnfnc.mynotebook.plugins.kindlepc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import it.tnnfnc.mynotebook.Book;
import it.tnnfnc.mynotebook.Element;
import it.tnnfnc.mynotebook.plugins.I_NotesParser;

/**
 * Parsing schema:
 * <ul>
 * <li>Heading: a 'div' element of class='noteHeading'</li>
 * <li>Note text: a 'div' element of class='noteText'</li>
 * <li>Pattern to localize a chapter:
 * <ol>
 * <li>a 'div' element of class='noteHeading',</li>
 * <li>a 'div' element of class='noteText' = <b>title</b>,</li>
 * <li>a 'div' element of class='noteHeading',</li>
 * <li>a 'div' element of class='noteText' = <b>�</b></li>
 * </ol>
 * </li>
 * <li>How to localize a position: in a 'div' element of class='noteHeading' the
 * '-' character is followed by the position number</li>
 * </ul>
 * 
 * @author FT
 *
 */
public class KindlePcParser extends DefaultHandler implements I_NotesParser {

	public static final String elementDiv = "div";
	public static final String bookTitle = "bookTitle";
	public static final String authors = "authors";
	public static final String noteHeading = "noteHeading";
	public static final String noteText = "noteText";
	public static final String attributeClass = "class";
	public static final String elementSpan = "span";
	public static final String highlight_yellow = "highlight_yellow";
	private Attributes attributes;
	private StringBuffer buffer;
	private Book book;
	private Element element;
	private String POSITION_DELIMITER;
	private boolean DEBUG = false;
	private boolean isPosition;

	@Override
	public Map<String, Book> parse(InputStream input, Properties param) throws IOException {

		POSITION_DELIMITER = param.getProperty(KindlePcConstants.PREF_POSITION_DELIMITER);

		Map<String, Book> books = new HashMap<>();

		XMLReader reader;

		try {
			// create new factory is ordinary way:
			SAXParserFactory factory = SAXParserFactory.newInstance();
			if (DEBUG) {
				String features[] = { "http://xml.org/sax/features/validation", //
						"http://xml.org/sax/features/external-general-entities", //
						"http://xml.org/sax/features/external-parameter-entities", //
						"http://xml.org/sax/features/use-entity-resolver2", //
						"http://xml.org/sax/features/resolve-dtd-uris", //
						"http://xml.org/sax/features/lexical-handler/parameter-entities", //
						"http://xml.org/sax/features/namespace-prefixes", //
						"http://xml.org/sax/features/string-interning", //
						"http://xml.org/sax/features/validation", //
						"http://xml.org/sax/features/external-general-entities", //
						"http://xml.org/sax/features/external-parameter-entities", //
						"http://xml.org/sax/features/namespaces", //
						"http://apache.org/xml/features/nonvalidating/load-external-dtd", //
						"http://apache.org/xml/features/nonvalidating/load-dtd-grammar", //
						"http://apache.org/xml/features/validation/dynamic", //
						"http://apache.org/xml/features/validation/schema/augment-psvi", //
				};
				for (int i = 0; i < features.length; i++) {
					System.out.print("\t- '" + features[i] + "' is ");
					try {
						System.out.println("'" + factory.getFeature(features[i]) + "'");
					} catch (org.xml.sax.SAXNotRecognizedException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			SAXParser parser = factory.newSAXParser();
			reader = parser.getXMLReader();
			reader.setContentHandler(this);
			reader.setErrorHandler(this);
			// Disable resolving of external DTD
			reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		} catch (SAXException | ParserConfigurationException e) {
			IOException ee = new IOException(e.getMessage());
			ee.initCause(e);
			throw ee;
		}
		// Reads the input and converts the XML content to an array of objects.
		// Creates an input source from the input stream.
		try {
			InputSource inputSource = new InputSource(new BufferedReader(new InputStreamReader(input)));
			// inputSource.setEncoding("UTF-8");
			reader.parse(inputSource);
			// reader.parse(new InputSource(new InputStreamReader(input)));
			books.put(book.getTitle(), book);
			if (DEBUG)
				System.out.println("Encoding = " + inputSource.getEncoding());
		} catch (SAXException | IOException e) {
			throw new IOException(e.getMessage());
		}
		return books;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		String temp = new String(ch, start, length);
		try {
			buffer.append(new String(temp.getBytes(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		// New Book
		book = new Book();
		buffer = new StringBuffer();
		if (DEBUG)
			System.out.println("startDocument");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		this.attributes = attributes;
		buffer.delete(0, buffer.length());
		if (DEBUG)
			System.out.println(
					"<" + qName + " " + attributeClass + "='" + attributes.getValue("", attributeClass) + "'" + ">");
		if (qName.equals(elementDiv) && attributes.getValue("", attributeClass).equals(bookTitle)) {
		} else if (qName.equals(elementDiv) && attributes.getValue("", attributeClass).equals(noteHeading)) {
			element = new Element();
			isPosition = true;
		} else if (qName.equals(elementDiv) && attributes.getValue("", attributeClass).equals(noteText)) {

		} else if (qName.equals(elementSpan) && attributes.getValue("", attributeClass).equals(highlight_yellow)) {

		} else {

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (DEBUG)
			System.out.println(buffer.toString().trim() + "</" + localName + "> qualified name = " + qName);

		if (qName.equals(elementDiv) && attributes != null) {
			String attValue = attributes.getValue("", attributeClass);
			switch (attValue) {
			case bookTitle:
				book.setTitle(buffer.toString().trim());
				break;

			case authors:
				book.setAuthor(buffer.toString().trim());
				break;

			case noteText:
				element.setContent(buffer.toString().trim());
				book.add(element);
				if (DEBUG)
					System.out.println("Element added = " + element);
				break;

			case highlight_yellow:
				if (DEBUG)
					System.out.println(highlight_yellow);
				break;

			default:
				break;
			}
		}

		if (qName.equals(elementDiv) && isPosition) {
			if (DEBUG)
				System.out.println("Position at " + buffer.toString().trim());
			// -- POSITION --//
			String[] xy = buffer.toString().trim().split(POSITION_DELIMITER);
			for (String string : xy) {
				try {
					int p = Integer.parseInt(string.replaceAll("\\D+", ""));
					if (DEBUG)
						System.out.println("position = " + p);
					element.setStartPosition(p);
					element.setEndPosition(p + 1);
					element.setType(Element.Annotation.UNKNOWN);
				} catch (NumberFormatException e) {
					// e.printStackTrace();
				}
			}
			isPosition = false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		// Auto-generated method stub
		if (DEBUG)
			System.out.println("end document = " + buffer);
		super.endDocument();
	}

	// handle references occurred in parsed XML:
	public InputSource resolveEntity(String publicId, String systemId) {
		if (DEBUG)
			System.out.println("public Id = " + publicId + "| system Id = " + systemId);
		return null;

	}

}
