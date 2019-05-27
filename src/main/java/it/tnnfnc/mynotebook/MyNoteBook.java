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
package it.tnnfnc.mynotebook;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import it.tnnfnc.apps.application.AbstractApplication;
import it.tnnfnc.apps.application.properties.AbstractEditingPanel;
import it.tnnfnc.apps.application.properties.PropertiesPanel;
import it.tnnfnc.apps.application.ui.GridBagLayoutUtility;
import it.tnnfnc.apps.application.ui.LicensePanel;
import it.tnnfnc.apps.application.ui.LogsPanel;
import it.tnnfnc.apps.application.ui.PopMessage;
import it.tnnfnc.apps.application.ui.style.PredefinedStyles;
import it.tnnfnc.apps.resource.I_Resource;
import it.tnnfnc.mynotebook.localization.MyNoteBookBundle;
import it.tnnfnc.mynotebook.map.BookTreeCellRenderer;
import it.tnnfnc.mynotebook.map.BookVisitor;
import it.tnnfnc.mynotebook.map.FreeMindBuilder;
import it.tnnfnc.mynotebook.map.I_MapBuilder;
import it.tnnfnc.mynotebook.map.MindMapBuilderFactory;
import it.tnnfnc.mynotebook.map.TreeNodeBuilder;
import it.tnnfnc.mynotebook.plugins.I_BookReaderPlugin;
import it.tnnfnc.mynotebook.plugins.PluginFactory;
import it.tnnfnc.mynotebook.plugins.kindle.KindlePlugin;
import it.tnnfnc.mynotebook.plugins.kindlepc.KindlePcPlugin;
import it.tnnfnc.mynotebook.plugins.mindjet.MindJetMapsPlugin;

public class MyNoteBook extends AbstractApplication<MapDocument> {

	// version = "1.0";
	// Preview
	private JTextArea previewPanel;
	private JPanel treeNodePanel;

	// e-reader
	private JComboBox<I_BookReaderPlugin> readersPlugins;

	// Map selector
	private JComboBox<I_MapBuilder> mapSelector;

	// Book
	private JButton importMyClippings;
	private JButton generateBookMap;
	private JButton previewBookMap;
	private JList<Book> listOfBooks;

	private JButton changeSettings;
	// Map
	private JCheckBox showBookmark;
	private JCheckBox showNote;
	private JCheckBox showPosition;

	private final JFileChooser fileChooser = new JFileChooser();

	// Program settings
	private PropertiesPanel propertiesPanel;

	// Current selections
	private Book editingBook;
	private JLabel labelBook;

	private I_BookReaderPlugin plugin;

	// Log options
	private LogsPanel logPanel;
	private final String emphasizedStyle = PredefinedStyles.importantStyle;
	private final String warningStyle = PredefinedStyles.orange2;
	private final String infoStyle = PredefinedStyles.green2;

	/**
	 * Constructor.
	 */
	public MyNoteBook() {
		initialise();
		createGui();
	}

	private void initialise() {
		this.bundleName = "";
		setVersion("2.4.1" + "" + "[java ver. " + System.getProperty("java.version") + "]");
		// Localizations and defaults
		setLocalization(
				(ListResourceBundle) ResourceBundle.getBundle(MyNoteBookBundle.class.getName(), Locale.getDefault()));
		// Initialize properties
		properties = new Properties();

		// Create and set up the window.
		frame = new JFrame(getLocalization().getString(Constants.READERMAP) + " " + getVersion());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//
		previewPanel = new JTextArea();
		previewPanel.setEditable(false);
		previewPanel.setLineWrap(false);
		// previewPanel = new JTextPane();
		treeNodePanel = new JPanel(new BorderLayout());
		//
		logPanel = new LogsPanel();
		logPanel.setVisibleRowCount(2);

		// Properties loadDefaultProperties();
		// Nothing to load

		// Settings
		propertiesPanel = new PropertiesPanel();
		changeSettings = new JButton(
				new ChangeSettingsListener(getLocalization().getString(Constants.CHANGE_SETTINGS)));
		propertiesPanel.addButton(changeSettings.getAction());

		// Book
		editingBook = null;
		labelBook = new JLabel();

		importMyClippings = new JButton(getLocalization().getString(Constants.IMPORT_MYCLIPPINGS));
		importMyClippings.addActionListener(new ImportMyClippingsListener());

		generateBookMap = new JButton(getLocalization().getString(Constants.GENERATE_BOOK_MAP));
		generateBookMap.addActionListener(new GenerateMapListener());
		previewBookMap = new JButton(getLocalization().getString(Constants.PREVIEW_BOOK_MAP));
		previewBookMap.addActionListener(new PreviewMapListener());

		// List of books
		listOfBooks = new JList<Book>(new DefaultListModel<Book>());
		listOfBooks.setCellRenderer(new BookListCellRenderer());
		listOfBooks.addMouseListener(new ListOfBooksListener());

		// Map
		showBookmark = new JCheckBox(getLocalization().getString(Constants.SHOW_BOOKMARK), false);
		showNote = new JCheckBox(getLocalization().getString(Constants.SHOW_NOTE), true);
		showPosition = new JCheckBox(getLocalization().getString(Constants.SHOW_POSITION), false);

		// Plugins
		readersPlugins = new JComboBox<I_BookReaderPlugin>();
		readersPlugins.addActionListener(new ReaderPluginsListener());
		registerPlugin(KindlePlugin.class.getName());
		registerPlugin(KindlePcPlugin.class.getName());
		registerPlugin(MindJetMapsPlugin.class.getName());
		if (readersPlugins != null && readersPlugins.getItemCount() > 0) {
			readersPlugins.setSelectedIndex(0);
			setPlugin(readersPlugins.getItemAt(0));
		}
		// Map selector
		mapSelector = new JComboBox<I_MapBuilder>();
		readersPlugins.addActionListener(new MindMapListener());
		registerMapBuilder(FreeMindBuilder.class.getName());
		mapSelector.setSelectedItem(0);
	}

	private void registerMapBuilder(String... string) {
		for (String className : string) {
			I_MapBuilder mapBuilder = MindMapBuilderFactory.getInstance(className);
			mapSelector.addItem(mapBuilder);
		}
	}

	private void registerPlugin(String... string) {
		PluginFactory pluginFactory = new PluginFactory();
		for (String className : string) {
			try {
				I_BookReaderPlugin plugIn = pluginFactory.getPlugin(className);
				readersPlugins.addItem(plugIn);
				propertiesPanel.addPanel(plugIn.getPropertiesPanel());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void createGui() {
		// Help Panel
		JTextPane helpPane = new JTextPane();
		helpPane.setEditable(false);
		String path = "../../../../localization/"+getLocalization().getString(Constants.HELP_FILE);
		java.net.URL helpURL = MyNoteBookBundle.class.getResource(path); // "ReaderMapHelp.htm"
		if (helpURL != null) {
			try {
				helpPane.setPage(helpURL);
			} catch (IOException e) {
				PopMessage.displayError(getFrame(), getLocalization().getString(Constants.BAD_URL) + ": " + helpURL);
			}
		} else {
			PopMessage.displayError(getFrame(), getLocalization().getString(Constants.BAD_URL) + ": " + helpURL);
			System.err.println("Couldn't find file " + helpURL);
		}
		JScrollPane helpScrollPane = new JScrollPane(helpPane);
		helpScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		helpScrollPane.setPreferredSize(new Dimension(250, 145));
		helpScrollPane.setMinimumSize(new Dimension(100, 100));

		// Book panel
		listOfBooks.setVisibleRowCount(4);
		JScrollPane scrollBookList = new JScrollPane(listOfBooks, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollBookList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedSoftBevelBorder(),
				getLocalization().getString(Constants.BOOK)));
		scrollBookList.setPreferredSize(new Dimension(250, 180));
		scrollBookList.setMinimumSize(new Dimension(250, 150));

		// Chapter panel
		JPanel chapterPanel = new JPanel(new GridLayout(0, 2));
		chapterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedSoftBevelBorder(),
				getLocalization().getString(Constants.CHAPTER)));

		// Preview panel

		JScrollPane previewScrollNodePane = new JScrollPane(treeNodePanel);
		previewScrollNodePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		previewScrollNodePane.setPreferredSize(new Dimension(250, 200));
		previewScrollNodePane.setMinimumSize(new Dimension(250, 100));

		// Overall container
		JPanel container = new JPanel(new BorderLayout());

		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		top.add(importMyClippingsPanel()); // Fixed
		top.add(scrollBookList);// Fixed

		container.add(top, BorderLayout.NORTH); // Adjustable
		container.add(previewScrollNodePane, BorderLayout.CENTER); // Adjustable
		container.add(generationPanel(), BorderLayout.SOUTH);// Fixed

		// Tabbed pane
		JTabbedPane tabpane = new JTabbedPane();
		tabpane.addTab(getLocalization().getString(Constants.GENERATE_BOOK_MAP), container);
		if (Constants.XML_PREVIEW) {
			JPanel preview = new JPanel(new BorderLayout());
			JScrollPane previewScrollPane = new JScrollPane(previewPanel);
			previewScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			previewScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			previewScrollPane.setPreferredSize(new Dimension(250, 145));
			previewScrollPane.setMinimumSize(new Dimension(100, 100));
			preview.add(previewScrollPane, BorderLayout.CENTER);
			preview.add(labelBook, BorderLayout.NORTH);
			tabpane.addTab(getLocalization().getString(Constants.PREVIEW), preview);
		}
		tabpane.addTab(getLocalization().getString(Constants.LOG), logPanel);
		tabpane.addTab(getLocalization().getString(Constants.SETTINGS), propertiesPanel);
		tabpane.addTab(getLocalization().getString(Constants.HELP), helpScrollPane);
		tabpane.addTab(getLocalization().getString(Constants.LICENSE),
				new LicensePanel(LicensePanel.GNU_GENERAL_HEADER));

		Container frame_container = frame.getContentPane();
		frame_container.setLayout(new BoxLayout(frame_container, BoxLayout.Y_AXIS));
		frame_container.add(tabpane);

		// Display
		frame.pack();
		Dimension minsize = frame.getSize();
		minsize.height = frame.getMinimumSize().height;
		frame.setMinimumSize(minsize);
		frame.setVisible(true);

	}

	private JComponent generationPanel() {
		JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		GridBagLayoutUtility.initConstraints(gbc);
		p.add(mapSelector, gbc);
		p.add(generateBookMap, GridBagLayoutUtility.right(gbc));
		p.add(previewBookMap, GridBagLayoutUtility.right(gbc));
		p.setBorder(BorderFactory.createTitledBorder(getLocalization().getString(Constants.GENERATE_BOOK_MAP)));
		return p;
	}

	private JComponent importMyClippingsPanel() {
		JPanel p = new JPanel();
		p.add(readersPlugins);
		p.add(importMyClippings);
		p.setBorder(BorderFactory.createTitledBorder(getLocalization().getString(Constants.IMPORT_MYCLIPPINGS)));
		return p;
	}

	private void refreshToc(Book b) {
		if (b != null) {
			// Build book internal hierarchy when needed
			if (!b.isHierarchyOn()) {
				editingBook = getPlugin().buildBookHierarchy(b);
				editingBook.setHierarchyOn(true);
				((DefaultListModel<Book>) listOfBooks.getModel()).set(listOfBooks.getSelectedIndex(), editingBook);
				logPanel.appendLog(getLocalization().getString(Constants.GENERATE_TABLE_OF_CONTENTS) + " "
						+ editingBook.getTitle(), emphasizedStyle);
			} else {
				editingBook = b;
			}
			// Preview
			try {
				TreeNodeBuilder treeNodeBuilder = new TreeNodeBuilder();
				editingBook.accept(new BookVisitor(treeNodeBuilder));
				JTree tree = new JTree(treeNodeBuilder.getTreeModel());
				tree.setCellRenderer(new BookTreeCellRenderer());
				treeNodePanel.removeAll();
				treeNodePanel.add(tree, BorderLayout.CENTER);
				getFrame().repaint();
				logPanel.appendLog(getLocalization().getString(Constants.PREVIEW_BOOK_MAP) + ": " //
						+ editingBook.getTitle(), emphasizedStyle);

			} catch (Exception ex) {
				PopMessage.displayError(getFrame(),
						getLocalization().getString(Constants.PREVIEW_BOOK_MAP) + " \n" + ex.getMessage());
				ex.printStackTrace();
			}

		} else {
			PopMessage.displayError(getFrame(), getLocalization().getString(Constants.NO_SELECTED_BOOK));
		}
	}

	/**
	 * Launch the application.
	 * 
	 * @param args no arguments needed.
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				UIManager.put("swing.boldMetal", Boolean.FALSE);

				new MyNoteBook();
			}
		});
	}

	@Override
	public void create(I_Resource r) throws IOException {
		super.create(r);
		logPanel.appendLog("create " + r.getURL(), null);
	}

	@Override
	public void open(I_Resource r) throws IOException {
		super.open(r);
		logPanel.appendLog("open " + r.getURL(), null);
	}

	@Override
	public void save(I_Resource r) throws IOException {
		super.save(r);
		logPanel.appendLog("save " + r.getURL(), null);
	}

	@Override
	public void saveAs(I_Resource r) throws IOException {
		super.saveAs(r);
		logPanel.appendLog("saveAs " + r.getURL(), null);
	}

	@Override
	public void close(I_Resource r) throws IOException {
		super.close(r);
	}

	@Override
	public void exit() throws IOException {
		super.exit();
		logPanel.appendLog("exit ", null);
	}

	@Override
	public boolean setResource(I_Resource r) {
		logPanel.appendLog("setResource ", null);
		return super.setResource(r);
	}

	@Override
	public I_Resource getResource() {
		logPanel.appendLog("getResource ", null);
		return super.getResource();
	}

	@Override
	public String getFileName() {
		return "MapOf_";
	}

	@Override
	public String getFileExtension() {
		return "mm";
	}

	@Override
	public FileFilter getFileFilter() {
		return null;
	}

	/**
	 * @return the selected book.
	 */
	public Book getSelectedBook() {
		Book b = listOfBooks.getSelectedValue();
		return b;
	}

	/**
	 * Load a new list of books.
	 * 
	 * @param map
	 */
	public void setListOfBooks(Map<String, Book> map) {
		((DefaultListModel<Book>) listOfBooks.getModel()).clear();
		for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			((DefaultListModel<Book>) listOfBooks.getModel()).addElement(map.get(key));
		}
		listOfBooks.repaint();
	}

	/**
	 * Get the active plug in.
	 * 
	 * @return the active plug in.
	 */
	public I_BookReaderPlugin getPlugin() {

		return this.plugin;
	}

	/**
	 * Set the active plug in.
	 * 
	 * @param plugin the active plug in.
	 */
	public void setPlugin(I_BookReaderPlugin plugin) {
		properties = plugin.getProperties();
		this.plugin = plugin;
	}

	@Override
	protected void loadProperties() throws IOException {
		logPanel.appendLog("Load properties ", null);
	}

	@Override
	protected void loadDefaultProperties() {
	}

	@Override
	protected void saveProperties() throws IOException {
	}

	@Override
	protected MapDocument newDocument() {

		MapDocument d = new MapDocument();

		return d;
	}

	@Override
	protected void closeActiveDocument() {

	}

	@Override
	protected void setActiveDocument() {

	}

	@Override
	protected void exitApplication() throws IllegalStateException {
		System.exit(0);
	}

	/**
	 * Listen to the selected book item
	 * 
	 */
	private class ListOfBooksListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == Constants.CLICK_TO_SELECT) {
				// editingBook = listOfBooks.getSelectedValue();
				logPanel.appendLog("mouse on " + listOfBooks.getSelectedIndex(), warningStyle);
				if (listOfBooks.getSelectedIndex() > -1) {

					Book b = getSelectedBook();
					// Reload the table of contents for that book.
					refreshToc(b);

				}
			}
		}
	}

	/**
	 * Change the program settings action.
	 * 
	 */
	private class ChangeSettingsListener extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ChangeSettingsListener(String name) {
			super(name);
		}

		public void actionPerformed(ActionEvent event) {
			propertiesPanel.update();
			logPanel.appendLog(getLocalization().getString(Constants.CHANGE_SETTINGS) + " " + getPlugin().getName(),
					warningStyle);
			logPanel.appendLog(getLocalization().getString(Constants.CHANGE_SETTINGS) + " " + properties, warningStyle);
		}
	}

	/**
	 * Reader Plugins Listener.
	 * 
	 */
	private class ReaderPluginsListener extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			propertiesPanel.setEnabled(false);

			Enumeration<AbstractEditingPanel> panels = propertiesPanel.getPanels();
			for (int i = 0; panels.hasMoreElements(); i++) {
				AbstractEditingPanel p = panels.nextElement();
				if (p.getLabel().equals(((I_BookReaderPlugin) readersPlugins.getSelectedItem()).getName())) {
					setPlugin((I_BookReaderPlugin) readersPlugins.getSelectedItem());
					propertiesPanel.setEnabled(p.getLabel(), true);
					propertiesPanel.setSelectedPanel(i);
					// Clear books
					((DefaultListModel<Book>) listOfBooks.getModel()).removeAllElements();
					listOfBooks.repaint();
					// Clear tocs
					treeNodePanel.removeAll();
					treeNodePanel.repaint();
				}
			}
		}
	}

	/**
	 * Reader Map Listener.
	 * 
	 */
	private class MindMapListener extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			setMindMapBuilder((I_MapBuilder) mapSelector.getSelectedItem());
		}

		private void setMindMapBuilder(I_MapBuilder selectedItem) {

		}
	}

	/**
	 * Generate the book map for the selected book.
	 * 
	 */
	private class PreviewMapListener extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			if (editingBook != null) {

				try {
					if (Constants.XML_PREVIEW) {
						getProperties().setProperty(Constants.SHOW_BOOKMARK, showBookmark.isSelected() + "");
						getProperties().setProperty(Constants.SHOW_NOTE, showNote.isSelected() + "");
						getProperties().setProperty(Constants.SHOW_POSITION, showPosition.isSelected() + "");

						// Book title
						String text = editingBook.getTitle();
						if (editingBook.getAuthor() != null)
							text = text + " - " + editingBook.getAuthor();
						if (editingBook.getContent() != null)
							text = text + " - " + editingBook.getContent();
						labelBook.setText(text);
						// MEMORY BUFFER
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						I_MapBuilder mapBuilder = (I_MapBuilder) mapSelector.getSelectedItem();
						mapBuilder.setOutputStream(out);
						editingBook.accept(new BookVisitor(mapBuilder));
						previewPanel.setText(new String(out.toByteArray(), "UTF-8"));

						logPanel.appendLog(getLocalization().getString(Constants.PREVIEW_BOOK_MAP) + ": " //
								+ editingBook.getTitle(), emphasizedStyle);
					}
				} catch (Exception ex) {
					PopMessage.displayError(getFrame(),
							getLocalization().getString(Constants.PREVIEW_BOOK_MAP) + " \n" + ex.getMessage());
					ex.printStackTrace();
				}

			} else {
				PopMessage.displayError(getFrame(), getLocalization().getString(Constants.NO_SELECTED_BOOK));
			}
		}

	}

	/**
	 * Generate the book map for the selected book.
	 * 
	 */
	private class GenerateMapListener extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			if (editingBook != null) {
				String s = new String(getFileName() + getSelectedBook().getTitle()).replaceAll("[^a-zA-Z1-9_]", "_");
				fileChooser.setSelectedFile(new File(s + "." + getFileExtension()));
				int returnVal = fileChooser.showSaveDialog(frame);
				File f = null;
				switch (returnVal) {
				case JFileChooser.APPROVE_OPTION:

					try {
						getProperties().setProperty(Constants.SHOW_BOOKMARK, showBookmark.isSelected() + "");
						getProperties().setProperty(Constants.SHOW_NOTE, showNote.isSelected() + "");
						getProperties().setProperty(Constants.SHOW_POSITION, showPosition.isSelected() + "");

						// Write to file
						f = fileChooser.getSelectedFile();
						OutputStream outFile = new FileOutputStream(f);
						I_MapBuilder mapBuilder = (I_MapBuilder) mapSelector.getSelectedItem();
						mapBuilder.setOutputStream(outFile);
						editingBook.accept(new BookVisitor(mapBuilder));

						outFile.close();

						logPanel.appendLog(getLocalization().getString(Constants.GENERATE_BOOK_MAP) + ": " //
								+ editingBook.getTitle() + " - " + getLocalization().getString(Constants.FILENAME)
								+ ": " + f.getName(), emphasizedStyle);
					} catch (Exception ex) {
						PopMessage.displayError(getFrame(), getLocalization().getString(Constants.GENERATE_BOOK_MAP)
								+ " \n" + f.getName() + " \n" + ex.getMessage());
						ex.printStackTrace();
					}

					break;
				case JFileChooser.CANCEL_OPTION:
					break;
				case JFileChooser.ERROR_OPTION:
					break;
				default:
					break;
				}

			} else {
				PopMessage.displayError(getFrame(), getLocalization().getString(Constants.NO_SELECTED_BOOK));
			}
		}

	}

	/**
	 * Import my clippings file.
	 * 
	 */
	private class ImportMyClippingsListener extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			if (getPlugin() != null) {
				int returnVal = fileChooser.showOpenDialog(frame);
				switch (returnVal) {
				case JFileChooser.APPROVE_OPTION:
					openContents(fileChooser.getSelectedFile());
					break;
				case JFileChooser.CANCEL_OPTION:
					break;
				case JFileChooser.ERROR_OPTION:
					break;
				default:
					break;
				}
			} else {
				PopMessage.displayError(frame, getLocalization().getString(Constants.ERROR),
						getLocalization().getString(Constants.PLUGIN_NOT_AVAILABLE));
			}
		}

		private void openContents(File f) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(f);
				setListOfBooks(getPlugin().parse(fis));
				fis.close();
				logPanel.appendLog(getLocalization().getString(Constants.IMPORT_MYCLIPPINGS) + ": "
						+ getPlugin().getName() + ": " + properties.toString(), emphasizedStyle);
				logPanel.appendLog(getLocalization().getString(Constants.IMPORT_MYCLIPPINGS)
						+ getLocalization().getString(Constants.LIST_OF_BOOKS) + ": "
						+ listOfBooks.getModel().getSize(), infoStyle);
			} catch (Exception e) {
				logPanel.appendLog(e.toString(), warningStyle);
				e.printStackTrace();
			}
		}

	}
}
