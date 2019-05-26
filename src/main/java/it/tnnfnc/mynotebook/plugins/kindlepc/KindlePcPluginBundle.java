package it.tnnfnc.mynotebook.plugins.kindlepc;

import java.util.ListResourceBundle;

public class KindlePcPluginBundle extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] { //
				//
				{ "", "" }, //
				// Kindle settings
				{ KindlePcConstants.KINDLE, "Kindle for pc" }, //
				{ KindlePcConstants.CHAPTER, "Chapters" }, //
				{ KindlePcConstants.PREF_POSITION_DELIMITER, "Note position delimiter" }, //
				{ KindlePcConstants.PREF_CHAPTER_MARKER, "Chapter marker" }, //
				{ KindlePcConstants.PREF_SCAN_SPAN, "Chapter scanning distance" }, //
				// Not localize
				{ KindlePcConstants.PREF_POSITION_DELIMITER_DEF, "-" }, //
				{ KindlePcConstants.PREF_CHAPTER_MARKER_DEF, "�" }, //
				{ KindlePcConstants.PREF_SCAN_SPAN_DEF, "1" }, //
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
