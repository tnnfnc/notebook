package it.tnnfnc.mynotebook.plugins;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import it.tnnfnc.mynotebook.Book;

public interface I_NotesParser {

	/**
	 * Parse the file containing the e-book reader user notes.
	 * 
	 * @param param
	 *            parse parameters.
	 * @param input
	 *            the input.
	 * @return the file contents organized into books.
	 * @throws IOException
	 */
	public Map<String, Book> parse(InputStream input, Properties param) throws IOException;

}