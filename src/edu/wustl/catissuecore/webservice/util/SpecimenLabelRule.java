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

/**
 * Bean class for Specimen Label Rule for printing label
 * @author vijay_pande
 *
 */
public class SpecimenLabelRule 
{
	private String specimenClass;
	private String specimenType;
	private String labelType;
	private String dataOnLabel;
	private String printer;
	private String workStationIP;
	
	/**
	 * Method to get Specimen Class
	 * @return
	 */
	public String getSpecimenClass() 
	{
		return specimenClass;
	}
	
	/**
	 * Method to set Specimen Class
	 * @param specimenClass
	 */
	public void setSpecimenClass(String specimenClass) 
	{
		this.specimenClass = specimenClass;
	}
	
	/**
	 * Method to get Specimen Type
	 * @return
	 */
	public String getSpecimenType() 
	{
		return specimenType;
	}
	
	/**
	 * Method to set Specimen Type
	 * @param specimenType
	 */
	public void setSpecimenType(String specimenType) 
	{
		this.specimenType = specimenType;
	}
	
	/**
	 * Method to get Specimen Lable
	 * @return
	 */
	public String getLabelType() 
	{
		return labelType;
	}
	
	/**
	 * Method to set Specimen Lable
	 * @param labelType
	 */
	public void setLabelType(String labelType) 
	{
		this.labelType = labelType;
	}
	
	/**
	 * Method to get data on label to be printed
	 * @return
	 */
	public String getDataOnLabel() 
	{
		return dataOnLabel;
	}
	
	/**
	 * Method to set data on lable to be printed
	 * @param dataOnLabel
	 */
	public void setDataOnLabel(String dataOnLabel) 
	{
		this.dataOnLabel = dataOnLabel;
	}

	/**
	 * @return the printer
	 */
	public String getPrinter() {
		return printer;
	}

	/**
	 * @param printer the printer to set
	 */
	public void setPrinter(String printer) {
		this.printer = printer;
	}

	/**
	 * @return the workStationIP
	 */
	public String getWorkStationIP() {
		return workStationIP;
	}

	/**
	 * @param workStationIP the workStationIP to set
	 */
	public void setWorkStationIP(String workStationIP) {
		this.workStationIP = workStationIP;
	}
}
