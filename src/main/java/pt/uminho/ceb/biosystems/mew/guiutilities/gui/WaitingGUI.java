package pt.uminho.ceb.biosystems.mew.guiutilities.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class WaitingGUI extends JDialog {
	
	public WaitingGUI(){
		this(null, null, null);
	}
	
	public WaitingGUI(Component comp, URL urlIcon, String textGUI) {
		//Window without java icon
		this.setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE));
		this.setTitle("Progress...");
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel(new BorderLayout());
		this.setLayout(new BorderLayout());
		
		
		this.setAlwaysOnTop(true);
		
		JLabel working = new JLabel("Working...");
		if(textGUI != null)
			working.setText(textGUI);
		
		panel.setOpaque(true);
		panel.setBackground(Color.WHITE);
		panel.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.CENTER;
	    panel.setMinimumSize(new Dimension(200, 50));
		
		if(urlIcon != null)
			working.setIcon(new ImageIcon(urlIcon));
		
		this.setMinimumSize(new Dimension(200, 50));
		
		panel.add(working);
		this.add(panel);
		
		if(comp!= null)
			this.setLocationRelativeTo(comp);
			
		this.setVisible(true);
		this.pack();
		this.revalidate();
		this.setResizable(true);
		this.repaint();
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
	
	public static void main(String[] args) {
		
			WaitingGUI wa = new WaitingGUI(null, null, 
					"Connecting to server...");
			
			WaitingGUI wa2 = new WaitingGUI(null, null, null);
			
			JDialog dialog = new JDialog();
			
			JFileChooser cho = new JFileChooser();
			
			cho.setDialogType(JFileChooser.OPEN_DIALOG);
			//method(cho, dialog);
//			int returnVal = cho.showSaveDialog(dialog);
//			int result = JOptionPane.showConfirmDialog(dialog,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
//            switch(result){
//                case JOptionPane.YES_OPTION:
//                	cho.approveSelection();
//                    return;
//                case JOptionPane.NO_OPTION:
//                	method(cho, dialog);
//                    return;
//                case JOptionPane.CLOSED_OPTION:
//                    return;
//                case JOptionPane.CANCEL_OPTION:
//                	cho.cancelSelection();
//                    return;
//            }
//			System.out.println("JFileChooser.OPEN_DIALOG: " +JFileChooser.OPEN_DIALOG  + " JFileChooser.SAVE_DIALOG: " + JFileChooser.SAVE_DIALOG);
//			System.out.println(cho.getDialogType());
			
			
//			System.out.println(returnVal);
			
//			dialog.add(cho);
			
			
	}
	
	public static void method(JFileChooser cho, JDialog dialog)
	{
		System.out.println(cho.showSaveDialog(dialog));
		int result = JOptionPane.showConfirmDialog(dialog,"The file already exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
        switch(result){
            case JOptionPane.YES_OPTION:
            	cho.approveSelection();
            	System.out.println(result);
                return;
            case JOptionPane.NO_OPTION:
            	method(cho, dialog);
            	System.out.println(result);
                return;
            case JOptionPane.CLOSED_OPTION:
                return;
            case JOptionPane.CANCEL_OPTION:
            	cho.cancelSelection();
                return;
        }
        //return returnVal;
	}

}
