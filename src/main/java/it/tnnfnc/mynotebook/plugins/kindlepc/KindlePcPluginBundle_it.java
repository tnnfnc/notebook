package it.tnnfnc.mynotebook.plugins.kindlepc;

import java.util.ListResourceBundle;

public class KindlePcPluginBundle_it extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] { //
				//
				{ "", "" }, //
				// Kindle settings
				{ KindlePcConstants.KINDLE, "Kindle per pc" }, //
				{ KindlePcConstants.CHAPTER, "Capitoli" }, //
				{ KindlePcConstants.PREF_POSITION_DELIMITER, "Indicatore della posizione della nota" }, //
				{ KindlePcConstants.PREF_CHAPTER_MARKER, "Contrassegno per un capitolo" }, //
				{ KindlePcConstants.PREF_SCAN_SPAN, "Livello di ricerca del capitolo" }, //
				// Not localize
				{ KindlePcConstants.PREF_POSITION_DELIMITER_DEF, "-" }, //
				{ KindlePcConstants.PREF_CHAPTER_MARKER_DEF, "�" }, //
				{ KindlePcConstants.PREF_SCAN_SPAN_DEF, "1" }, //
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
