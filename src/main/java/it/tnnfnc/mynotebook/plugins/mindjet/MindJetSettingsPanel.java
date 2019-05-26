package it.tnnfnc.mynotebook.plugins.mindjet;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ListResourceBundle;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.tnnfnc.apps.application.properties.AbstractEditingPanel;
import it.tnnfnc.apps.application.ui.GridBagLayoutUtility;

/**
 * @author Franco Toninato
 * 
 */
public class MindJetSettingsPanel extends AbstractEditingPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private Properties properties;
	// private ReaderMap readerMap;
	// private JTextField infoDelimiter = new JTextField(5);
	// private JTextField chapterMarker = new JTextField(5);
	// private JSpinner scanSpan = new JSpinner(new SpinnerNumberModel(1, 1, 10,
	// 1));

	private JTextField contentSeparator = new JTextField(5);
	private JTextField datePattern = new JTextField(20);
	private JTextField levelSeparator = new JTextField(5);
	private JTextField eofDelimiter = new JTextField(10);
	private ListResourceBundle resource;
	private Properties properties;

	/**
	 * Panel constructor.
	 * 
	 * @param properties
	 *            properties.
	 */
	public MindJetSettingsPanel(Properties properties) {
		this.properties = properties;
		init();
		createGUI();
		initGUI();
	}

	private void init() {
		resource = (ListResourceBundle) ResourceBundle.getBundle(MindJetPluginBundle.class.getName());
		
		properties.setProperty(MindJetConstants.PREF_CONTENT_SEPARATOR,
				resource.getString(MindJetConstants.PREF_CONTENT_SEPARATOR_DEF));
		properties.setProperty(MindJetConstants.PREF_LEVEL_SEPARATOR,
				resource.getString(MindJetConstants.PREF_LEVEL_SEPARATOR_DEF));
		properties.setProperty(MindJetConstants.PREF_DATE_PATTERN,
				resource.getString(MindJetConstants.PREF_DATE_PATTERN_DEF));
		properties.setProperty(MindJetConstants.PREF_EOF_DELIMITER,
				resource.getString(MindJetConstants.PREF_EOF_DELIMITER_DEF));
	}

	private void createGUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		GridBagConstraints gbc = new GridBagConstraints();

		// fILE Panel
		JPanel clippingsPanel = new JPanel(new GridBagLayout());
		GridBagLayoutUtility.initConstraints(gbc);
		clippingsPanel.setBorder(BorderFactory.createTitledBorder(resource.getString(MindJetConstants.FILE)));
		GridBagLayoutUtility.newLine(gbc);
		GridBagLayoutUtility.add(new JLabel(resource.getString(MindJetConstants.PREF_EOF_DELIMITER)), eofDelimiter,
				clippingsPanel, gbc);

		// Note Panel
		JPanel annotationPanel = new JPanel(new GridBagLayout());
		GridBagLayoutUtility.initConstraints(gbc);
		annotationPanel.setBorder(BorderFactory.createTitledBorder(resource.getString(MindJetConstants.RECORD)));
		GridBagLayoutUtility.newLine(gbc);
		GridBagLayoutUtility.add(new JLabel(resource.getString(MindJetConstants.PREF_CONTENT_SEPARATOR)),
				contentSeparator, annotationPanel, gbc);

		GridBagLayoutUtility.newLine(gbc);
		GridBagLayoutUtility.add(new JLabel(resource.getString(MindJetConstants.PREF_LEVEL_SEPARATOR)), levelSeparator,
				annotationPanel, gbc);

		GridBagLayoutUtility.newLine(gbc);
		annotationPanel
				.setBorder(BorderFactory.createTitledBorder(resource.getString(MindJetConstants.PREF_DATE_PATTERN)));
		GridBagLayoutUtility.newLine(gbc);
		GridBagLayoutUtility.add(new JLabel(resource.getString(MindJetConstants.PREF_DATE_PATTERN)), datePattern,
				annotationPanel, gbc);

		// Put all together
		this.add(clippingsPanel);
		this.add(annotationPanel);

	}

	@Override
	public String getLabel() {
		return resource.getString(MindJetConstants.MINDJET);
	}

	@Override
	public void initGUI() {
		String key = MindJetConstants.PREF_CONTENT_SEPARATOR;
		contentSeparator.setText(properties.getProperty(key));

		key = MindJetConstants.PREF_LEVEL_SEPARATOR;
		levelSeparator.setText(properties.getProperty(key));

		key = MindJetConstants.PREF_EOF_DELIMITER;
		eofDelimiter.setText(properties.getProperty(key));

		key = MindJetConstants.PREF_DATE_PATTERN;
		datePattern.setText(properties.getProperty(key));

	}

	@Override
	public void update() {
		String key = MindJetConstants.PREF_CONTENT_SEPARATOR;
		properties.setProperty(key, contentSeparator.getText().trim());

		key = MindJetConstants.PREF_LEVEL_SEPARATOR;
		properties.setProperty(key, levelSeparator.getText().trim());

		key = MindJetConstants.PREF_EOF_DELIMITER;
		properties.setProperty(key, eofDelimiter.getText().trim());

		key = MindJetConstants.PREF_DATE_PATTERN;
		properties.setProperty(key, datePattern.getText().trim());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		contentSeparator.setEnabled(b);
		levelSeparator.setEnabled(b);
		eofDelimiter.setEnabled(b);
		datePattern.setEnabled(b);
	}

}
