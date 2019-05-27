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
package it.tnnfnc.mynotebook.plugins.kindle;

import java.util.ListResourceBundle;

public class KindlePluginBundle_it extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] { //
				//
				{ "", "" }, //
				// Kindle settings
				{ KindleConstants.KINDLE, "Kindle" }, //
				{ KindleConstants.CLIPPINGS, "My clippings" }, //
				{ KindleConstants.BOOK, "Libri" }, //
				{ KindleConstants.CHAPTER, "Capitoli" }, //
				{ KindleConstants.ANNOTATION, "Nota" }, //
				{ KindleConstants.PREF_HEADER_DELIMITER, "Indicatore dei dati di testata" }, //
				{ KindleConstants.PREF_INFO_DELIMITER, "Indicatore dei dati del libro" }, //
				{ KindleConstants.PREF_POSITION_DELIMITER, "Indicatore della posizione della nota" }, //
				{ KindleConstants.PREF_RECORD_DELIMITER, "Carattere di fine record" }, //
				{ KindleConstants.PREF_CHAPTER_MARKER, "Contrassegno per un capitolo" }, //
				{ KindleConstants.PREF_SCAN_SPAN, "Livello di ricerca del capitolo" }, //
				{ KindleConstants.PREF_DATE_PATTERN, "Formato data" }, //
				// Not localize
				{ KindleConstants.PREF_INFO_DELIMITER_DEF, "\\(" }, //
				{ KindleConstants.PREF_HEADER_DELIMITER_DEF, "\\|" }, //
				{ KindleConstants.PREF_POSITION_DELIMITER_DEF, "-" }, //
				{ KindleConstants.PREF_RECORD_DELIMITER_DEF, "==========" }, //
				{ KindleConstants.PREF_CHAPTER_MARKER_DEF, "§" }, //
				{ KindleConstants.PREF_SCAN_SPAN_DEF, "1" }, //
				{ KindleConstants.PREF_DATE_PATTERN_DEF, "EEEE d MMMM yyyy HH:mm:ss" }, //
				//

				// Kindle settings
				{ "", "" }, //
				{ "", "" }, //
				{ "", "" }, //
				{ "", "" }, //
				{ "", "" }, //
				{ "", "" }, //

				//
		};//
	}

}
