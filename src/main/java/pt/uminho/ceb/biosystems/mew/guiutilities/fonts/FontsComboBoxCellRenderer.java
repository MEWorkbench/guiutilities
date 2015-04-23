package pt.uminho.ceb.biosystems.mew.guiutilities.fonts;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

public class FontsComboBoxCellRenderer extends DefaultListCellRenderer{

	private static final long serialVersionUID = -2047195183734897476L;
	
	protected JLabel label;
	
	
	public FontsComboBoxCellRenderer(){
		super();
		label = new JLabel();
	}

	
	public Component getListCellRendererComponent(JList<?> list,Object value,int index,boolean isSelected,boolean cellHasFocus){  
		RenderingFont font = (RenderingFont) value;
		label.setFont(font);
		label.setText(font.toString());
		return label;
	}
	
}