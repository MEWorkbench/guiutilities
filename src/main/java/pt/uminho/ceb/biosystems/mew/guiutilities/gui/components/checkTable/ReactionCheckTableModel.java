package pt.uminho.ceb.biosystems.mew.guiutilities.gui.components.checkTable;

import java.util.HashSet;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

public class ReactionCheckTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private String[] columnNames; 
    private Object[][] data;

    public ReactionCheckTableModel(Set<String> reactionIds){
    	
    	columnNames = new String[]{ "Reaction Id", "Use" };
		data = new Object[reactionIds.size()][2];
		
		int i = 0;
		for(String s : reactionIds){
			data[i][0] = s;
			data[i][1] = new Boolean(false);
			i++;
		}
    }
    
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    public boolean isCellEditable(int row, int col) {

        if (col < 1) {
            return false;
        } else {
            return true;
        }
    }
    
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
    
    public Set<String> getReactionsSelected(){
    	
    	Set<String> result = new HashSet<String>();
    	for(int i= 0; i< data.length; i++){
    		
    		if((Boolean)getValueAt(i, 1)){
    			result.add((String) getValueAt(i, 0));
    		}
    	}
    	
    	return result;
    }
}
