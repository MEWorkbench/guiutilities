package pt.uminho.ceb.biosystems.mew.guiutilities.gui.components.searchableList;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractListModel;

public abstract class SearchableStringListModel<T extends Comparable<T>> extends AbstractListModel{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private Set<T> stringsList;
	    private ArrayList<T> resultsArrayList;
	    private String lastSearch ="";

//	    public SearchableStringListModel(T[] stringsArray) {
//	        this(new TreeSet<T>(stringsArray));
//	    }

	    public SearchableStringListModel(Set<T> stringsList) {
	     
	    	this.stringsList = new TreeSet<T>(stringsList);
//	    	Collections.sort();
	        resultsArrayList = new ArrayList<T>();
	        
	    }

	    // The following methods are related to the ListModel interface.
	    // You MUST NOT change the signature of these methods!!!

	    public T getElementAt(int index) {
	        return resultsArrayList.get(index);
	    }

	    public int getSize() {
	        return resultsArrayList.size();
	    }

	    // The following methods are NOT related to the ListModel interface
	    // or the AbstractListModel class.
	    // These methods are "custom" and lets to do other operations on the
	    // model. In this specific example the content of the model can be
	    // "filtered" using a search string.

	    public final void search(String search) {
	        // Setups the search string.
	    	lastSearch = search;
	        search = setup(search);

	        int oldSize = resultsArrayList.size();

	        if (oldSize > 0) {
	            // Removes all the content from the results list.
	            resultsArrayList.clear();

	            // IMPORTANT!!!
	            // Notifies all listeners that some (all, in this case)
	            // content has been removed.
	            fireIntervalRemoved(this, 0, oldSize - 1);
	        }

	        for (T item : stringsList) {
	            // Invokes accept() implemented in a subclass to test
	            // if the item is to be accepted or not.
	            if (accept(search, item.toString())) {
	                resultsArrayList.add(item);
	            }
	        }

	        int newSize = resultsArrayList.size();

	        if (newSize > 0) {
	            // IMPORTANT!!!
	            // Notifies all listeners that some content has been added.
	            fireIntervalAdded(this, 0, newSize - 1);
	        }
	    }

	    public void removeElementAt(int selectedIndex) {
			Object ob = getElementAt(selectedIndex);		
			stringsList.remove(ob);
			search(lastSearch);
			
		}
	    
	    public void addElement(T value){
	    	stringsList.add(value);
	    	search(lastSearch);
	    }
	    
	    public void removeAllElements(){
	    	stringsList.clear();
	    	search("");
	    }
	    
	    public void removeVisibleItems(){
	    	stringsList.removeAll(resultsArrayList);
	    	search(lastSearch);
	    }
	    
	    // The setup() method CAN be implemented in a subclass.
	    protected String setup(String search) {
	        // Returns the same string. This is a "default" behaviour.
	        return search;
	    }

	    // The accept() method MUST be implemented in a subclass.
	    protected abstract boolean accept(String search, String item);

}
