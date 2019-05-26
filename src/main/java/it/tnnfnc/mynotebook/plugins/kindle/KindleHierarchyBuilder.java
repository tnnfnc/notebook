package it.tnnfnc.mynotebook.plugins.kindle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import it.tnnfnc.mynotebook.Book;
import it.tnnfnc.mynotebook.Element;
import it.tnnfnc.mynotebook.Element.Annotation;
import it.tnnfnc.mynotebook.plugins.I_BookHierarchyBuilder;
import it.tnnfnc.mynotebook.I_Iterator;

/**
 * This class organizes the book content into a hierarchical view adding
 * chapters to the book and ordering notes into their chapter.
 * 
 * @author Franco Toninato
 * 
 */
public class KindleHierarchyBuilder implements I_BookHierarchyBuilder {

	private Properties properties;
	// private Book book;

	/*
	 * (non-Javadoc)
	 * 
	 * @see kindlemap.I_BookBuilder#visitBook(kindlemap.Book)
	 */
	public Book build(final Book b, Properties param) {
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
			e.setEndPosition(loopingElement.getEndPosition());
			e.setType(loopingElement.getType());
			elements.add(e);
		} while (bookIterator.hasNext());

		// Sort the elements by starting - ending positions
		Collections.sort(elements);

		// Find chapters and prepare to discard unknown elements
		buildTableOfContents(elements, properties.getProperty(KindleConstants.PREF_CHAPTER_MARKER));

		// Discard unknown elements
		purge(elements);

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

	private void purge(ArrayList<Element> elements) {
		try {
			for (int i = 0; i < elements.size();) {
				if (elements.get(i).getType().equals(Annotation.UNKNOWN)) {
					elements.remove(i);
				} else {
					i++;
				}
			}
		} catch (Exception err) {
		}
	}

	/**
	 * Find a chapter title: a) the note contains a chapter marker. b) scan the
	 * previous or following notes to find the title.
	 * 
	 * @param elements
	 * 
	 * @param b
	 * @param chapterMarker
	 */
	private void buildTableOfContents(ArrayList<Element> elements, String chapterMarker) {
		int scanSpan = 0;
		try {
			scanSpan = Integer.parseInt(properties.getProperty(KindleConstants.PREF_SCAN_SPAN));
		} catch (NumberFormatException e) {
			scanSpan = 3;
		}

		// Looping on elements
		Element chapter = null;
		for (int i = 0; i < elements.size(); i++) {
			Element element = elements.get(i);
			/*
			 * Mark as a chapter an element containing the chapter marker in its
			 * content.
			 */
			if (element == null)
				continue;

			if (element.getContent().contains(chapterMarker)) {
				element.setType(Element.Annotation.CHAPTER);
				/*
				 * Scan the previous or following notes to find the title
				 */
				int found = scanForChapter(elements, i, scanSpan);

				/*
				 * If found: create a new chapter
				 */
				if (found > -1) {
					/*
					 * Set the end position of preceding chapter, but an end
					 * position must be set to avoid notes collapsing into the
					 * no chapter element.
					 */
					if (chapter != null) {
						chapter.setEndPosition(elements.get(found).getStartPosition());
					}
					chapter = elements.get(found);
					chapter.setType(Element.Annotation.CHAPTER);
					// To be discarded
					element.setType(Element.Annotation.UNKNOWN);
				} else {
					/*
					 * Nothing exists: nothing to do, go ahead
					 */
					element.setType(Element.Annotation.UNKNOWN);
				}
			}

		}
		// No chapter case
		if (chapter == null) {
			chapter = new Element();
			chapter.setContent(" --- ");
			chapter.setType(Annotation.CHAPTER);
			chapter.setStartPosition(0);
			// Add to the book
			elements.add(chapter);
		}
		// // The last chapter
		chapter.setEndPosition(elements.get(elements.size() - 1).getStartPosition());
	}

	/**
	 * Scan from index an interval further and former if it founds a note with
	 * the same ending position or with the same start position
	 * 
	 * @param elements
	 * 
	 * @param index
	 *            starting index
	 * @param interval
	 *            number of previous or following notes to scan.
	 * @return the index of the chapter type note.
	 */
	private int scanForChapter(ArrayList<Element> elements, int index, int interval) {
		/*
		 * Define the start and end interval from actual position
		 */
		int start_position = elements.get(index).getStartPosition();
		int from = (index - interval) >= 0 ? (index - interval) : 0;
		int to = (index + interval) < elements.size() ? (index + interval) : (elements.size() - 1);
		/*
		 * Look for an emphasized text belonging to the interval, it has the
		 * chapter title.
		 */
		for (int i = from; i <= to; i++) {
			Element element = elements.get(i);

			if (element.getType().equals(Element.Annotation.EMPHASIZED)) {
				if (start_position >= element.getStartPosition() && start_position <= element.getEndPosition()) {
					return i;
				}
			}
		}
		return -1;
	}

}
