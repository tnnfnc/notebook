package it.tnnfnc.mynotebook.plugins.mindjet;

import java.util.Properties;

import it.tnnfnc.mynotebook.Book;
import it.tnnfnc.mynotebook.Element;
import it.tnnfnc.mynotebook.I_Iterator;
import it.tnnfnc.mynotebook.plugins.I_BookHierarchyBuilder;

/**
 * This class organizes the book content into a hierarchical view adding
 * chapters to the book and ordering notes into their chapter.
 * 
 * @author Franco Toninato
 * 
 */
public class MindJetHierarchyBuilder implements I_BookHierarchyBuilder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see kindlemap.I_BookBuilder#visitBook(kindlemap.Book)
	 */
	public Book build(final Book b, Properties param) {

		I_Iterator<? extends Element> bIterator = b.getIterator();
		Element root;
		do {
			root = bIterator.next();
		} while (bIterator.hasNext());

		I_Iterator<? extends Element> rootIterator = root.getIterator();

		// Create a new book
		Book newBook = new Book();
		newBook.setTitle(b.getTitle());
		newBook.setAuthor(b.getAuthor());
		newBook.setComment(b.getComment());
		// b.clear();
		Element chapter;
		do {
			chapter = rootIterator.next();
			chapter.setType(Element.Annotation.CHAPTER);
			newBook.add(chapter);
		} while (rootIterator.hasNext());

		return newBook;
	}

}
