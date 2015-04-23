package pt.uminho.ceb.biosystems.mew.guiutilities.gui.components.searchableList;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SearchableListPanel<T extends Comparable<T>> extends javax.swing.JPanel {
	
	private static final long serialVersionUID = 1L;
	protected JTextField searchTextField;
	protected JScrollPane schrollPane;
	protected JList searchableList;
	protected List<String> words;
	protected InternalMatchStringListModel<T> internalListModel;
//	private String panelLabel;
	
	
	public SearchableListPanel(){
		super();
		initGUI();
		InternalMatchStringListModel<T> n = new InternalMatchStringListModel<T>(new TreeSet<T>());
		setModel(n);
	}
	
	public void setLabel(String label){
		this.setBorder(new TitledBorder(null, label, TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, null));
	}
	
	public SearchableListPanel(Set<T> words, String label) {
		super();
		initGUI();
		setLabel(label);
		InternalMatchStringListModel<T> n = new InternalMatchStringListModel<T>(words);
		setModel(n);
	}
	
	public void setModel(InternalMatchStringListModel<T> model){
		internalListModel = model;
		searchableList.setModel(model);
		internalListModel.search("");
	}
	
	private void initGUI() {
		try {

			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			{
				this.searchableList = new JList();
				
		        schrollPane = new JScrollPane(searchableList);
		        this.add(schrollPane,BorderLayout.CENTER);

			}
			{
				searchTextField = new JTextField();
				this.add(searchTextField,BorderLayout.NORTH);
				searchTextField.setText("");
				
				searchTextField.getDocument().addDocumentListener(new DocumentListener() {
		            public void changedUpdate(DocumentEvent e) { search(); }
		            public void insertUpdate(DocumentEvent e) { search(); }
		            public void removeUpdate(DocumentEvent e) { search(); }
		        });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setDefault(String text){
		if(words.contains(text)){
//			searchTextField.setText(text);
//			search();
			searchableList.setSelectedValue(text, true);
		}
	}

	private void search() {
		String text = searchTextField.getText();
		internalListModel.search(text);
	}
	
	public InternalMatchStringListModel<T> getModel(){
		return internalListModel;
	}
	
	public T getSelectedValue(){
		return (T) this.searchableList.getSelectedValue();
	}

	public int getSelectedIndex() {
		return this.searchableList.getSelectedIndex();
	}
	

	public void removeElementAt(int selectedIndex) {
		
		internalListModel.removeElementAt(selectedIndex);
		
	}
	
	public int[] getSelectedItemsInds(){
		return this.searchableList.getSelectedIndices();
	}
	
	public void setNewWords(List<String> new_words){
		
		this.words = new_words;
		TreeSet<T> stuff = new TreeSet(new_words);
		InternalMatchStringListModel<T> n = new InternalMatchStringListModel<T>(stuff);
		setModel(n);
	}

	public JList getSearchableList(){
		return searchableList;
	}

}
