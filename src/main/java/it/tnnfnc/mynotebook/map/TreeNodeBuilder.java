package it.tnnfnc.mynotebook.map;

import java.io.OutputStream;
import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import it.tnnfnc.mynotebook.Book;
import it.tnnfnc.mynotebook.Element;

/**
 * @author FT
 *
 */
public class TreeNodeBuilder implements I_MapBuilder {

	private DefaultMutableTreeNode root;
	private DefaultMutableTreeNode node;
	private HashMap<Element, DefaultMutableTreeNode> parentNodes;

	public TreeNodeBuilder() {

	}

	@Override
	public void setOutputStream(OutputStream outputStream) {
	}

	@Override
	public void openBook(Book b) {
		root = new DefaultMutableTreeNode(b);
		parentNodes = new HashMap<>();
		parentNodes.put(b, root);
	}

	@Override
	public void closeBook() {
		parentNodes = null;
		node = null;
	}

	@Override
	public void openElement(Element e) {
		node = new DefaultMutableTreeNode(e);
		if (parentNodes.containsKey(e.getParent())) {
			parentNodes.get(e.getParent()).add(node);
		}
		if (!parentNodes.containsKey(e)) {
			parentNodes.put(e, node);
		}
	}

	@Override
	public void closeElement(Element e) {
	}

	@Override
	public void isLeaf(Element e) {
		parentNodes.remove(e);

	}

	@Override
	public String getName() {
		return null;
	}

	public DefaultTreeModel getTreeModel() {
		return new DefaultTreeModel(root);
	}

	
}
