package pt.uminho.ceb.biosystems.mew.guiutilities.fonts;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;

public class FontsComboBoxEditor extends BasicComboBoxEditor{

	public FontsComboBoxEditor(){
		super();
	}
	
	@Override
	public void setItem(Object anObject) {
		super.setItem(anObject);
		editor.setFont((Font) anObject);
	}
	
	@Override
	protected JTextField createEditorComponent(){
		editor = new JTextField();
		editor.setEditable(false);
		return editor;
	}
	
	
	
}
