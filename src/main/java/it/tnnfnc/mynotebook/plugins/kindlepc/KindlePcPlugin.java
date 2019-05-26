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
package it.tnnfnc.mynotebook.plugins.kindlepc;

import java.util.ListResourceBundle;
import java.util.Properties;
import java.util.ResourceBundle;

import it.tnnfnc.mynotebook.plugins.AbstractPlugin;

/**
 * @author FT
 *
 */
public class KindlePcPlugin extends AbstractPlugin {

	/**
	 * 
	 */
	public KindlePcPlugin() {
		super();
		this.resource = (ListResourceBundle) ResourceBundle.getBundle(KindlePcPluginBundle.class.getName());
		this.properties = new Properties();
		this.notesParser = new KindlePcParser();
		this.bookHierarchyBuilder = new KindlePcHierarchyBuilder();
		this.propertiesPanel = new KindlePcSettingsPanel(properties);
		name = resource.getString(KindlePcConstants.KINDLE);
	}
}
