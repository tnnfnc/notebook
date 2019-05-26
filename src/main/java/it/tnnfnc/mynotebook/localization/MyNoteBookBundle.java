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
package it.tnnfnc.mynotebook.localization;

import java.util.ListResourceBundle;

import it.tnnfnc.mynotebook.Constants;

public class MyNoteBookBundle extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] { //
				//
				{ "", "" }, //
				{ Constants.READERMAP, "mynotebook" }, //
				// Book
				{ Constants.BOOK, "Books" }, //
				{ Constants.BOOK_TITLE, "Title" }, //
				{ Constants.BOOK_AUTHOR, "Author" }, //
				{ Constants.BOOK_COMMENT, "Comment" }, //
				{ Constants.CHANGE_BOOK, "Change book" }, //
				{ Constants.IMPORT_MYCLIPPINGS, "Import my clippings" }, //
				{ Constants.GENERATE_BOOK_MAP, "Generate map" }, //
				{ Constants.PREVIEW_BOOK_MAP, "Map preview" }, //
				{ Constants.GENERATE_TABLE_OF_CONTENTS, "Generate Table Of Contents" }, //
				{ Constants.LIST_OF_BOOKS, "List of Books" }, //
				{ Constants.SETTINGS, "Settings" }, //
				{ Constants.CHANGE_SETTINGS, "Change Settings" }, //
				{ Constants.LOG, "Log" }, //
				{ Constants.PREVIEW, "Preview" }, //
				{ Constants.FILENAME, "File name" }, //
				// Chapter
				{ Constants.CHAPTER, "Chapters" }, //
				{ Constants.CHAPTER_TITLE, "Title" }, //
				{ Constants.CHAPTER_NUMBER, "Number" }, //
				{ Constants.CHAPTER_COMMENT, "Comment" }, //
				{ Constants.CHAPTER_START_POSITION, "Start position" }, //
				{ Constants.CHAPTER_END_POSITION, "End position" }, //
				{ Constants.CHAPTER_LEVEL, "Hierarchy level" }, //
				{ Constants.ADD_CHAPTER, "Add Chapter" }, //
				{ Constants.CHANGE_CHAPTER, "Change Chapter" }, //
				{ Constants.REMOVE_CHAPTER, "Remove Chapter" }, //
				{ Constants.IMPORT_CONTENTS, "Import table of contents" }, //
				{ Constants.TABLE_OF_CONTENTS, "Table of contents" }, //
				{ Constants.EXPORT_CONTENTS, "Export table of contents" }, //
				// Map settings
				{ Constants.SHOW_BOOKMARK, "Show bookmarks" }, //
				{ Constants.SHOW_NOTE, "Show notes" }, //
				{ Constants.SHOW_POSITION, "Show positions" }, //
				// Kindle settings
				{ Constants.READER_SETTINGS, "Kindle" }, //
				// { Constants.PREF_HEADER_DELIMITER, "Header data delimiters"
				// }, //
				// { Constants.PREF_INFO_DELIMITER, "Book informations
				// delimiter" }, //
				// { Constants.PREF_POSITION_DELIMITER, "Note position
				// delimiter" }, //
				// { Constants.PREF_RECORD_DELIMITER, "End of record delimiter"
				// }, //
				// Not localize
				// { Constants.PREF_INFO_DELIMITER_DEF, "\\(" }, //
				// { Constants.PREF_HEADER_DELIMITER_DEF, "\\|" }, //
				// { Constants.PREF_POSITION_DELIMITER_DEF, "-" }, //
				// { Constants.PREF_RECORD_DELIMITER_DEF, "==========" }, //
				// { Constants.PREF_CHAPTER_MARKER, "Chapter marker" }, //
				// { Constants.PREF_CHAPTER_MARKER_DEF, "�" }, //
				// { Constants.PREF_DATE_PATTERN, "EEEE d MMMM yyyy HH:mm:ss" },
				// //
				// Clippings
				{ Constants.CLIPPINGS, "My clippings file" }, //
				// Notes
				{ Constants.ANNOTATION, "Annotations" }, //
				// Help
				{ Constants.HELP, "Program help" }, //
				{ Constants.HELP_FILE, "Help.htm" }, //
				// License
				{ Constants.LICENSE, "License" }, //
				{ "", "" }, //
				// Messages
				{ Constants.ERROR, "Error" }, //
				{ Constants.PLUGIN_NOT_AVAILABLE, "No e-reader is selected" }, //
				{ Constants.NO_SELECTED_BOOK, "No selected book" }, //
				{ Constants.NO_SELECTED_CHAPTER, "No selected chapter" }, //
				{ Constants.BAD_URL, "Bad file path" }, //
				{ "", "" }, //
				{ "", "" }, //
				{ "", "" }, //

				//
		};//
	}

}
