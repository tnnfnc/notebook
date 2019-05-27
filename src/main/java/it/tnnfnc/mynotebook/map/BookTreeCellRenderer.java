package it.tnnfnc.mynotebook.map;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import it.tnnfnc.mynotebook.Book;
import it.tnnfnc.mynotebook.Element;

public class BookTreeCellRenderer extends DefaultTreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Element element = (Element) node.getUserObject();

		String toolTipText = "";
		String text = "";
		switch (element.getType()) {
		case BOOK:
			text = ((Book) element).getTitle() + " - " + ((Book) element).getAuthor();
			break;
		case CHAPTER:
			text = element.getNumber() + " " + element.getContent();
			toolTipText = element.getStartPosition() + "";
			break;
		case EMPHASIZED:
			text = element.getContent();
			toolTipText = element.getStartPosition() + "";
			break;
		case ANNOTATION:
			text = element.getStartPosition() + " - " + element.getContent();
			toolTipText = element.getStartPosition() + "";
			break;
		case BOOKMARK:
			text = element.getStartPosition() + "";
			break;
		case UNKNOWN:
			break;
		default:
			text = Element.Annotation.UNKNOWN + "";
			break;
		}

		// setText(text);
		super.setToolTipText(toolTipText); // no tool tip

		setLeafIcon(null);
		setClosedIcon(null);
		setOpenIcon(null);
		return super.getTreeCellRendererComponent(tree, text, sel, expanded, leaf, row, hasFocus);
	}

}