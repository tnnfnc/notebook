package it.tnnfnc.mynotebook.plugins;

/**
 * Plugin factory class.
 * 
 * @author FT
 *
 */
public class PluginFactory {

	/**
	 * Return a plugin from its name, if available.
	 * 
	 * @param className
	 *            the plugin name.
	 * @return a plugin from its name.
	 */
	public I_BookReaderPlugin getPlugin(String className) {
		I_BookReaderPlugin plugIn;
		try {
			plugIn = (I_BookReaderPlugin) Class.forName(className).getDeclaredConstructor().newInstance();
			return plugIn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
