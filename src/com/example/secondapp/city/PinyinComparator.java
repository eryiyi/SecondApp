package com.example.secondapp.city;

import java.util.Comparator;
import com.example.secondapp.model.SortModel;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<SortModel> {

	public int compare(SortModel o1, SortModel o2) {
		if (o1.getFirst().equals("@")
				|| o2.getFirst().equals("#")) {
			return -1;
		} else if (o1.getFirst().equals("#")
				|| o2.getFirst().equals("@")) {
			return 1;
		} else {
			return o1.getFirst().compareTo(o2.getFirst());
		}
	}

}
