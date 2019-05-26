package it.tnnfnc.mynotebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * A book element.
 * 
 * @author Franco Toninato
 * 
 */
public class Element implements Comparable<Element>, I_Composite, I_Acceptor, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7544795314623869851L;
	private int startPosition;
	private int endPosition;
	private Annotation type;
	private String content;
	private String comment;
	private String level; // 1, 2, 3, 4
	private Date date = new Date();
	private String number; // 1, 1.1, 2, 2.1.1
	private ArrayList<Element> elements;
	private Element parent;

	/**
	 * This enumeration stands for annotation types.
	 * 
	 * @author Franco Toninato
	 * 
	 */
	public static enum Annotation {
		ANNOTATION, BOOK, BOOKMARK, CHAPTER, EMPHASIZED, SCRIPT, UNKNOWN
	}

	/**
	 * Create a unknown element.
	 * 
	 * 
	 */
	public Element() {
		parent = null;
		elements = new ArrayList<Element>();
		this.type = Annotation.UNKNOWN;
	}

	/**
	 * Add an element.
	 * 
	 * @param element
	 */
	public void add(Element element) {
		element.parent = this;
		this.elements.add(element);
		// return elements.indexOf(element);
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Clear all elements assigned to this element.
	 */
	public void clearElements() {
		this.elements = null;
		this.elements = new ArrayList<Element>();
	}

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the elementNumber
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param elementNumber
	 *            the elementNumber to set
	 */
	public void setNumber(String elementNumber) {
		this.number = elementNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Element o) {
		if (o == null) {
			return 1;
		}
		if (this.equals(o))
			return 0;
		/*
		 * Rules: a) this note starts before the other.
		 */
		int result = this.getStartPosition() - o.getStartPosition();

		if (result == 0) {
			result = this.getEndPosition() - o.getEndPosition();
		}
		return result;
	}

	/**
	 * Two Element are equal if the have the same string representation.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Element other = (Element) obj;
		if (this.toString() == null) {
			if (other.toString() != null)

				return false;
		} else if (!this.toString().equals(other.toString())) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		// b.append("content: ");
		b.append(getContent());
		b.append(" - ");

		b.append("number: ");
		b.append(getNumber());
		b.append("; ");

		// b.append("endPosition: ");
		b.append("Position: ");
		b.append(getStartPosition());
		b.append(" - ");
		b.append(getEndPosition());
		b.append("; ");

		b.append("type: ");
		b.append(getType());
		b.append("; ");

		b.append("level: ");
		b.append(getLevel());
		b.append("; ");

		b.append("comment: ");
		b.append(getComment());
		b.append("}");

		return b.toString();
	}

	/**
	 * Return the text.
	 * 
	 * @return the text.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Set the text.
	 * 
	 * @param note
	 *            the note text to set
	 */
	public void setContent(String note) {
		this.content = note;
	}

	/**
	 * Get the start position.
	 * 
	 * @return the startPosition
	 */
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * Set the start position.
	 * 
	 * @param startPosition
	 *            the startPosition to set
	 */
	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * Get the end position.
	 * 
	 * @return the endPosition
	 */
	public int getEndPosition() {
		return endPosition;
	}

	/**
	 * Set the end position.
	 * 
	 * @param endPosition
	 *            the endPosition to set
	 */
	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}

	/**
	 * Get the note date.
	 * 
	 * @return the note adding date.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Set the note date.
	 * 
	 * @param date
	 *            the note adding date to set.
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Set the note type.
	 * 
	 * @param type
	 *            the type.
	 */
	public void setType(Annotation type) {
		this.type = type;

	}

	/**
	 * Get the note type.
	 * 
	 * @return the type.
	 */
	public Annotation getType() {
		return this.type;

	}

	/**
	 * Get the element parent.
	 * 
	 * @return the parent
	 */
	public Element getParent() {
		return parent;
	}

	// public void setParent(Element parent) {
	// this.parent = parent;
	// }

	/**
	 * Remove any note reference to its chapter. Call this method before
	 * recreating a new note to chapter assignment.
	 */
	public void clear() {
		elements = null;
		elements = new ArrayList<Element>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.tnnfnc.mynotebook.I_Visited#accept(it.tnnfnc.mynotebook.I_BookVisitor)
	 */
	public void accept(I_BookVisitor v) {
		v.visit(this);
	}

	public I_Iterator<? extends Element> getIterator() {
		return new Iterator();
	}

	/**
	 * Iterator over the book's elements and their sub-elements.
	 * 
	 * @author FT
	 *
	 */
	private class Iterator implements I_Iterator<Element> {

		private int index = 0;

		public boolean hasNext() {
			return index < elements.size();
		}

		public Element next() {
			try {
				Element e = elements.get(index++);
				return e;
			} catch (Exception e) {
				return null;
			}

		}

	}

}