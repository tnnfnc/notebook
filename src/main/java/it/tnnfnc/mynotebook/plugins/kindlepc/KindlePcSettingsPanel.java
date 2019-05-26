package it.tnnfnc.mynotebook.plugins.kindlepc;

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

public class KindlePcSettingsPanel extends AbstractEditingPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private Properties properties;
	// private ReaderMap readerMap;

	// private JTextField infoDelimiter = new JTextField(5);
	// private JTextField headerDelimiter = new JTextField(5);
	// private JTextField datePattern = new JTextField(20);
	// private JTextField recordDelimiter = new JTextField(10);
	private JTextField positionDelimiter = new JTextField(5);
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
	public KindlePcSettingsPanel(Properties properties) {
		this.properties = properties;
		init();
		createGUI();
		initGUI();
	}

	private void init() {
		resource = (ListResourceBundle) ResourceBundle.getBundle(KindlePcPluginBundle.class.getName());

		properties.setProperty(KindlePcConstants.PREF_POSITION_DELIMITER,
				resource.getString(KindlePcConstants.PREF_POSITION_DELIMITER_DEF));

		properties.setProperty(KindlePcConstants.PREF_CHAPTER_MARKER,
				resource.getString(KindlePcConstants.PREF_CHAPTER_MARKER_DEF));

		properties.setProperty(KindlePcConstants.PREF_SCAN_SPAN,
				resource.getString(KindlePcConstants.PREF_SCAN_SPAN_DEF));

	}

	private void createGUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		GridBagConstraints gbc = new GridBagConstraints();

		// Chapter Panel
		JPanel chapterPanel = new JPanel(new GridBagLayout());
		GridBagLayoutUtility.initConstraints(gbc);

		chapterPanel.setBorder(BorderFactory.createTitledBorder(resource.getString(KindlePcConstants.CHAPTER)));
		GridBagLayoutUtility.add(new JLabel(resource.getString(KindlePcConstants.PREF_POSITION_DELIMITER)),
				positionDelimiter, chapterPanel, gbc);

		GridBagLayoutUtility.newLine(gbc);
		GridBagLayoutUtility.add(new JLabel(resource.getString(KindlePcConstants.PREF_CHAPTER_MARKER)), chapterMarker,
				chapterPanel, gbc);
		GridBagLayoutUtility.newLine(gbc);
		GridBagLayoutUtility.add(new JLabel(resource.getString(KindlePcConstants.PREF_SCAN_SPAN)), scanSpan,
				chapterPanel, gbc);

		// Put all together
		this.add(chapterPanel);

	}

	@Override
	public String getLabel() {
		return resource.getString(KindlePcConstants.KINDLE);
	}

	@Override
	public void initGUI() {

		String key = KindlePcConstants.PREF_POSITION_DELIMITER;
		positionDelimiter.setText(properties.getProperty(key));

		key = KindlePcConstants.PREF_CHAPTER_MARKER;
		chapterMarker.setText(properties.getProperty(key));

		key = KindlePcConstants.PREF_SCAN_SPAN;
		try {
			scanSpan.setValue((Integer.parseInt(properties.getProperty(key))));
		} catch (NumberFormatException e) {
			scanSpan.setValue(1);
		}

	}

	@Override
	public void update() {

		String key = KindlePcConstants.PREF_POSITION_DELIMITER;
		properties.setProperty(key, positionDelimiter.getText().trim());

		key = KindlePcConstants.PREF_SCAN_SPAN;
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
		positionDelimiter.setEnabled(b);
		chapterMarker.setEnabled(b);
		scanSpan.setEnabled(false);
	}

}
