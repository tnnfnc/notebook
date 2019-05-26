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

public class MyNoteBookBundle_it extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] { //
				//
				{ "", "" }, //
				{ Constants.READERMAP, "mynotebook" }, //
				// Book
				{ Constants.BOOK, "Libro" }, //
				{ Constants.BOOK_TITLE, "Titolo" }, //
				{ Constants.BOOK_AUTHOR, "Autore" }, //
				{ Constants.BOOK_COMMENT, "Commento" }, //
				{ Constants.CHANGE_BOOK, "Modifica il libro" }, //
				{ Constants.IMPORT_MYCLIPPINGS, "Importa i miei appunti" }, //
				{ Constants.GENERATE_BOOK_MAP, "Genera la mappa" }, //
				{ Constants.PREVIEW_BOOK_MAP, "Anteprima" }, //
				{ Constants.GENERATE_TABLE_OF_CONTENTS, "Genera l'indice" }, //
				{ Constants.LIST_OF_BOOKS, "Elenco dei libri" }, //
				{ Constants.SETTINGS, "Impostazioni" }, //
				{ Constants.CHANGE_SETTINGS, "Cambia le impostazioni" }, //
				{ Constants.LOG, "Log" }, //
				{ Constants.PREVIEW, "Anteprima" }, //
				{ Constants.FILENAME, "File name" }, //
				// Chapter
				{ Constants.CHAPTER, "Capitoli" }, //
				{ Constants.CHAPTER_TITLE, "Titolo" }, //
				{ Constants.CHAPTER_NUMBER, "Numero" }, //
				{ Constants.CHAPTER_COMMENT, "Commento" }, //
				{ Constants.CHAPTER_START_POSITION, "Inizio" }, //
				{ Constants.CHAPTER_END_POSITION, "Fine" }, //
				{ Constants.CHAPTER_LEVEL, "Livello" }, //
				{ Constants.ADD_CHAPTER, "Aggiungi un capitolo" }, //
				{ Constants.CHANGE_CHAPTER, "Cambia il capitolo" }, //
				{ Constants.REMOVE_CHAPTER, "Elimina il capitolo" }, //
				{ Constants.IMPORT_CONTENTS, "Importa l'indice" }, //
				{ Constants.TABLE_OF_CONTENTS, "Indice" }, //
				{ Constants.EXPORT_CONTENTS, "Esporta l'indice" }, //
				// Map settings
				{ Constants.SHOW_BOOKMARK, "Mostra segnalibri" }, //
				{ Constants.SHOW_NOTE, "Mostra note" }, //
				{ Constants.SHOW_POSITION, "Mostra posizioni" }, //
				// Clippings
				{ Constants.CLIPPINGS, "My clippings file" }, //
				// Notes
				{ Constants.ANNOTATION, "Note" }, //
				// Help
				{ Constants.HELP, "Aiuto" }, //
				{ Constants.HELP_FILE, "Help_it.htm" }, //
				// License
				{ Constants.LICENSE, "Licenza" }, //
				{ "", "" }, //
				// Messages
				{ Constants.ERROR, "Errore" }, //
				{ Constants.PLUGIN_NOT_AVAILABLE, "Non � stato selezionato un e-reader" }, //
				{ Constants.NO_SELECTED_BOOK, "Non � stato selezionato il libro" }, //
				{ Constants.NO_SELECTED_CHAPTER, "Non � stato selezionato il capitolo" }, //
				{ Constants.BAD_URL, "Il percorso del file non � valido" }, //
				{ "", "" }, //
				{ "", "" }, //
				{ "", "" }, //

				//
		};//
	}

}
