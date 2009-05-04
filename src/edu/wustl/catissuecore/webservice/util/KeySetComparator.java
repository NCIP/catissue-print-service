package edu.wustl.catissuecore.webservice.util;

import java.util.Comparator;

public class KeySetComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		String k1 = (String)o1;
		String k2 = (String)o2;
		String[] k1Set = k1.split("_");
		String[] k2Set = k2.split("_");
		int k1Index = k1Set.length;
		int k2Index = k2Set.length;
		String k1Id = k1Set[k1Index-1];
		String k2Id = k2Set[k2Index-1];
		try
		{
			int id1 = Integer.parseInt(k1Id);
			int id2 = Integer.parseInt(k2Id);
			if(id1 < id2)
			{
				return -1;
			}
			else if(id1 > id2)
			{
				return 1;
			}
			else if(id1 == id2)
			{
				return 0;
			}
		}
		catch(Exception e)
		{
			return 0;
		}
		// TODO Auto-generated method stub
		return 0;
	}

}
