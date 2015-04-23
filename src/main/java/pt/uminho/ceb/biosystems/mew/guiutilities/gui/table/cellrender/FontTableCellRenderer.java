package pt.uminho.ceb.biosystems.mew.guiutilities.gui.table.cellrender;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import pt.uminho.ceb.biosystems.mew.guiutilities.fonts.RenderingFont;

public class FontTableCellRenderer extends DefaultTableCellRenderer{
	
	private static final long serialVersionUID = -4107190186421136479L;

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		
		RenderingFont f = null;
		try {
			f = (RenderingFont) arg1;
			this.setText(f.toString());
			this.setFont(f);
		} catch (Exception e) {e.printStackTrace();}
		
		return this;
	}

}
