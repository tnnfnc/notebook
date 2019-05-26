package it.tnnfnc.mynotebook.map;

/**
 * Factory for mind map builder class.
 * 
 * @author FT
 *
 */
public class MindMapBuilderFactory {

	/**
	 * @param clazz
	 * @return
	 */
	public static I_MapBuilder getInstance(String clazz) {

		try {
			// return (I_MapBuilder) Class.forName(clazz).newInstance();
			return (I_MapBuilder) Class.forName(clazz).getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			return null;
		}
	}

}
