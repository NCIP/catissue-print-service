package edu.wustl.catissuecore.webservice.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Class to manage properties that will be used by webservice classes.
 * 
 * @author prafull_kadam
 * 
 */
public class PrinterPropertyHandler {

	/**
	 * static instance to hold all required properties
	 */
	private static Properties printimplClassProperties = null;

	/**
	 * @param path
	 *            The Path of the property file.
	 * @throws IOException
	 */
	public static void init(String path) throws IOException {
		try {
			printimplClassProperties = new Properties();
			printimplClassProperties.load(PrinterPropertyHandler.class
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
		if (printimplClassProperties == null) {
			init("printer.properties");
		}
		return (String) printimplClassProperties.get(propertyName);
	}
}
