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
import java.util.Map;
import java.util.Properties;

import it.tnnfnc.apps.application.properties.AbstractEditingPanel;
import it.tnnfnc.mynotebook.Book;

/**
 * @author FT
 *
 */
public interface I_BookReaderPlugin {

	/**
	 * Get the plugin name.
	 * 
	 * @return the plugin name.
	 */
	String getName();

	/**
	 * Get the plugin configuration properties panel.
	 * 
	 * @return the plugin configuration properties panel.
	 */
	AbstractEditingPanel getPropertiesPanel();

	/**
	 * Get the plugin configuration properties.
	 * 
	 * @return the plugin configuration properties.
	 */
	Properties getProperties();

	/**
	 * Parse the input user notes.
	 * 
	 * @param in
	 *            the input resource.
	 * @throws IOException 
	 */
	Map<String, Book> parse(InputStream in) throws IOException;

	/**
	 * Build the book table of contents. User comments, emphasized text and
	 * annotations are ordered into the table of contents.
	 * 
	 * @param book
	 *            the book.
	 * @return
	 */
	Book buildBookHierarchy(Book book);

}
