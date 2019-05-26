package it.tnnfnc.mynotebook.plugins.kindlepc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import it.tnnfnc.mynotebook.Book;
import it.tnnfnc.mynotebook.Element;
import it.tnnfnc.mynotebook.I_Iterator;
import it.tnnfnc.mynotebook.Element.Annotation;
import it.tnnfnc.mynotebook.plugins.I_BookHierarchyBuilder;

public class KindlePcHierarchyBuilder implements I_BookHierarchyBuilder {

	private Properties properties;

	@Override
	public Book build(Book b, Properties param) {
		// Book book = b;
		this.properties = param;

		// Add to a temporary flat list
		ArrayList<Element> elements = new ArrayList<Element>();
		I_Iterator<? extends Element> bookIterator = b.getIterator();

		do {
			Element loopingElement = bookIterator.next();
			Element e = new Element();
			e.setComment(loopingElement.getComment());
			e.setContent(loopingElement.getContent());
			e.setDate(loopingElement.getDate());
			e.setLevel(loopingElement.getLevel());
			e.setNumber(loopingElement.getNumber());
			e.setStartPosition(loopingElement.getStartPosition());
			e.setEndPosition(loopingElement.getEndPosition() == 0 ? loopingElement.getStartPosition()
					: loopingElement.getEndPosition());
			e.setType(loopingElement.getType());

			// Is it a chapter?
			if (e.getContent().contains(properties.getProperty(KindlePcConstants.PREF_CHAPTER_MARKER))) {
				elements.get(elements.size() - 1).setType(Element.Annotation.CHAPTER);
			} else {
				e.setType(Element.Annotation.EMPHASIZED);
				elements.add(e);
			}

		} while (bookIterator.hasNext());

		// Sort the elements by starting - ending positions
		Collections.sort(elements);

		// First Chapter
		Element chapter = new Element();
		chapter.setContent("");
		chapter.setType(Annotation.CHAPTER);
		try {
			chapter.setStartPosition(elements.get(0).getStartPosition());
			chapter.setEndPosition(elements.get(0).getEndPosition());
		} catch (Exception err) {
			chapter.setStartPosition(1);
			chapter.setEndPosition(Integer.MAX_VALUE);
		}

		// Return a new book with inner structure
		Book newBook = new Book();
		newBook.setTitle(b.getTitle());
		newBook.setAuthor(b.getAuthor());
		newBook.setComment(b.getComment());

		for (Element element : elements) {
			if (element != null)
				if (element.getType().equals(Annotation.CHAPTER)) {
					chapter = element;
					newBook.add(chapter);
				} else {
					chapter.add(element);
				}
		}

		return newBook;
	}

}
