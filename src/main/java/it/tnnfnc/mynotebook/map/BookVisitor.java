package it.tnnfnc.mynotebook.map;

import java.util.HashMap;
import java.util.Map;

import it.tnnfnc.mynotebook.Book;
import it.tnnfnc.mynotebook.Element;
import it.tnnfnc.mynotebook.I_BookVisitor;
import it.tnnfnc.mynotebook.I_Iterator;

/**
 * This class is a visitor for book and its elements. It traverses all elements.
 * 
 * @author FT
 *
 */
public class BookVisitor implements I_BookVisitor {

	private int level = 0;
	private I_MapBuilder mapBuilder;
	private Map<Integer, Integer> hierarchyLevel = new HashMap<>();
	private Map<Integer, Element> hierarchyElement = new HashMap<>();
	private final boolean DEBUG = false;

	/**
	 * Constructor.
	 * 
	 * @param mapBuilder
	 *            builder object for the final conceptual map.
	 */
	public BookVisitor(I_MapBuilder mapBuilder) {
		super();
		this.mapBuilder = mapBuilder;
	}

	public void visit(Book book) {
		I_Iterator<? extends Element> iterator = book.getIterator();
		openBook(book);

		int i = 0;
		do {
			level = 1; // Set the root level
			hierarchyLevel.put(level, i++);
			Element e = iterator.next();
			traverse(e);
		} while (iterator.hasNext());

		closeBook();
	}

	public void visit(Element e) {
		openElement(e);
	}

	private void traverse(Element e) {
		if (e == null) {
			return;
		}
		e.accept(this);

		// Traverse children
		I_Iterator<? extends Element> iterator = e.getIterator();

		// boolean b = iterator.hasNext();
		if (!iterator.hasNext()) {
			isLeaf(e);
			closeElement(e);
			level--; // get out
			return;
		}

		while (iterator.hasNext()) {
			traverseChildren(iterator.next());
		}
		closeElement(e);
		level--; // get out
	}

	private void traverseChildren(Element e) {
		if (e != null) { // Element has no child
			level++; // go deep
			traverse(e);
		}
	}

	/**
	 * Set the element hierarchy Level.
	 * 
	 * @param e
	 *            element.
	 */
	public void setHierarchy(Element e) {
		// Purge hierarchy of old leaf
		while (hierarchyLevel.size() > level) {
			hierarchyLevel.remove(hierarchyLevel.size());
			hierarchyElement.remove(hierarchyElement.size());
		}

		if (hierarchyLevel.get(level) == null) { // Init hierarchy if needed
			hierarchyLevel.put(level, 1);
			hierarchyElement.put(level, e);
		} else { // Count children
			hierarchyLevel.put(level, hierarchyLevel.get(level) + 1);
			hierarchyElement.put(level, e);
		}
		//
		StringBuffer numberBuffer = new StringBuffer("");
		for (int i = 1; i < hierarchyLevel.size(); i++) {
			numberBuffer.append(hierarchyLevel.get(i) + ".");
		}
		numberBuffer.append(hierarchyLevel.get(hierarchyLevel.size()));

		e.setLevel(level + "");
		e.setNumber(numberBuffer.toString());

		// if (DEBUG) {
		// System.out.println("Set hierarchy = " + hierarchyLevel + " | element
		// number = " + e.getNumber());
		// System.out.println("Element hierarchy = " + hierarchyElement);
		// }
	}

	/**
	 * Call back at visiting the book.
	 * 
	 * @param book
	 *            the book.
	 */
	public void openBook(Book book) {
		mapBuilder.openBook(book);
		// if (DEBUG)
		// System.out.println("open book - The book is: " + book);
	}

	/**
	 * Call back after visiting the book.
	 */
	public void closeBook() {
		if (DEBUG)
			System.out.println("close book ");
		mapBuilder.closeBook();
	}

	/**
	 * Call back at visiting the element.
	 * 
	 * @param e
	 *            the element.
	 */
	public void openElement(Element e) {
		// if (DEBUG)
		// System.out.println("open element ");
		setHierarchy(e);
		mapBuilder.openElement(e);
	}

	/**
	 * Call back after visiting the element.
	 * 
	 * @param e
	 */
	public void closeElement(Element e) {
		// if (DEBUG)
		// System.out.println("close element, depth = " + hierarchyLevel);
		mapBuilder.closeElement(e);
	}

	/**
	 * 
	 */
	public void getResult() {

	}

	/**
	 * Call back after visiting the element with no child.
	 * 
	 * @param e
	 *            the element.
	 */
	public void isLeaf(Element e) {
		// if (DEBUG)
		// System.out.println("is leaf, depth = " + hierarchyLevel + e);
		mapBuilder.isLeaf(e);
	}

}
