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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Class to read data from Microsoft Excel Sheet
 * @author vijay_pande
 *
 */
public class ExcelFileReader 
{
	private String fileName;
	private HSSFSheet sheet;
	private String[][] data;
	//0=String, 1= Numeric, 2=Date
	public static Integer[] columnDataType=new Integer[]{};
	public static final String DATE_PATTERN_MM_DD_YYYY = "MM-dd-yyyy";
	
	/**
	 * Constructor for class which takes Excel  file name as input parameter
	 * @param fileName Excel file to be read
	 * @throws Exception Generic Exception
	 */
	ExcelFileReader(String fileName) throws Exception
	{
		this.fileName=fileName;
		this.init();
		this.readData();
	}
	
	/**
	 * Method to perform initialization tasks for the class
	 * @throws IOException
	 */
	private void init() throws IOException
	{
		File excelSheet=new File(this.fileName);
		InputStream s=new FileInputStream(excelSheet);
		HSSFWorkbook workbook=new HSSFWorkbook(s);
		sheet=workbook.getSheetAt(0);
	}

	/**
	 * Method to read whole Excel sheet into data structure to avoid repetitive calls to file IO
	 */
	private void readData()
	{
		HSSFRow  row=null;
		int noOfRows=sheet.getPhysicalNumberOfRows();
		data=new String[noOfRows][];
		for (int h = 0; h <noOfRows ; h++) 
		{
			row = sheet.getRow(h);
			if(row!=null)
				data[h] = getRowContent(row);
			else
				data[h]=new String[0];
		}
	}
	
	/**
	 * Method to read content of one row of Excel sheet
	 * @param row HSSFRow row whose content to be read
	 * @return
	 */
	private String[] getRowContent(HSSFRow row)
	{
		HSSFCell cell = null;
		int noOfColumn= row.getPhysicalNumberOfCells();
		String[] rowContent=new String[noOfColumn];
		for (short i = 0; i < noOfColumn; i++) 
		{  
			try{
			cell = row.getCell(i);
			if (cell==null)
				rowContent[i]="";
			else 
			{
				if(columnDataType.length>i && columnDataType[i]!=null)
				{
					switch(columnDataType[i])
					{
						case 0:
						{
							HSSFRichTextString strCell = cell.getRichStringCellValue();
							rowContent[i]=strCell.toString();
							break;
						}
						case 1:
						{
							rowContent[i]=String.valueOf(cell.getNumericCellValue());
							break;
						}
						case 2:
						{
							Date  date=cell.getDateCellValue();
							rowContent[i]=parseDateToString(date, DATE_PATTERN_MM_DD_YYYY);
							break;
						}
					}
				}
				else
				{
					HSSFRichTextString strCell = cell.getRichStringCellValue();
					rowContent[i]=strCell.toString();
				}
			}
			}catch(Exception e){
				System.out.println("columnDataType["+i+"]"+columnDataType[i]);
			}
		}
		return rowContent;
	}
	
	/**
	 * Method to get number of rows in Excel sheet  
	 * @return rowCount of Excel sheet
	 */
	public int getRowCount()
	{
		return sheet.getPhysicalNumberOfRows();
	}
	
	/**
	 * Method to return a row depending
	 * @param index
	 * @return
	 */
	public String[] getRowAt(int index)
	{
		return data[index];
	}
	public String[][] getAllRows()
	{
		return data;
	}
	
	public static void main(String[] args) throws Exception
	{
		ExcelFileReader efr=new ExcelFileReader("D:/TestBuild/catissue1.2.0.1/caTissueCore_caCORE_Client/PathogenDiscoverySerumEgypt2_Bid7529.xls");
		String[][] str=efr.getAllRows();
		for(int i=0;i<str.length;i++)
		{
			int noOfCol=str[i].length;
			for(int j=0;j<noOfCol;j++)
			System.out.print(str[i][j]);
			System.out.println();
		}
		
	}
	
	/**
	 * Parses the Date in given format and returns the string representation.
	 * @param date the Date to be parsed.
	 * @param pattern the pattern of the date.
	 * @return
	 */
	private String parseDateToString(Date date, String pattern)
	{
	    String d = "";
	    if(date!=null)
	    {
		    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			d = dateFormat.format(date);
	    }
	    return d;
	}
	
	/**
	 * Method to set datatype of each column of the excel sheet
	 * @param columnDataType
	 */
	public void setColumnDataType(Integer[] columnDataType)
	{
		ExcelFileReader.columnDataType=columnDataType;
	}
}
