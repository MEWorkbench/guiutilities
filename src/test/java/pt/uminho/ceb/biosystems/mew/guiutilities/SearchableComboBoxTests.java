package pt.uminho.ceb.biosystems.mew.guiutilities;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import pt.uminho.ceb.biosystems.mew.guiutilities.gui.components.searchableCombo.SearchableComboBox;

public class SearchableComboBoxTests {
	
	public static void main(String[] args) {
		JDialog dialog = new JDialog();
		dialog.setLayout(new FlowLayout());
		
		TreeSet<String> list = new TreeSet<String>();

		try{
			URL url = new URL("https://www.vocabulary.com/lists/52473#view=notes");
	
	        URLConnection con = url.openConnection();
	        InputStream is =con.getInputStream();
	
	
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	
	        String line = null;
	
	        while ((line = br.readLine()) != null) {
	        	String s = "lang=\"en\" word=\"";
	        	if(line.contains(s)){
	        		int firstIndex = line.indexOf(s);
	        		int lastIndex = line.indexOf("\" freq=");
	        		list.add(line.substring(firstIndex+s.length(), lastIndex));
	        	}
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		
		SearchableComboBox combo = new SearchableComboBox();
		combo.setNewValues(list, list.first());
		
		combo.setPreferredSize(new Dimension(200, 40));
		dialog.add(combo);
		
		final JComboBox<String> normalCombo = new JComboBox<>();
		for (String string : list) {
			normalCombo.addItem(string);
			normalCombo.addItem(string + "_TEST");
		}
		
		final JButton button = new JButton("Show");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				if(normalCombo.isPopupVisible()){
//					long init = System.currentTimeMillis();
//					normalCombo.hidePopup();
//					System.out.println("Time to hide: " +(System.currentTimeMillis() - init));
//				}
//				else{
					long init = System.currentTimeMillis();
					normalCombo.showPopup();
					System.out.println("Time to show: " +(System.currentTimeMillis() - init));
//				}
			}
		});
		
		dialog.add(button);
		dialog.add(normalCombo);
		
		dialog.setPreferredSize(new Dimension(300, 150));
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.pack();
		dialog.setVisible(true);
	}
	
}
