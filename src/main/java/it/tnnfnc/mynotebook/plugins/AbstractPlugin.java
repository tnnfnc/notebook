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
package it.tnnfnc.mynotebook.plugins;

import java.io.IOException;
import java.io.InputStream;
import java.util.ListResourceBundle;
import java.util.Map;
import java.util.Properties;

import it.tnnfnc.apps.application.properties.AbstractEditingPanel;
import it.tnnfnc.mynotebook.Book;

/**
 * The steps to implement a new plugin are:
 * <ol>
 * <li>Extend this class to a new <b>plugin</b> class managing the device</li>
 * <li>Define a <b>resource file</b> containing the localization</li>
 * <li>Define a <b>parser</b> for the input notes file</li>
 * <li>Define a hierarchy <b>builder</b> for reconstruct the book hierarchy relationships chapter-notes</li>
 * <li>Define a <b>constants</b> file providing the program constants and default settings</li>
 * <li>Define a <b>settings</b> class managing the technical settings used by the plugin</li>
 * <li><b>Register</b> the plugin class to {@link it.tnnfnc.mynotebook.MyNoteBook} </li>
 * <li><b>Register</b> the settings class to {@link it.tnnfnc.mynotebook.MyNoteBook} </li>
 * <li><b></b></li>
 * <li><b></b></li>
 * <li><b></b></li>
 * </ol> 
 * @author FT
 *
 */
public abstract class AbstractPlugin implements I_BookReaderPlugin {

	protected Properties properties;
	protected I_NotesParser notesParser;
	protected I_BookHierarchyBuilder bookHierarchyBuilder;
	protected AbstractEditingPanel propertiesPanel;
	protected ListResourceBundle resource;
	protected String name;

	/**
	 * Get the plugin configuration properties panel.
	 * 
	 * @return the plugin configuration properties panel.
	 */
	public AbstractEditingPanel getPropertiesPanel() {
		return propertiesPanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.tnnfnc.mynotebook.plugins.I_BookReaderPlugin#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * Parse the input user notes.
	 * 
	 * @param in
	 *            the input resource.
	 * @throws IOException 
	 */
	public Map<String, Book> parse(InputStream in) throws IOException {
		return notesParser.parse(in, getProperties());
	}

	/**
	 * Get the plugin configuration properties.
	 * 
	 * @return the plugin configuration properties.
	 */
	public Properties getProperties() {
		return this.properties;
	}

	/**
	 * Build the book table of contents. User comments, emphasized text and
	 * annotations are ordered into the table of contents.
	 * 
	 * @param book
	 *            the book.
	 */
	public Book buildBookHierarchy(Book book) {
		return bookHierarchyBuilder.build(book, getProperties());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}

}
