/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-print-service/LICENSE.txt for details.
 */

package edu.wustl.catissuecore.webservice.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

import edu.wustl.catissuecore.webservice.util.LabelResourceHandler;
import edu.wustl.catissuecore.webservice.util.PrintWebServiceConstants;

/**
 * This is the class which reads input rules through excel sheet and populates map of rules. Also generates file content as per rule.
 * @author vijay_pande
 *
 */
public class SpecimenLabelRuleHandler 
{
	private static HashMap<String, SpecimenLabelRule> printLabelRules;
	private static SpecimenLabelRule defaultSpecimenLabelRule;
	
	public static void init() 
	{
		defaultSpecimenLabelRule = new SpecimenLabelRule();
		defaultSpecimenLabelRule.setLabelType(PrintWebServiceConstants.SIDE);
		defaultSpecimenLabelRule.setDataOnLabel(PrintWebServiceConstants.SPECIMEN_LABEL);
		
		printLabelRules = new HashMap<String, SpecimenLabelRule>();
		try
		{
			//ExcelFileReader inputFile=new ExcelFileReader(PrinterPropertyHandler.getValue(PrintWebServiceConstants.RULES_INPUT_FILENAME));
	  	   ExcelFileReader inputFile = new ExcelFileReader(System.getProperty("user.dir") + "/../print/" + PrinterPropertyHandler.getValue(PrintWebServiceConstants.RULES_INPUT_FILENAME));
           System.out.println("JAVA_HOME "+ System.getProperty("user.dir"));
           System.out.println("JBOSS_HOME "+ System.getenv("jboss.home"));

           String[][] data=inputFile.getAllRows();
           int noOfRows=data.length;
           for(int i=1;i<noOfRows;i++)
           {
				String[] row = data[i];
				int noOfColumns = data[0].length;
				if(row!=null && row.length == noOfColumns)
				{
					SpecimenLabelRule rule = new  SpecimenLabelRule();
					rule.setSpecimenClass(row[PrintWebServiceConstants.INDEX_SPECIMEN_CLASS]);  //0
					rule.setSpecimenType(row[PrintWebServiceConstants.INDEX_SPECIMEN_TYPE]); //1
					rule.setLabelType(row[PrintWebServiceConstants.INDEX_LABEL_FORMAT]); //2
					rule.setDataOnLabel(row[PrintWebServiceConstants.INDEX_DATA_ON_LABEL]); //3
					rule.setPrinter(row[PrintWebServiceConstants.INDEX_PRINTER]); //4
					rule.setWorkStationIP(row[PrintWebServiceConstants.INDEX_WORKSTAION_IP]); //5
					String key = row[PrintWebServiceConstants.INDEX_SPECIMEN_CLASS]+"_"+row[PrintWebServiceConstants.INDEX_SPECIMEN_TYPE]+"_"+row[PrintWebServiceConstants.INDEX_WORKSTAION_IP];
					//System.out.println("rules check "+rule.getPrinter()+rule.getWorkStationIP());
					printLabelRules.put(key, rule);
				}
           }
		}
		catch (Exception e) 
		{
			System.out.println("Error while parsing rules for printing labels"+e);
		}
	}
	
	/**
	 * Method to return object of SpecimenLabelRule depending on specimenClass and specimenType
	 * @param specimenClass
	 * @param specimenType
	 * @return specimenLabelRule object of SpecimenLabelRule
	 */
	public static SpecimenLabelRule getRule(String specimenClass, String specimenType,String workStationIP) throws IOException // All three are null
	{
		SpecimenLabelRule specimenLabelRule = defaultSpecimenLabelRule;
		System.out.println("specimenLabelRule 1 "+ specimenLabelRule);
		
		String key = specimenClass+"_"+specimenType+"_"+workStationIP;
		String keyAny = specimenClass+"_"+PrintWebServiceConstants.ANY+"_"+workStationIP;
		if(printLabelRules.containsKey(key))
		{
			System.out.println("specimenLabelRule a "+ specimenLabelRule);

			return specimenLabelRule = printLabelRules.get(key);
		}
		else if(printLabelRules.containsKey(keyAny))
		{
			System.out.println("specimenLabelRule b "+ specimenLabelRule);

			return specimenLabelRule = printLabelRules.get(keyAny);
		}
		Set<String> keySetForRule = new LinkedHashSet<String>();
		keySetForRule = printLabelRules.keySet();
		System.out.println("keySetForRule " + keySetForRule.size());
		Iterator keySetIterator = keySetForRule.iterator();
		while(keySetIterator.hasNext())
		{
			String tempKey = (String)keySetIterator.next();
			String[] keySplit = tempKey.split("_");
			String keyWorkStationIP = keySplit[2];
			String replacedKeyIP = keyWorkStationIP.replace(".", "_");//127_0_0_1		
			String replacedIP = workStationIP.replace(".", "_");	//10_88_164_26		
			String[] workStationIPSplit = replacedKeyIP.split("_"); //[127, 0, 0, 1]
			String[] currentWorkStationIP = replacedIP.split("_"); // [10, 88, 164, 26]
			if(currentWorkStationIP[0].equals(workStationIPSplit[0]) && currentWorkStationIP[1].equals(workStationIPSplit[1]) && workStationIPSplit[2].equalsIgnoreCase("XXX"))
			{
				String newKey = specimenClass+"_"+specimenType+"_"+keyWorkStationIP;
				String newKeyAny = specimenClass+"_"+PrintWebServiceConstants.ANY+"_"+keyWorkStationIP;
				if(printLabelRules.containsKey(newKey))
				{
					System.out.println("specimenLabelRule 2 "+ specimenLabelRule);
					return specimenLabelRule = printLabelRules.get(newKey);
				}
				else if(printLabelRules.containsKey(newKeyAny))
				{
					System.out.println("specimenLabelRule  3 "+ specimenLabelRule);
					return specimenLabelRule = printLabelRules.get(newKeyAny);
				}
			}
		}
		
		
	//	System.out.println("specimenLabelRule 4  "+ specimenLabelRule);
		//else {
			//throw new IOException("IP Adress not found");
		//}
		return specimenLabelRule;
	}
	
	/**
	 * Method generated file content depending on the rule
	 * @param map PrintRule Map
	 * @return
	 * @throws IOException
	 */
	public static String getFileContentForSpecimen(HashMap<String, String> map) throws IOException//map = {label=90, barcode=90, printerType= , printerLocation= }
	{
		String specimenClass = map.get(PrintWebServiceConstants.SPECIMEN_CLASS); // null
		String specimenType = map.get(PrintWebServiceConstants.SPECIMEN_TYPE); // null
		String workStationIP = map.get(PrintWebServiceConstants.USER_IPADDRESS); //workStationIP =  com.sun.jdi.InternalException: Got error code in reply:35 occurred while retrieving value.
		SpecimenLabelRule specimenLabelRule = getRule(specimenClass, specimenType, workStationIP);
		String dataToPrint = specimenLabelRule.getDataOnLabel();//Specimen Label
		System.out.println("dataprint "+ dataToPrint);
		StringTokenizer stringTokenizer=new StringTokenizer(dataToPrint, ",");
		StringBuilder stringBuilder=new StringBuilder();
	    stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_LABELNAME)+" = \""+specimenLabelRule.getLabelType()+"\""+PrintWebServiceConstants.NEWLINE);
    	for (int x = 0; stringTokenizer.hasMoreTokens(); x++)
		{
			String field = stringTokenizer.nextToken().trim().toUpperCase();
			if(field.equalsIgnoreCase(PrintWebServiceConstants.SPECIMEN_LABEL))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_SPECIMEN_LABEL)+" = \""+map.get(PrintWebServiceConstants.SPECIMEN_LABEL)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else if(field.equalsIgnoreCase(PrintWebServiceConstants.SPECIMEN_BARCODE))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_SPECIMEN_BARCODE)+" = \""+map.get(PrintWebServiceConstants.SPECIMEN_BARCODE)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else if(field.equalsIgnoreCase(PrintWebServiceConstants.SPECIMEN_TYPE))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_SPECIMEN_TYPE)+" = \""+map.get(PrintWebServiceConstants.SPECIMEN_TYPE)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else if(field.equalsIgnoreCase(PrintWebServiceConstants.SPECIMEN_CLASS))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_SPECIMEN_CLASS)+" = \""+map.get(PrintWebServiceConstants.SPECIMEN_CLASS)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else if(field.equalsIgnoreCase(PrintWebServiceConstants.CONCENTRATION))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_CONCENTRATION)+" = \""+map.get(PrintWebServiceConstants.CONCENTRATION)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else if(field.equalsIgnoreCase(PrintWebServiceConstants.QUANTITY))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_QUANTITY)+" = \""+map.get(PrintWebServiceConstants.QUANTITY)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else if(field.equalsIgnoreCase(PrintWebServiceConstants.CP_TITLE))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_CP_TITLE)+" = \""+map.get(PrintWebServiceConstants.CP_TITLE)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else if(field.equalsIgnoreCase(PrintWebServiceConstants.PARTICIPANT_PROTOCOL_IDENTIFIER))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_PARTICIPANT_PROTOCOL_IDENTIFIER)+" = \""+map.get(PrintWebServiceConstants.PARTICIPANT_PROTOCOL_IDENTIFIER)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else if(field.equalsIgnoreCase(PrintWebServiceConstants.PATHOLOGICAL_STATUS))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_PATHOLOGICAL_STATUS)+" = \""+map.get(PrintWebServiceConstants.PATHOLOGICAL_STATUS)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else if(field.equalsIgnoreCase(PrintWebServiceConstants.SPECIMEN_LINEAGE))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_SPECIMEN_LINEAGE)+" = \""+map.get(PrintWebServiceConstants.SPECIMEN_LINEAGE)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else if(field.equalsIgnoreCase(PrintWebServiceConstants.SPECIMEN_STORAGE_CONTAINER_NAME))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_SPECIMEN_STORAGE_CONTAINER_NAME)+" = \""+map.get(PrintWebServiceConstants.SPECIMEN_STORAGE_CONTAINER_NAME)+"_"+map.get(PrintWebServiceConstants.SPECIMEN_POSITION_DIMENSION_ONE)+"_"+map.get(PrintWebServiceConstants.SPECIMEN_POSITION_DIMENSION_TWO)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else if(field.equalsIgnoreCase(PrintWebServiceConstants.SPECIMEN_COMMENT))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_SPECIMEN_COMMENT)+" = \""+map.get(PrintWebServiceConstants.SPECIMEN_COMMENT)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else if(field.equalsIgnoreCase(PrintWebServiceConstants.SPECIMEN_COLLECTION_STATUS))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_SPECIMEN_COLLECTION_STATUS)+" = \""+map.get(PrintWebServiceConstants.SPECIMEN_COLLECTION_STATUS)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else if(field.equalsIgnoreCase(PrintWebServiceConstants.SPECIMEN_CREATED_ON))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_SPECIMEN_CREATED_ON)+" = \""+map.get(PrintWebServiceConstants.SPECIMEN_CREATED_ON)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else if(field.equalsIgnoreCase(PrintWebServiceConstants.SPECIMEN_MESSAGE_LABEL))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_SPECIMEN_MESSAGE_LABEL)+" = \""+map.get(PrintWebServiceConstants.SPECIMEN_MESSAGE_LABEL)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else if(field.equalsIgnoreCase(PrintWebServiceConstants.SPECIMEN_TISSUE_SITE))
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_SPECIMEN_TISSUE_SITE)+" = \""+map.get(PrintWebServiceConstants.SPECIMEN_TISSUE_SITE)+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else
			{
				System.out.println("Not found:::::::"+field);
			}
		}
			if(specimenLabelRule != null && specimenLabelRule.getPrinter() != null)
			{
				stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_PRINTER)+" = \""+specimenLabelRule.getPrinter()+"\""+PrintWebServiceConstants.NEWLINE);
			}
			else 
			{
				throw new IOException("No printer configuration found");
				
			}
			stringBuilder.append(LabelResourceHandler.getValue(PrintWebServiceConstants.DISPLAY_LABELQUANTITY)+" = \"1\""+PrintWebServiceConstants.NEWLINE);
			stringBuilder.append(PrintWebServiceConstants.DISPLAY_END +PrintWebServiceConstants.NEWLINE);

			return stringBuilder.toString();
	}
	
	/**
	 * Method to get Label file name depending on label type
	 * @param labelType
	 * @return
	 * @throws IOException
	 */
	private static String getLabelFileName(String labelType) throws IOException 
	{
		if(labelType.equalsIgnoreCase(PrintWebServiceConstants.CAP))
		{
			return PrinterPropertyHandler.getValue(PrintWebServiceConstants.CAP_LABEL_FILENAME);
		}
		else if(labelType.equalsIgnoreCase(PrintWebServiceConstants.CAP_PLUS_SIDE))
		{
			return PrinterPropertyHandler.getValue(PrintWebServiceConstants.CAP_SIDE_LABEL_FILENAME);
		}
		else if(labelType.equalsIgnoreCase(PrintWebServiceConstants.SIDE))
		{
			return PrinterPropertyHandler.getValue(PrintWebServiceConstants.SIDE_LABEL_FILENAME);
		}
		return "";
	}
}

