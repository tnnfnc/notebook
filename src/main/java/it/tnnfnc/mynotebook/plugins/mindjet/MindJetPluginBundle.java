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
package it.tnnfnc.mynotebook.plugins.mindjet;

import java.util.ListResourceBundle;

public class MindJetPluginBundle extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] { //
				//
				{ "", "" }, //
				// Kindle settings
				{ MindJetConstants.MINDJET, "MindJetMaps" }, //
				{ MindJetConstants.FILE, "File" }, //
				{ MindJetConstants.RECORD, "Record" }, //
				{ MindJetConstants.PREF_CONTENT_SEPARATOR, "Begin of content delimiter" }, //
				{ MindJetConstants.PREF_LEVEL_SEPARATOR, "Node level separator" }, //
				{ MindJetConstants.PREF_EOF_DELIMITER, "End of file" }, //
				{ MindJetConstants.PREF_DATE_PATTERN, "Date pattern" }, //
				// Not localize
				{ MindJetConstants.PREF_EOF_DELIMITER_DEF, "Powered by Mindjet. http://www.mindjet.com/products/connect/" }, //
				{ MindJetConstants.PREF_CONTENT_SEPARATOR_DEF, "," }, //
				{ MindJetConstants.PREF_LEVEL_SEPARATOR_DEF, "\\." }, //
				{ MindJetConstants.PREF_DATE_PATTERN_DEF, "EEEE d MMMM yyyy HH:mm:ss" }, //
				//
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
