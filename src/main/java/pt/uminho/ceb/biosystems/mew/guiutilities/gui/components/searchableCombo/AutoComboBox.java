package pt.uminho.ceb.biosystems.mew.guiutilities.gui.components.searchableCombo;

import java.awt.event.ItemEvent;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxEditor;

public class AutoComboBox extends JComboBox{
	
	  public TreeSet<String> getValues(){
		  return this.list;
	  }

	private class AutoTextFieldEditor extends BasicComboBoxEditor {

	    private AutoTextField getAutoTextFieldEditor() {
	      return (AutoTextField) editor;
	    }

	    AutoTextFieldEditor(TreeSet<String> list, String initial) {
	      editor = new AutoTextField(list, AutoComboBox.this);
	    }
	  }

	public AutoComboBox(){
		isFired = false;
		setEditable(false);
		list = new TreeSet<String>();
	}

	public void setNewValues(TreeSet<String> list, String initial){
		
		this.list = list;
		
		autoTextFieldEditor = new AutoTextFieldEditor(list, initial);
		setEditable(true);
		setModel(new DefaultComboBoxModel(list.toArray()) {

			protected void fireContentsChanged(Object obj, int i, int j) {
				if (!isFired)
					super.fireContentsChanged(obj, i, j);
			}

		});
		setEditor(autoTextFieldEditor);
		this.setSelectedIndex(getIndexOfInitial(initial,list));
		
	}
	
	public AutoComboBox(TreeSet<String> list, String initial) {
		
		this.list = list;
		isFired = false;
		autoTextFieldEditor = new AutoTextFieldEditor(list, initial);
		setEditable(true);
		setModel(new DefaultComboBoxModel(list.toArray()) {

			protected void fireContentsChanged(Object obj, int i, int j) {
				if (!isFired)
					super.fireContentsChanged(obj, i, j);
			}

		});
		setEditor(autoTextFieldEditor);
		this.setSelectedIndex(getIndexOfInitial(initial,list));
	}

	public void addValue(String s){

		list.add(s);
		setNewValues(list, s);
	}
	
	public void removeValue(String text){
		
		list.remove(text);
		setNewValues(list, "");
	}
	
	// If the last element from the combobox is 
	// removed then the list is cleaned as well 
	public void removeLastValue(){
		
		list = new TreeSet<String>();
		removeAllItems();
	}
	
	private int getIndexOfInitial(String initial, TreeSet<String> list) {

		int index= 0;
		  int counter = 0;
		  for(String s : list){
			  if(s.equals(initial))
				  index = counter;
			  counter++;
		  }
		  return index;
	  }

	  public boolean isCaseSensitive() {
		  return autoTextFieldEditor.getAutoTextFieldEditor().isCaseSensitive();
	  }

	  public void setCaseSensitive(boolean flag) {
	    autoTextFieldEditor.getAutoTextFieldEditor().setCaseSensitive(flag);
	  }

	  public boolean isStrict() {
	    return autoTextFieldEditor.getAutoTextFieldEditor().isStrict();
	  }

	  public void setStrict(boolean flag) {
	    autoTextFieldEditor.getAutoTextFieldEditor().setStrict(flag);
	  }

	  public Set getDataList() {
	    return autoTextFieldEditor.getAutoTextFieldEditor().getDataList();
	  }

	  public void setDataList(Set list) {
	    autoTextFieldEditor.getAutoTextFieldEditor().setDataList(list);
	    setModel(new DefaultComboBoxModel(list.toArray()));
	  }

	  void setSelectedValue(Object obj) {
	    if (isFired) {
	      return;
	    } else {
	      isFired = true;
	      setSelectedItem(obj);
	      fireItemStateChanged(new ItemEvent(this, 701, selectedItemReminder,
	          1));
	      isFired = false;
	      return;
	    }
	  }

	  protected void fireActionEvent() {
	    if (!isFired)
	      super.fireActionEvent();
	  }

	  private AutoTextFieldEditor autoTextFieldEditor;

	  private boolean isFired;
	  
	  private TreeSet<String> list;

	
}
