package hack.dialog.common;

import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class DialogFlowUtil {
	//End string with period if needed.
	public static String formatPeriod(String str) {
		String newStr;
		System.out.println("before " + str);
		if (str != null && !str.endsWith(".")) {
			StringBuilder sb = new StringBuilder();
			sb.append(str).append(".");
			newStr = sb.toString();
		} else {
			newStr = str;
		}
		System.out.println("after " + newStr);
		return newStr;
	}
	
	public static void removeDuplicates(List<String> inList, boolean toCaps) {
		// add elements to al, including duplicates
		Set<String> hs = new HashSet<>();
		hs.addAll(inList);
		inList.clear();
		inList.addAll(hs);
		if (toCaps) {
			ListIterator<String> iterator = inList.listIterator();
			while (iterator.hasNext()) {
				iterator.set(iterator.next().toUpperCase());
			}
		}

	}
 
  
 

}
