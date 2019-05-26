package it.tnnfnc.mynotebook.map;

import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import it.tnnfnc.mynotebook.Book;
import it.tnnfnc.mynotebook.Element;

/**
 * @author FT
 *
 */
public class FreeMindBuilder implements I_MapBuilder {

	// private OutputStream outputStream;
	private TransformerHandler handler;
	private final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM,
			Locale.getDefault());
	/**
	 * Map format.
	 */
	public static String FORMAT = "FreeMind http://freemind.sourceforge.net/";

	/**
	 * @param outputStram
	 */
	public FreeMindBuilder() {
		super();

		// Obtain a SAXTransformerFactory
		SAXTransformerFactory tf = (SAXTransformerFactory) TransformerFactory.newInstance();
		try {
			handler = tf.newTransformerHandler();
			// Set the parameters
			Transformer transformer = handler.getTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		} catch (TransformerConfigurationException e) {
			errorLog(e);
		}
	}

	public String getName() {
		return FORMAT;
	}

	public void setOutputStream(OutputStream outputStream) {
		StreamResult streamResult = new StreamResult();
		streamResult.setOutputStream(outputStream);
		handler.setResult(streamResult);
	}

	public void openBook(Book b) {
		// XML DOCUMENT
		try {
			handler.startDocument();
		} catch (SAXException err) {
			errorLog(err);
		}

		// XML ROOT
		try {
			AttributesImpl atts;
			atts = new AttributesImpl();
			atts.addAttribute("", "", FreeMindConstants.ATTR_VERSION, "CDATA", FreeMindConstants.FREE_MIND_VERSION);
			handler.startElement("", "", FreeMindConstants.MAP, atts);
		} catch (SAXException err) {
			errorLog(err);
		}
		// Book node
		try {
			openNode(b.getTitle(), "");
			// Author
			if (b.getAuthor() != null) {
				openNode(b.getAuthor(), FreeMindConstants.POSITION_RIGHT);
				handler.endElement("", "", FreeMindConstants.ELEMENT_NODE);
			}
			// Comment
			if (b.getComment() != null) {
				openNode(b.getComment(), FreeMindConstants.POSITION_RIGHT);
				handler.endElement("", "", FreeMindConstants.ELEMENT_NODE);
			}
		} catch (SAXException err) {
			errorLog(err);
		}
	}

	public void closeBook() {
		// Close the Book node
		try {
			handler.endElement("", "", FreeMindConstants.ELEMENT_NODE);
		} catch (SAXException err) {
			errorLog(err);

		}
		// Close the root
		try {
			handler.endElement("", "", FreeMindConstants.MAP);
		} catch (SAXException err) {
			errorLog(err);
		}
		// End the document
		try {
			handler.endDocument();
		} catch (SAXException err) {
			errorLog(err);
		}

	}

	public void openElement(Element element) {
		String position = "[" + element.getStartPosition() + "-" + element.getEndPosition() + "]";
		String text = null;
		// Element position
		String layout = FreeMindConstants.POSITION_RIGHT;
		try {
			// Number m.n
			int m_dot_n = element.getNumber().indexOf('.') > -1 ? element.getNumber().indexOf('.')
					: element.getNumber().length();
			int n = Integer.parseInt(element.getNumber().substring(0, m_dot_n));
			layout = n % 2 == 0 ? FreeMindConstants.POSITION_RIGHT : FreeMindConstants.POSITION_LEFT;

		} catch (NumberFormatException e) {
			layout = element.getLevel().length() % 2 == 0 ? FreeMindConstants.POSITION_RIGHT
					: FreeMindConstants.POSITION_LEFT;
		}

		switch (element.getType()) {

		case ANNOTATION:
			try {
				text = formatTextElement(position, Element.Annotation.ANNOTATION + "", element.getLevel(),
						element.getContent());
				openNode(text, FreeMindConstants.POSITION_RIGHT);
			} catch (SAXException err) {
				errorLog(err);
			}
			break;
		case UNKNOWN:
			try {
				text = formatTextElement(element.getLevel() + ")", element.getNumber(), element.getContent(),
						dateFormat.format(element.getDate()), position);
				openNode(text, FreeMindConstants.POSITION_RIGHT);
			} catch (SAXException err) {
				errorLog(err);
			}
			break;
		case CHAPTER:
			try {
				text = formatTextElement(element.getNumber() == null ? "" : element.getNumber() + " ",
						element.getContent());
				openNode(text, layout);
			} catch (SAXException err) {
				errorLog(err);
			}
			// Child node
			try {
				text = formatTextElement(position, element.getLevel(), dateFormat.format(element.getDate()),
						element.getComment());
				openNode(text, layout);
				// startNode(text, FreeMindConstants.POSITION_RIGHT);
				closeElement(element);
				// handler.endElement("", "", FreeMindConstants.ELEMENT_NODE);
			} catch (SAXException err) {
				errorLog(err);
			}
			break;
		case EMPHASIZED:
			try {
				text = formatTextElement(element.getContent());
				openNode(text, FreeMindConstants.POSITION_RIGHT);
			} catch (SAXException err) {
				errorLog(err);
			}
			break;
		case BOOKMARK:
			try {
				text = formatTextElement(position, Element.Annotation.BOOKMARK + "",
						dateFormat.format(element.getDate()));
				openNode(text, FreeMindConstants.POSITION_RIGHT);
			} catch (SAXException err) {
				errorLog(err);
			}
			break;

		default:
			break;
		}
	}

	public void closeElement(Element e) {
		try {
			handler.endElement("", "", FreeMindConstants.ELEMENT_NODE);
		} catch (SAXException err) {
			errorLog(err);
		}
	}

	public void isLeaf(Element e) {

	}

	/**
	 * Open a new node.
	 * 
	 * @param text
	 *            the text,
	 * @param modified
	 *            date in milliseconds from 1970.
	 * @param id
	 *            node id,
	 * @param created
	 *            date in milliseconds from 1970.
	 * @param position
	 *            node position: right or left.
	 * @throws SAXException
	 */
	private void openNode(String text, String position) throws SAXException {

		AttributesImpl atts;
		atts = new AttributesImpl();
		atts.addAttribute("", "", FreeMindConstants.ATTRIBUTE_TEXT, "CDATA", text);
		atts.addAttribute("", "", FreeMindConstants.ATTRIBUTE_CREATED, "CDATA", new Date().getTime() + "");
		atts.addAttribute("", "", FreeMindConstants.ATTRIBUTE_MODIFIED, "CDATA", new Date().getTime() + "");
		if (position != null) {
			atts.addAttribute("", "", FreeMindConstants.ATTRIBUTE_POSITION, "CDATA", position);
		}

		atts.addAttribute("", "", FreeMindConstants.ATTRIBUTE_ID, "CDATA",
				FreeMindConstants.ATTRIBUTE_ID + "_" + atts.hashCode());
		handler.startElement("", "", FreeMindConstants.ELEMENT_NODE, atts);
	}

	private void errorLog(Exception e) {
		e.printStackTrace();
	}

	private String formatTextElement(String... s) throws SAXException {

		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < s.length; i++) {
			if (s[i] != null && s[i].length() > 0) {
				if (i == 0) {
					buffer.append(s[i]);
				} else {
					buffer.append(" ");
					buffer.append(s[i]);
				}
			}
		}
		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}

}
