package it.tnnfnc.mynotebook;

import java.awt.Component;
import java.net.URL;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

public class BookListCellRenderer extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5146363595477462139L;

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {

		Book book = (Book) value;
		String text = book.getTitle();

		if (book.getAuthor() != null)
			text = text + " - " + book.getAuthor();
		if (book.getContent() != null)
			text = text + " - " + book.getContent();

		Component c = super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
		String path = "/images/ebooks-32.png";
		java.net.URL imgurl = BookListCellRenderer.class.getResource(path);
		ImageIcon icon = new ImageIcon(imgurl, "");
		if (icon != null)
			setIcon(icon);

		return c;
	}
}
