package search;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * A comparator that compares Strings
 */
class ValueComparator implements Comparator<String>{

	Map<String, Integer> map = new HashMap<String, Integer>();

	public ValueComparator(Map<String, Integer> map){
		this.map.putAll(map);
	}

	@Override
	public int compare(String s1, String s2) {
		int compareValue = Integer.compare(map.get(s2), map.get(s1));
		if(compareValue == 0) {
		     compareValue = s1.compareTo(s2);
		}
		return compareValue;
	}
	
	public static TreeMap<String, Integer> sortMapByValue(Map<String, Integer> map){
		Comparator<String> comparator = new ValueComparator(map);
		//TreeMap is a map sorted by its keys. 
		//The comparator is used to sort the TreeMap by keys. 
		TreeMap<String, Integer> result = new TreeMap<>(comparator);
		result.putAll(map);
		return result;
	}
}
