package it.tnnfnc.mynotebook.plugins.kindle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import it.tnnfnc.mynotebook.Book;
import it.tnnfnc.mynotebook.Element;
import it.tnnfnc.mynotebook.plugins.I_NotesParser;

/**
 * Parser for the Kindle <code>MyClippings.txt</code> file. It extracts the list
 * of books and their user notes. The format is as follows:
 * <ol>
 * <li>Record line 0 = book title; info delimiter is a "("</li>
 * <li>Record line 1 = note position and date separated by "|"</li>
 * <li>Record line 2 = empty line</li>
 * <li>Record line 3 = note first line of text</li>
 * <li>Record line greater than 3 = further lines of text</li>
 * <li>Record line containing "======" = end of note marker</li>
 * </ol>
 * 
 * @author Franco Toninato
 * 
 */
public class KindleParser implements I_NotesParser {
	private int lineType;
	private SimpleDateFormat sdf;
	private String INFO_DELIMITER;
	private String HEADER_DELIMITER;
	private String POSITION_DELIMITER;
	private String RECORD_DELIMITER;
	private HashMap<String, Book> library;
	private StringBuffer textBuffer;
	private Book book;
	private Element element;
	private int date_substring_beginning = -1;

	public Map<String, Book> parse(InputStream input, Properties param) {

		INFO_DELIMITER = param.getProperty(KindleConstants.PREF_INFO_DELIMITER);
		HEADER_DELIMITER = param.getProperty(KindleConstants.PREF_HEADER_DELIMITER);
		POSITION_DELIMITER = param.getProperty(KindleConstants.PREF_POSITION_DELIMITER);
		RECORD_DELIMITER = param.getProperty(KindleConstants.PREF_RECORD_DELIMITER);
		sdf = new SimpleDateFormat(param.getProperty(KindleConstants.PREF_DATE_PATTERN), Locale.getDefault());
		library = new HashMap<String, Book>();
		BufferedReader inputStream = null;
		try {
			// inputStream = new BufferedReader(new InputStreamReader(new
			// FileInputStream(input), "UTF8"));
			inputStream = new BufferedReader(new InputStreamReader(input, "UTF8"));
			String line;
			while ((line = inputStream.readLine()) != null) {
				parseLine(line);
			}
		} catch (IOException e) {

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
	 * Parse a line.
	 * 
	 * @param line
	 *            the line of text.
	 */
	public void parseLine(String line) {

		switch (lineType) {
		case 0:
			// Book title
			beginOfBook(line);
			break;
		case 1:
			// Note position
			beginOfNote(line);
			break;
		case 2: // Empty
			emptyLine(line);
			break;
		case 3:
			appendText(line);
			break;
		default:
			if (line == null) {

			} else if (endOfRecord(line)) {
				lineType = -1;
				endOfNote(line);
			} else {
				// Text spread on many lines
				appendText(" " + line);
			}
			break;
		}

		lineType++;
	}

	/**
	 * Find a book.
	 * 
	 * @param line
	 *            the line with the book title.
	 */
	public void beginOfBook(String line) {
		book = null;
		StringBuffer comment = new StringBuffer();
		String[] infos = null;
		infos = line.split(INFO_DELIMITER);
		if (infos != null && infos.length > 0) {
			infos = line.split(INFO_DELIMITER);
			for (int j = 0; j < infos.length; j++) {
				infos[j] = infos[j].trim();

				switch (j) {
				case 0:
					// infos[0] = infos[0].substring(0,
					// infos[0].length() - 1);
					break;
				default:
					if (comment.length() > 0) {
						comment.append(", ");
					}
					comment.append(infos[j].substring(0, infos[j].length() - 1));
					break;
				}
			}
		} else {
			infos = new String[] { line };
		}
		if (!library.containsKey(line)) {
			book = new Book(infos[0], infos[infos.length - 1].substring(0, infos[infos.length - 1].length() - 1),
					comment.toString() + "");
			library.put(line, book);
		} else {
			book = library.get(line);
		}
	}

	/**
	 * Find the note header line.
	 * 
	 * @param line
	 */
	public void beginOfNote(String line) {

		/*
		 * CASE 1 position: - La tua evidenziazione alla posizione 326-327 |
		 * Aggiunto in data gioved� 7 aprile 2016 07:58:04
		 */

		/*
		 * CASE 2 position or without a delimiter: - La tua nota alla posizione
		 * 326 | Aggiunto in data gioved� 7 aprile 2016 07:58:04
		 */

		/*
		 * CASE 3 page and position; - La tua evidenziazione a pagina 23 |
		 * posizione 338-338 | Aggiunto in data luned� 8 gennaio 2018 08:43:18
		 */
		textBuffer = new StringBuffer();
		element = new Element();
		element.setType(Element.Annotation.UNKNOWN);
		element.setStartPosition(0);
		element.setEndPosition(0);
		element.setDate(new Date());

		String[] lineArray = line.split(HEADER_DELIMITER);

		// -- DATE comes as LAST: Aggiunto in data gioved� 7 aprile 2016
		// 07:58:04 //
		String date = lineArray.length > 0 ? lineArray[lineArray.length - 1].trim() : "";

		/*
		 * Date recognition: when a date is successfully parsed it defines the
		 * starting date position.
		 */
		if (date_substring_beginning == -1) {
			int i = 0; // Invalid date parsing
			for (int j = 0; j < date.length(); j++) {
				try {
					sdf.parse(date.substring(i++));
					// System.out.println("bingo date ! " +
					// date.substring(i - 1));
					date_substring_beginning = i - 1;
				} catch (Exception e1) {

				}
			}
		}

		// Set the date
		if (date_substring_beginning > -1) {
			try {
				element.setDate(sdf.parse(date.substring(date_substring_beginning)));
				// System.out.println("Simple formatted " +
				// sdf.format(note.getDate()));
			} catch (Exception e) {
				element.setDate(null);
			}
		}

		// -- POSITION OR PAGE --//
		// The position comes before the date
		String[] xy = lineArray[(lineArray.length - 2) < 0 ? 0 : lineArray.length - 2].split(POSITION_DELIMITER);

		int x = 0;
		for (String string : xy) {
			try {
				int p = Integer.parseInt(string.replaceAll("\\D+", ""));
				if (x == 0) {
					x++;
					element.setStartPosition(p);
					element.setType(Element.Annotation.UNKNOWN);
				} else if (x == 1) {
					x = 0;
					element.setEndPosition(p);
					element.setType(Element.Annotation.EMPHASIZED);
				} else {
				}
			} catch (NumberFormatException e) {
			}
		}

		// -- PAGE --//
		// The page, if presents, comes before the position
		// ... non need to trace it ...
	}

	/**
	 * Skip the empty line.
	 * 
	 * @param line
	 */
	public void emptyLine(String line) {
		// nothing
	}

	/**
	 * Append the multilines text.
	 * 
	 * @param line
	 */
	public void appendText(String line) {
		// System.out.println(line + " contains \\n?" + line.contains("\\n"));
		textBuffer.append(line);
	}

	/**
	 * Find the closing note delimiter, and adds the element to the book.
	 * 
	 * @param line
	 *            input line.
	 */
	public void endOfNote(String line) {
		/*
		 * Bookmark: no text Note: some text
		 */
		if (textBuffer.length() > 0 && element.getType().equals(Element.Annotation.UNKNOWN)) {
			element.setType(Element.Annotation.ANNOTATION);
		} else if (textBuffer.length() == 0 && element.getType().equals(Element.Annotation.UNKNOWN)) {
			element.setType(Element.Annotation.BOOKMARK);
			textBuffer.append(element.getStartPosition());
		}

		element.setContent(textBuffer.toString().trim());

		book.add(element);
	}

	/**
	 * Find the end of record.
	 * 
	 * @param line
	 * @return true when the record ends.
	 */
	public boolean endOfRecord(String line) {
		/*
		 * Record delimiter: ==========
		 */
		return line.contains(RECORD_DELIMITER);
	}

}
