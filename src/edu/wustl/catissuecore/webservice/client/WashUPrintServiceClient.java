/**
 * <p>Title: WashUPrintServiceClient Class>
 * <p>Description:	WashUPrintServiceClient is a class that is default client of
 * the print web service  
 * Copyright:    Copyright (c) year
 * Company: Washington University, School of Medicine, St. Louis.
 * @author Amit Doshi
 * @version 1.00
 * Created on Nov 1, 2007
 */
package edu.wustl.catissuecore.webservice.client;

import java.util.LinkedHashMap;

import edu.wustl.catissuecore.webservice.util.WashuLabelFileWriter;
import edu.wustl.webservice.catissuecore.print.PrintServiceClient;
import edu.wustl.webservice.catissuecore.print.PrintXMLParser;

public class WashUPrintServiceClient implements PrintServiceClient{


	/*To implement the PrintService interface 
	 * this class define the actual functionality   
	 * of web method
	 */
	PrintXMLParser pxp = new PrintXMLParser();
	
	public String print(String xmlFormat)
	{
		try 
		{
			System.out.println("In WashUPrintServiceClient");
			LinkedHashMap objMap = pxp.getPrintMap(xmlFormat);
			printDomainObject(objMap);
			return "Successed";

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Failed *************");
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	
	void printDomainObject(LinkedHashMap obj) throws Exception {
		System.out.println("In WashUPrintServiceClient.printDomainObject() --------------------");
		new WashuLabelFileWriter().createFile(obj);//obj = {Fluid_269={label=90, barcode=90, printerType= , printerLocation= }, Tissue_270={label=91, barcode=91, printerType= , printerLocation= }}
		System.out.println("------------------------------");
	}
}