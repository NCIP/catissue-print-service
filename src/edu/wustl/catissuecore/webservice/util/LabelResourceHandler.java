/**
 * This class reads the LabelResource.properties
 *  which has the field names to be displayed on the label
 */
package edu.wustl.catissuecore.webservice.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @author sunil_agarwal
 *
 */
public class LabelResourceHandler {
	/**
	 * static instance to hold all required properties
	 */
	private static Properties LabelResourceProperties = null;

	/**
	 * @param path
	 *            The Path of the property file.
	 * @throws IOException
	 */
	public static void init(String path) throws IOException {
		try {
			LabelResourceProperties = new Properties();
			LabelResourceProperties.load(PrinterPropertyHandler.class
					.getClassLoader().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * To get the property value to for the given name.
	 * 
	 * @param propertyName
	 *            name of property Key
	 * @return String The property value
	 * @throws IOException
	 */
	public static String getValue(String propertyName) throws IOException {
		if (LabelResourceProperties == null) {
			init("LabelResource.properties");
		}
		return (String) LabelResourceProperties.get(propertyName);
	}

}
