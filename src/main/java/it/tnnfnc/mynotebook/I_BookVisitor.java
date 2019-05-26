package it.tnnfnc.mynotebook;

/**
 * This interface represents an action taken every time the concrete visitor is
 * accepted by a visited book element. visitor.
 * 
 * @author FT
 *
 */
public interface I_BookVisitor {

	/**
	 * Visit a book.
	 * 
	 * @param book
	 *            the book.
	 */
	void visit(Book book);

	/**
	 * Visit an element.
	 * 
	 * @param element
	 *            the element.
	 */
	void visit(Element element);

}
