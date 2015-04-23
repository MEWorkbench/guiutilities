package pt.uminho.ceb.biosystems.mew.guiutilities.gui.table.cellrender;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

public class ColorRenderer implements TableCellRenderer{

	protected JTextField label = new JTextField();
	
	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		
		Color c = null;
		try {
			c = (Color) arg1;
		} catch (Exception e) {
			c = Color.orange;
			e.printStackTrace();
		}
		
		label.setBackground(c);
		return label;
	}

}
