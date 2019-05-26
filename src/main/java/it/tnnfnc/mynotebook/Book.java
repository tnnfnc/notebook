/*
 * Copyright (c) 2015, Franco Toninato. All rights reserved.
 * 
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. 
 * EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER 
 * PARTIES PROVIDE THE PROGRAM �AS IS� WITHOUT WARRANTY OF ANY KIND, EITHER 
 * EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE ENTIRE RISK AS TO THE 
 * QUALITY AND PERFORMANCE OF THE PROGRAM IS WITH YOU. SHOULD THE PROGRAM PROVE 
 * DEFECTIVE, YOU ASSUME THE COST OF ALL NECESSARY SERVICING, REPAIR OR CORRECTION.
 */
package it.tnnfnc.mynotebook;

/**
 * The book has a title, one or more authors, a comment, a table of contents and
 * several annotations.
 * 
 * @author Franco Toninato
 *
 */
public class Book extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1047466879211228991L;
	private String title;
	private String author;
	private boolean hierarchyOn;

	/**
	 * Create a book.
	 * 
	 */
	public Book() {
		super();
		setType(Annotation.BOOK);
	}

	/**
	 * Create a book.
	 * 
	 * @param title
	 *            the title.
	 * @param author
	 *            the author.
	 * @param comment
	 *            a book comment.
	 */
	public Book(String title, String author, String comment) {
		this();
		this.author = author;
		this.title = title;
		setComment(comment);
	}

	/**
	 * @return the bookTitle
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param bookTitle
	 *            the bookTitle to set
	 */
	public void setTitle(String bookTitle) {
		this.title = bookTitle;
	}

	/**
	 * @return the bookAuthor
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param bookAuthor
	 *            the bookAuthor to set
	 */
	public void setAuthor(String bookAuthor) {
		this.author = bookAuthor;
	}

	public void accept(I_BookVisitor v) {
		v.visit(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("Title: ");
		b.append(getTitle());
		b.append(" - ");
		b.append("Authors: [");
		b.append(getAuthor());
		b.append("] - ");
		b.append("Comment: [");
		b.append(getComment());
		b.append("]");

		return b.toString();
	}

	/**
	 * Check if its table of contents was already set.
	 * 
	 * @return the hierarchyOn true when its table of contents was already set.
	 */
	public boolean isHierarchyOn() {
		return hierarchyOn;
	}

	/**
	 * Set to true when its table of contents was already set.
	 * 
	 * @param hierarchyOn
	 *            the hierarchyOn to set.
	 */
	public void setHierarchyOn(boolean hierarchyOn) {
		this.hierarchyOn = hierarchyOn;
	}
}
