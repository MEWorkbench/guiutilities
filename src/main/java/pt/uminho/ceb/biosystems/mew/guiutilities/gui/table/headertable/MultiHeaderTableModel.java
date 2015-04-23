package pt.uminho.ceb.biosystems.mew.guiutilities.gui.table.headertable;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class MultiHeaderTableModel {
	
	protected MultiHeaderModel					headersModel;
	protected DefaultTableModel					datamodel;
	protected Map<String, TableCellRenderer>	cellRenders			= new HashMap<String, TableCellRenderer>();
	protected Map<String, TableCellEditor>		cellEditor			= new HashMap<String, TableCellEditor>();
	private int									preferedColumnWidth	= 150;
	
	public MultiHeaderTableModel() {
		super();
		headersModel = new MultiHeaderModel();
		datamodel = new DefaultTableModel();
		//		headersModel.getValueAt(row, column)
	}
	
	public void addHeader(Object[] header) {
		
		if (headersModel.getColumnCount() == 0) headersModel.setColumnCount(header.length);
		headersModel.addRow(header);
	}
	
	public void addRow(Object[] row) {
		
		if (datamodel.getColumnCount() == 0) {
			datamodel.setColumnCount(row.length);
		}
		datamodel.addRow(row);
	}
	
	public TableModel getDataModel() {
		return datamodel;
	}
	
	public Object getValueAt(int row, int column) {
		return datamodel.getValueAt(row, column);
	}
	
	//	public void setEditable(int row, int column, boolean editable){
	//		iscelleditable.put(key, editable);
	//	}
	
	public Class<?> getColumnClass(int columnIndex) {
		return datamodel.getColumnClass(columnIndex);
	}
	
	public int getColumnCount() {
		return datamodel.getColumnCount();
	}
	
	public String getColumnName(int columnIndex) {
		return datamodel.getColumnName(columnIndex);
	}
	
	public int getRowCount() {
		return datamodel.getRowCount() + headersModel.getRowCount();
	}
	
	public int getNumberOfHeaders() {
		return headersModel.getRowCount();
	}
	
	public MultiHeaderModel getHeadersModel() {
		return headersModel;
	}
	
	public void setCellEditor(int row, int column, TableCellEditor tce) {
		String key = row + "" + column;
		cellEditor.put(key, tce);
		headersModel.setEditable(row, column, true);
	}
	
	public void setEditable(int row, int column, boolean editable) {
		headersModel.setEditable(row, column, editable);
	}
	
	public void setCellRenders(int row, int column, TableCellRenderer tce) {		
		String key = row + "" + column;
		cellRenders.put(key, tce);
	}
	
	public TableCellRenderer getCellRenderer(int row, int column) {
		String key = row + "" + column;
		return cellRenders.get(key);
	}
	
	public TableCellEditor getCellEditor(int row, int column) {
		String key = row + "" + column;
		return cellEditor.get(key);
	}
	
	public void removeTableModelListener(TableModelListener l) {
		datamodel.removeTableModelListener(l);
		
	}
	
	public void addTableModelListener(TableModelListener l) {
		headersModel.addTableModelListener(l);
		datamodel.addTableModelListener(l);
	}
	
	public JScrollPane getMultiHeaderTable(TableModelListener l) {
		
		JTable rows = new JTable(datamodel);
		JTable headers = new JHeaderTable(headersModel, cellRenders, cellEditor);
		headersModel.addTableModelListener(l);

		for (int i = 0; i < rows.getColumnCount(); i++) {
			rows.getColumnModel().getColumn(i).setMinWidth(preferedColumnWidth);
			headers.getColumnModel().getColumn(i).setMinWidth(preferedColumnWidth);
		}
		JScrollPane sp = new JScrollPane(rows);
		
		CorrectStrangeBehaviourListener t = new CorrectStrangeBehaviourListener(rows, sp, headers);
		sp.addComponentListener(t);
		
		replaceColumnHeader(sp, headers);
		
		return sp;
		
	}
	
	private class CorrectStrangeBehaviourListener extends ComponentAdapter {
		
		JTable		mainTable			= null;
		JTable		headers				= null;
		JScrollPane	mainTableScrollPane	= null;
		
		public CorrectStrangeBehaviourListener(JTable mainTable, JScrollPane mainTableScrollPane, JTable headers) {
			super();
			this.mainTable = mainTable;
			this.mainTableScrollPane = mainTableScrollPane;
			this.headers = headers;
		}
		
		public void componentResized(ComponentEvent e) {
			if (mainTable.getPreferredSize().width <= mainTableScrollPane.getViewport().getExtentSize().width) {
				mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				headers.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			} else {
				mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				headers.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			}
		}
	}
	
	public static final void replaceColumnHeader(JScrollPane scrollPane, JComponent newHeader) {
		final class HeaderSwapper implements HierarchyListener {
			private final JComponent	c;
			
			private HeaderSwapper(JComponent c) {
				this.c = c;
			}
			
			public void hierarchyChanged(HierarchyEvent e) {
				JScrollPane p = (JScrollPane) e.getSource();
				if (p.isShowing()) {
					JViewport v = new JViewport();
					v.setView(this.c);
					p.setColumnHeader(v);
				}
			}
		}
		
		scrollPane.addHierarchyListener(new HeaderSwapper(newHeader));
	}
	
}
