package pt.uminho.ceb.biosystems.mew.guiutilities.gui.components.searchableCombo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SearchableComboBox extends JComboBox {
	
	protected static final Color HILIT_COLOR = Color.LIGHT_GRAY;
	private static Set<Integer> codes;
	static{
		codes = new HashSet<Integer>();
		codes.add(10);
		codes.add(27);
		codes.add(37);
		codes.add(38);
		codes.add(39);
		codes.add(40);
		codes.add(224);
		codes.add(225);
		codes.add(226);
		codes.add(227);
		codes.add(16);
		codes.add(35);
		codes.add(36);
	}
	private ArrayList<String> allItemsArrayList;
	private TreeSet<String> allItemsList;
	
	private String strAuto = "";
	private Set<IValidatedComboSearch> listeners = null;
	private boolean lastStatus = true;
	
	private boolean typing = false;
	private MouseListener[] originalComboButtonMouseListeners, comboButtonMouseListeners;
	
	private boolean listenersAlreadyInitialized = false;
	
//	protected Color defaultColor;
	public TreeSet<String> getValues(){
		  return allItemsList;
	}
	
	public SearchableComboBox()	{
		this(new TreeSet<String>(), null);
	}
	
	public SearchableComboBox(TreeSet<String> list, String initial)	{
		this.allItemsList = list;
		if(initial!=null){
			this.setSelectedIndex(getIndexOfInitial(initial,list));
			setModel(new DefaultComboBoxModel(list.toArray()));
		}
		
		strAuto = initial;
		
		listeners = new HashSet<IValidatedComboSearch>();
		
		for (Component comp : getComponents())
			if(comp instanceof JButton)
				originalComboButtonMouseListeners = comp.getMouseListeners();
		
		initialize();
	}
	
	public void addValidatedComboSearchListener(IValidatedComboSearch l ){
		listeners.add(l);
	}
	
	public void removeValidatedComboSearchListener(IValidatedComboSearch l ){
		listeners.remove(l);
	}
	
	public void fireInvalidCombo(){
		for(IValidatedComboSearch l : listeners)
			l.changedStatus("invalid");
	}
	
	public void fireValidCombo(){
		for(IValidatedComboSearch l : listeners)
			l.changedStatus("valid");
	}
	
	private void verifyValueCombo() {
		boolean status = false;
		if(isComboBoxItemValid())
			status = true;
		
		if(status != lastStatus ){
			if(status)
				fireValidCombo();
			else
				fireInvalidCombo();
			
			lastStatus = status;
		}
	}
	
	private void initialize(){
		setEditable(true);
		
		allItemsArrayList = new ArrayList<String>();
		for (int i = 0; i < getItemCount(); i++)
			allItemsArrayList.add(getItemAt(i).toString());
		
		for (Component comp : getComponents())
			if(comp instanceof JButton)
				comboButtonMouseListeners = comp.getMouseListeners();
		
		triggerFilledCombo();
		
		if(!listenersAlreadyInitialized)
			addListeners();
	}
	
	protected void addListeners(){
		addKeyListenerToComboBox();
		
		addFocusListenerToComboBox();
		
		addDocumentListenerToComboBoxEditor();
		
		listenersAlreadyInitialized = true;
	}
	
	// Variable used to prevent multiple focusEvents 
	private boolean lostFocus = false;
	private void addFocusListenerToComboBox() {
		getEditor().getEditorComponent().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if(!lostFocus) {
					if(getItemCount() > 0 && !allItemsArrayList.contains(strAuto)) {
						setSelectedIndex(0);
						repopulatePopUpList(getItemAt(0));
					}
					
					lostFocus=true;
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				lostFocus=false;
			}
		});
	}

	private void addDocumentListenerToComboBoxEditor() {
		((JTextField)getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(!typing) {
					strAuto = getEditor().getItem().toString();
					verifyValueCombo();
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
	}
	

	private void addKeyListenerToComboBox() {
		getEditor().getEditorComponent().addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				typing = true;
				
				if(!codes.contains(e.getKeyCode()) ){
					hidePopup();
					
					strAuto = ((JTextField)getEditor().getEditorComponent()).getText();
			    	int pos =((JTextField)getEditor().getEditorComponent()).getCaretPosition();
	
			    	ArrayList<String> objL = getMatchList(strAuto);
			    	  
			    	removeAllItems();
			    	  
			    	if(objL.size() == 0)
			    		triggerEmptyCombo();
			    	
			    	else {
			    		triggerFilledCombo();
			    		
			    		for (Object item : objL)
			    			addItem(item);
			    		
			    		((JTextField)getEditor().getEditorComponent()).setText(strAuto);
				    	((JTextField)getEditor().getEditorComponent()).setCaretPosition(pos);
			    	}
			    	  
			    	if(objL.size() != 0)
			    		showPopup();
			    	
			    	((JTextField)getEditor().getEditorComponent()).setText(strAuto);
			    	((JTextField)getEditor().getEditorComponent()).setCaretPosition(pos);
				}
								
				if((e.getKeyCode() == 10 || e.getKeyCode() == 27) && getItemCount() > 0) { //Enter and Esc
					if(getItemAt(getSelectedIndex()) == null) {
						((JTextField)getEditor().getEditorComponent()).setText(getItemAt(0).toString());
						setSelectedIndex(0);
					}
						
					strAuto = getSelectedItem().toString();
					((JTextField)getEditor().getEditorComponent()).setText(getSelectedItem().toString());
				}
				
				verifyValueCombo();
				
				typing = false;
			}
			
			@Override
			public void keyPressed(KeyEvent e) {				
			}
		});
	}
	
	protected void triggerEmptyCombo(){
		((JTextField)getEditor().getEditorComponent()).setForeground(Color.RED);
		for (Component comp : getComponents()) {
			if(comp instanceof AbstractButton) {
				comp.setEnabled(false);
				//Remove listeners
				for (MouseListener mouseListener : comp.getMouseListeners())
					comp.removeMouseListener(mouseListener);
			}
		}
	}

	protected void triggerFilledCombo(){
		((JTextField)getEditor().getEditorComponent()).setForeground(Color.BLACK);
		for (Component comp : getComponents()) {
			if(comp instanceof AbstractButton) {
				if(!comp.isEnabled()){
					comp.setEnabled(true);
					//Add original listeners
					for (MouseListener mouseListener : originalComboButtonMouseListeners)
						comp.addMouseListener(mouseListener);
				}
			}
		}
	}
	
	private void repopulatePopUpList(Object selectedItem) {
		setModel(new DefaultComboBoxModel(allItemsList.toArray()));
		if(selectedItem != null)
			setSelectedItem(selectedItem);
	}
	
	public void setNewValues(TreeSet<String> list, String initial)	{
		this.allItemsList = list;
		strAuto = initial;
		setModel(new DefaultComboBoxModel(list.toArray()));
		int index = getIndexOfInitial(initial, list);
		if(index >= 0 && list.size() != 0)
			this.setSelectedIndex(index);
		
		initialize();
	}
	
	public void addValue(String s){
		allItemsList.add(s);
		setNewValues(allItemsList, s);
	}
	
	public void removeValue(String text){
		if(text != null && !text.equals("") && allItemsList.contains(text)){
			allItemsList.remove(text);
			setNewValues(allItemsList, "");
		}
	}

	private int getIndexOfInitial(String initial, TreeSet<String> list) {
		int index = 0;
		int counter = 0;
		
		for(String s : list) {
			if(s.equals(initial))
				index = counter;
			counter++;
		}
		return index;
	  }
	
	private ArrayList<String> getMatchList(String str)	{
		ArrayList<String> objL = new ArrayList<String>();
		for (String item : allItemsArrayList) 
			if(item.toString().toLowerCase().contains(str.toLowerCase()))
				objL.add(item);

		return objL;
	}
	
	@Override
	public void updateUI(){
		super.updateUI();
//		initialize();
	}
	
	public boolean isComboBoxValid(){
		if(getSelectedIndex() > -1)
			return true;

		return false;
	}
	
	public boolean isComboBoxItemValid(){
		if(((DefaultComboBoxModel)getModel()).getIndexOf(strAuto) != -1)
			return true;

		return false;
	}
	
	static JFrame frame;
	public static void createPanel(Component comp, URL url)
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1, 2));
		final SearchableComboBox combo = new SearchableComboBox();
		
		TreeSet<String> list = new TreeSet<String>();
		String initial = "REACTION";
		list.add("TEST");
		list.add("COMBO");
		list.add("REACTION");
		list.add("GENE");
		list.add("R_EX_X");
		ArrayList<String[]> items = new ArrayList<String[]>();
		String[] strL = {"R_EX_glc_e","R_ICDHyr","R_Biomass_Ecoli_core_w_GAM","R_EX_nh4_e","R_PGK","R_PGM","R_EX_pi_e","R_GAPD","R_ACONTa","R_ACONTb","R_GLCpts","R_GLNS","R_EX_h_e","R_PIt2r","R_ENO","R_CS","R_NH4t","R_RPI", "A_dxs"};
		items.add(strL);
		
		for (String string : strL) {
			list.add(string);
		}

		combo.setNewValues(list, initial);
		System.out.println("Number: "+combo.getSelectedIndex()+"");
		System.out.println(combo.isComboBoxValid());

		//textfield = new JTextField();
		//frame.add(combo); 
//		frame.add(new JTextField(""));
//		frame.add(new JButton("B"));

		Icon icon;
		//Image img = ((ImageIcon) UIManager.getIcon("OptionPane.informationIcon")).getImage() ;
		String path = "/home/hgiesteira/Desktop/EclipseProjects/OptFlux/optfluxcore3/plugins_src/optflux.simulation/images/icons/s16x16/icon-sbml.png";
		Image img = new ImageIcon(path).getImage();
		Image newimg = img.getScaledInstance( 48, 48,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
//		JLabel warnErrorLabel = new JLabel("TEST", UIManager.getIcon("OptionPane.warningIcon")/*new ImageIcon(ERRORICONPATH)*/, JLabel.LEFT);
//		JLabel warnErrorLabel2 = new JLabel("TEST", icon, JLabel.LEFT);
//		frame.add(warnErrorLabel);
//		frame.add(warnErrorLabel2);
		
//		JLabel label = new JLabel("Waiting.....");
//		label.setBackground(Color.WHITE);
//		frame.add(label);
		
		frame.setPreferredSize(new Dimension(210,80));
		frame.setBackground(Color.WHITE);
		
		JLabel working = new JLabel("Working...");
		working.setOpaque(true);
		working.setBackground(Color.WHITE);
		working.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		//working.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.CENTER;
		working.setPreferredSize(new Dimension(210,80));
		//working.setIcon(new ImageIcon("/home/hgiesteira/git/aibench/plugins_src/es.uvigo.ei.aibench.workbench/images/longtask.gif"));
		
		
		
		//working.setIcon(new ImageIcon(url));
		
		
		frame.add(working);
		frame.setVisible(true);
		frame.pack();
		if(comp!= null)
			frame.setLocationRelativeTo(comp);
		
		
	}
		
	public static void hidePanel()
	{
		frame.setVisible(false);
		frame.dispose();
	}
	
//	public static void main(String[] args) {
//		SearchableComboBox.createPanel(null, null);
//		JFrame frame = new JFrame();
//		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
//		frame.setLayout(new GridLayout(1, 2));
//		final SearchableComboBox combo = new SearchableComboBox();
//		
//		combo.addValidatedComboSearchListener(new IValidatedComboSearch() {
//			
//			@Override
//			public void changedStatus(String s) {
//				System.out.println("PVVVVVVVVVVVVV " + s);
//				
//			}
//		});
//		
//		TreeSet<String> list = new TreeSet<String>();
//		String initial = "REACTION";
//		list.add("TEST");
//		list.add("COMBO");
//		list.add("REACTION");
//		list.add("GENE");
//		list.add("R_EX_X");
//		ArrayList<String[]> items = new ArrayList<String[]>();
//		String[] strL = {"R_EX_glc_e","R_ICDHyr","R_Biomass_Ecoli_core_w_GAM","R_EX_nh4_e","R_PGK","R_PGM","R_EX_pi_e","R_GAPD","R_ACONTa","R_ACONTb","R_GLCpts","R_GLNS","R_EX_h_e","R_PIt2r","R_ENO","R_CS","R_NH4t","R_RPI", "A_dxs"};
//		items.add(strL);
//		
//		for (String string : strL) {
//			list.add(string);
//		}
//
//		combo.setNewValues(list, initial);
//		System.out.println("Number: "+combo.getSelectedIndex()+"");
//		System.out.println(combo.isComboBoxValid());
//
//		//textfield = new JTextField();
//		frame.add(combo); 
//		frame.add(new JTextField(""));
//		frame.add(new JButton("B"));
//
//		Icon icon;
//		//Image img = ((ImageIcon) UIManager.getIcon("OptionPane.informationIcon")).getImage() ;
//		String path = "/home/hgiesteira/Desktop/EclipseProjects/OptFlux/optfluxcore3/plugins_src/optflux.simulation/images/icons/s16x16/icon-sbml.png";
//		Image img = new ImageIcon(path).getImage();
//		Image newimg = img.getScaledInstance( 48, 48,  java.awt.Image.SCALE_SMOOTH ) ;  
//		icon = new ImageIcon( newimg );
//		JLabel warnErrorLabel = new JLabel("TEST", UIManager.getIcon("OptionPane.warningIcon")/*new ImageIcon(ERRORICONPATH)*/, JLabel.LEFT);
//		JLabel warnErrorLabel2 = new JLabel("TEST", icon, JLabel.LEFT);
//		frame.add(warnErrorLabel);
//		frame.add(warnErrorLabel2);
//		
//		//frame.add(textfield);
//		frame.setVisible(true);
//		frame.pack();
//	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		
		frame.setLayout(new FlowLayout());
		
		
		Scanner s = null;
		
		final TreeSet<String> list = new TreeSet<String>();

        try {
            s = new Scanner(new BufferedReader(new FileReader("/home/hgiesteira/Desktop/ToDelete/Names")));

            while (s.hasNext()) {
            	list.add(s.next());
            }
        } catch (FileNotFoundException e) {
        	if (s != null) {
                s.close();
            }
			e.printStackTrace();
		} finally {
			if (s != null) {
                s.close();
            }
        }
		
		final SearchableComboBox combo = new SearchableComboBox();
		
		
		JComboBox cmb = new JComboBox();
		cmb.setEditable(true);
		cmb.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

		    @Override
		    public void keyReleased(KeyEvent event) {
		    	System.out.println("please dont make me blank");
//		        if (event.getKeyChar() == KeyEvent.VK_ENTER) {
//		            if (((JTextComponent) ((JComboBox) ((Component) event
//		                    .getSource()).getParent()).getEditor()
//		                    .getEditorComponent()).getText().isEmpty())
//		                System.out.println("please dont make me blank");
//		        }
		    }
		});
		
		JButton button = new JButton("Change");
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				combo.setNewValues(list, "Mills");
			}
		});
		
		JComboBox<String> normalCombo = new JComboBox<>();
		List<String> l = new ArrayList<>();
		l.add("Ab");
		
		for (String string : list) {
			normalCombo.addItem(string);
		}
		
		combo.setNewValues(list, "Mills");
		
		frame.add(combo);
		frame.add(normalCombo);
		frame.add(button);
		
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frame.setVisible(true);
		frame.pack();
		
//		System.out.println("AGORA!");
//		long init = System.currentTimeMillis();
//		normalCombo.setPopupVisible(false);
//		
//		System.out.println("T: " + (System.currentTimeMillis() - init));
//		long init2 = System.currentTimeMillis();
//		
//		System.out.println("AGAIN");
//		
//		normalCombo.setPopupVisible(true);
//		System.out.println("T: " + (System.currentTimeMillis() - init2));
	}
	//static JTextField textfield;
	
	
	//////////////////////////////////////////////
	// Message box with "Do not show this message again." option
//	public static void main(String[] args){
//		JFrame frame = new JFrame();
//		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
//		JCheckBox checkbox = new JCheckBox("Do not show this message again.");  
//		String message = "In this new version some workspaces may lose items. \nBe sure to make a backup before load them.";  
//		Object[] params = {message, checkbox};  
//		int n = JOptionPane.showConfirmDialog(frame, params, "OptFlux Information", JOptionPane.DEFAULT_OPTION);  
//		boolean dontShow = checkbox.isSelected(); 
//		System.out.println(dontShow);
//		
//		//frame.add(combo); 
//		frame.setVisible(true);
//		frame.pack();
//	}
	
}
