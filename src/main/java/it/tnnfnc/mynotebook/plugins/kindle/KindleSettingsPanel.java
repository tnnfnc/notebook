package it.tnnfnc.mynotebook.plugins.kindle;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ListResourceBundle;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import it.tnnfnc.apps.application.properties.AbstractEditingPanel;
import it.tnnfnc.apps.application.ui.GridBagLayoutUtility;

/**
 * @author Franco Toninato
 * 
 */
public class KindleSettingsPanel extends AbstractEditingPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private Properties properties;
	// private ReaderMap readerMap;

	private JTextField infoDelimiter = new JTextField(5);
	private JTextField headerDelimiter = new JTextField(5);
	private JTextField datePattern = new JTextField(20);
	private JTextField positionDelimiter = new JTextField(5);
	private JTextField recordDelimiter = new JTextField(10);
	private JTextField chapterMarker = new JTextField(5);
	private JSpinner scanSpan = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
	private ListResourceBundle resource;
	private Properties properties;

	/**
	 * Panel constructor.
	 * 
	 * @param properties
	 *            properties.
	 */
	public KindleSettingsPanel(Properties properties) {
		this.properties = properties;
		init();
		createGUI();
		initGUI();
	}

	private void init() {
		resource = (ListResourceBundle) ResourceBundle.getBundle(KindlePluginBundle.class.getName());
		properties.setProperty(KindleConstants.PREF_INFO_DELIMITER,
				resource.getString(KindleConstants.PREF_INFO_DELIMITER_DEF));
		properties.setProperty(KindleConstants.PREF_HEADER_DELIMITER,
				resource.getString(KindleConstants.PREF_HEADER_DELIMITER_DEF));
		properties.setProperty(KindleConstants.PREF_POSITION_DELIMITER,
				resource.getString(KindleConstants.PREF_POSITION_DELIMITER_DEF));
		properties.setProperty(KindleConstants.PREF_DATE_PATTERN,
				resource.getString(KindleConstants.PREF_DATE_PATTERN_DEF));
		properties.setProperty(KindleConstants.PREF_RECORD_DELIMITER,
				resource.getString(KindleConstants.PREF_RECORD_DELIMITER_DEF));
		properties.setProperty(KindleConstants.PREF_CHAPTER_MARKER,
				resource.getString(KindleConstants.PREF_CHAPTER_MARKER_DEF));
		properties.setProperty(KindleConstants.PREF_SCAN_SPAN, resource.getString(KindleConstants.PREF_SCAN_SPAN_DEF));

	}

	private void createGUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		GridBagConstraints gbc = new GridBagConstraints();

		// Clippings Panel
		JPanel clippingsPanel = new JPanel(new GridBagLayout());
		GridBagLayoutUtility.initConstraints(gbc);
		clippingsPanel.setBorder(BorderFactory.createTitledBorder(resource.getString(KindleConstants.CLIPPINGS)));
		GridBagLayoutUtility.newLine(gbc);
		GridBagLayoutUtility.add(new JLabel(resource.getString(KindleConstants.PREF_RECORD_DELIMITER)), recordDelimiter,
				clippingsPanel, gbc);

		// Book Panel
		JPanel bookPanel = new JPanel(new GridBagLayout());
		GridBagLayoutUtility.initConstraints(gbc);
		bookPanel.setBorder(BorderFactory.createTitledBorder(resource.getString(KindleConstants.BOOK)));

		GridBagLayoutUtility.add(new JLabel(resource.getString(KindleConstants.PREF_INFO_DELIMITER)), infoDelimiter,
				bookPanel, gbc);

		// Note Panel
		JPanel annotationPanel = new JPanel(new GridBagLayout());
		GridBagLayoutUtility.initConstraints(gbc);
		annotationPanel.setBorder(BorderFactory.createTitledBorder(resource.getString(KindleConstants.ANNOTATION)));
		GridBagLayoutUtility.newLine(gbc);
		GridBagLayoutUtility.add(new JLabel(resource.getString(KindleConstants.PREF_HEADER_DELIMITER)), headerDelimiter,
				annotationPanel, gbc);

		GridBagLayoutUtility.newLine(gbc);
		annotationPanel
				.setBorder(BorderFactory.createTitledBorder(resource.getString(KindleConstants.PREF_DATE_PATTERN)));
		GridBagLayoutUtility.newLine(gbc);
		GridBagLayoutUtility.add(new JLabel(resource.getString(KindleConstants.PREF_DATE_PATTERN)), datePattern,
				annotationPanel, gbc);

		GridBagLayoutUtility.newLine(gbc);
		GridBagLayoutUtility.add(new JLabel(resource.getString(KindleConstants.PREF_POSITION_DELIMITER)),
				positionDelimiter, annotationPanel, gbc);

		// Chapter Panel
		JPanel chapterPanel = new JPanel(new GridBagLayout());
		GridBagLayoutUtility.initConstraints(gbc);
		chapterPanel.setBorder(BorderFactory.createTitledBorder(resource.getString(KindleConstants.CHAPTER)));
		GridBagLayoutUtility.newLine(gbc);
		GridBagLayoutUtility.add(new JLabel(resource.getString(KindleConstants.PREF_CHAPTER_MARKER)), chapterMarker,
				chapterPanel, gbc);
		GridBagLayoutUtility.newLine(gbc);
		GridBagLayoutUtility.add(new JLabel(resource.getString(KindleConstants.PREF_SCAN_SPAN)), scanSpan, chapterPanel,
				gbc);

		// Put all together
		this.add(clippingsPanel);
		this.add(bookPanel);
		this.add(annotationPanel);
		this.add(chapterPanel);

	}

	@Override
	public String getLabel() {
		return resource.getString(KindleConstants.KINDLE);
	}

	@Override
	public void initGUI() {

		// String key = KindleConstants.PREF_INFO_DELIMITER;
		// String value = properties.getProperty(key, p.getProperty(key));
		// infoDelimiter.setText(value);
		//
		// key = KindleConstants.PREF_HEADER_DELIMITER;
		// value = properties.getProperty(key, p.getProperty(key));
		// headerDelimiter.setText(value);
		//
		// key = KindleConstants.PREF_DATE_PATTERN;
		// value = properties.getProperty(key, p.getProperty(key));
		// datePattern.setText(value);
		//
		// key = KindleConstants.PREF_POSITION_DELIMITER;
		// value = properties.getProperty(key, p.getProperty(key));
		// positionDelimiter.setText(value);
		//
		// key = KindleConstants.PREF_RECORD_DELIMITER;
		// value = properties.getProperty(key, p.getProperty(key));
		// recordDelimiter.setText(value);
		//
		// key = KindleConstants.PREF_CHAPTER_MARKER;
		// value = properties.getProperty(key, p.getProperty(key));
		// chapterMarker.setText(value);
		//
		// key = KindleConstants.PREF_SCAN_SPAN;
		// value = properties.getProperty(key, p.getProperty(key));
		// try {
		// scanSpan.setValue((Integer.parseInt(value)));
		// } catch (NumberFormatException e) {
		// scanSpan.setValue(3);
		// }
		String key = KindleConstants.PREF_INFO_DELIMITER;
		infoDelimiter.setText(properties.getProperty(key));

		key = KindleConstants.PREF_HEADER_DELIMITER;
		headerDelimiter.setText(properties.getProperty(key));

		key = KindleConstants.PREF_DATE_PATTERN;
		datePattern.setText(properties.getProperty(key));

		key = KindleConstants.PREF_POSITION_DELIMITER;
		positionDelimiter.setText(properties.getProperty(key));

		key = KindleConstants.PREF_RECORD_DELIMITER;
		recordDelimiter.setText(properties.getProperty(key));

		key = KindleConstants.PREF_CHAPTER_MARKER;
		chapterMarker.setText(properties.getProperty(key));

		key = KindleConstants.PREF_SCAN_SPAN;
		try {
			scanSpan.setValue((Integer.parseInt(properties.getProperty(key))));
		} catch (NumberFormatException e) {
			scanSpan.setValue(3);
		}

	}

	@Override
	public void update() {
		String key = KindleConstants.PREF_INFO_DELIMITER;
		properties.setProperty(key, infoDelimiter.getText().trim());

		key = KindleConstants.PREF_HEADER_DELIMITER;
		properties.setProperty(key, headerDelimiter.getText().trim());

		key = KindleConstants.PREF_DATE_PATTERN;
		properties.setProperty(key, headerDelimiter.getText().trim());

		key = KindleConstants.PREF_POSITION_DELIMITER;
		properties.setProperty(key, positionDelimiter.getText().trim());

		key = KindleConstants.PREF_RECORD_DELIMITER;
		properties.setProperty(key, recordDelimiter.getText().trim());

		key = KindleConstants.PREF_SCAN_SPAN;
		properties.setProperty(key, ((SpinnerNumberModel) scanSpan.getModel()).getNumber().intValue() + "");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		infoDelimiter.setEnabled(b);
		headerDelimiter.setEnabled(b);
		positionDelimiter.setEnabled(b);
		recordDelimiter.setEnabled(b);
		datePattern.setEnabled(b);
		chapterMarker.setEnabled(b);
		scanSpan.setEnabled(b);
	}

}
