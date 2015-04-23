package pt.uminho.ceb.biosystems.mew.guiutilities.gui.components.searchableList;

import java.util.Arrays;
import java.util.TreeSet;

public class InitialMatchStringListModel extends SearchableStringListModel{
	
	 public InitialMatchStringListModel(String[] stringsArray) {
	        super(new TreeSet<String>(Arrays.asList(stringsArray)));
	    }

	    protected String setup(String search) {
	        return search.toLowerCase();
	    }

	    protected boolean accept(String search, String item) {
	        // Tests if the item STARTS with a string (case-insensitive).
	        return item.toLowerCase().startsWith(search);
	    }
}
