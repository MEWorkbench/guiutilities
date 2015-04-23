package pt.uminho.ceb.biosystems.mew.guiutilities.gui.components.searchableList;

import java.util.Set;


public class InternalMatchStringListModel<T extends Comparable<T>> extends SearchableStringListModel<T>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	public InternalMatchStringListModel(T[] stringsArray) {
//        super(stringsArray);
//    }
	
	public InternalMatchStringListModel(Set<T> strings){
		super(strings);
	}

    protected String setup(String search) {
        return search.toLowerCase();
    }

    protected boolean accept(String search, String item) {
        // Tests if the item CONTAINS a string (case-insensitive).
        return item.toLowerCase().contains(search);
    }


	
}
