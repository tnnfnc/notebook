package it.tnnfnc.mynotebook.plugins.mindjet;

import java.util.ListResourceBundle;
import java.util.Properties;
import java.util.ResourceBundle;

import it.tnnfnc.mynotebook.plugins.AbstractPlugin;

public class MindJetMapsPlugin extends AbstractPlugin {
	/**
	 * 
	 */
	public MindJetMapsPlugin() {
		super();
		resource = (ListResourceBundle) ResourceBundle.getBundle(MindJetPluginBundle.class.getName());
		properties = new Properties();
		notesParser = new MindJetParser();
		bookHierarchyBuilder = new MindJetHierarchyBuilder();
		propertiesPanel = new MindJetSettingsPanel(properties);
		name = resource.getString(MindJetConstants.MINDJET);
	}

}
