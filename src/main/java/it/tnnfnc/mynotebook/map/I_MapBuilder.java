package it.tnnfnc.mynotebook.map;

import java.io.OutputStream;

import it.tnnfnc.mynotebook.Book;
import it.tnnfnc.mynotebook.Element;

/**
 * Interface for a map builder.
 * 
 * @author FT
 *
 */
public interface I_MapBuilder {

	/**
	 * Set the output stream for the map.
	 * 
	 * @param outputStream
	 *            the output stream.
	 */
	void setOutputStream(OutputStream outputStream);

	/**
	 * Call back at a new book.
	 * 
	 * @param b
	 *            a book.
	 */
	void openBook(Book b);

	/**
	 * Call back at closing the book.
	 * 
	 */
	void closeBook();

	/**
	 * Call back at a new book element.
	 * 
	 * @param e
	 *            book element.
	 */
	void openElement(Element e);

	/**
	 * Call back at closing an element.
	 * 
	 * @param e
	 *            the element.
	 */
	void closeElement(Element e);

	/**
	 * Call back when the current element is a leaf, it has got no children.
	 * 
	 * @param e
	 *            the element.
	 */
	void isLeaf(Element e);

	/**
	 * Get the plugin name.
	 * 
	 * @return the plugin name.
	 */
	String getName();

}
