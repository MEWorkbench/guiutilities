package pt.uminho.ceb.biosystems.mew.guiutilities.gui.table.celleditor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JButton button;
	protected Object value;
	
	public ButtonEditor() {
		this.button = new JButton();
		button.addActionListener(this);
	}
	
	public ButtonEditor(String caption) {
		this.button = new JButton(caption);
		button.addActionListener(this);
	}
	
	public ButtonEditor(Icon icon) {
		this.button = new JButton(icon);
		button.addActionListener(this);
	}
		
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,int row, int column) {
//		if(button.getText() == null && button.getIcon() == null)
//    		button.setText(value.toString());
		this.value = value;
		return button;
	}
	
	public Object getCellEditorValue() {
		return value;
	}
	
	public boolean stopCellEditing() {
		return super.stopCellEditing();
	}
	
	public void cancelCellEditing() {
		super.cancelCellEditing();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		fireEditingStopped();
	}
}