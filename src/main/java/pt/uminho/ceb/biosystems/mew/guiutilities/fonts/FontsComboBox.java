package pt.uminho.ceb.biosystems.mew.guiutilities.fonts;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.WindowConstants;

public class FontsComboBox extends JComboBox<RenderingFont>{
	
	private static final long serialVersionUID = -7108298314257326840L;

	
	public FontsComboBox(){
		super();
		setRenderer(new FontsComboBoxCellRenderer());
		setEditable(true);
		setEditor(new FontsComboBoxEditor());
		buildComboModel();
//		setSelectedFont(fontName);
	}
	
	public void setSelectedFont(RenderingFont font) throws FontException{
		setSelectedFont(font.getName());
	}
	
	public void setSelectedFont(String fontName) throws FontException{
		Font[] sFonts = FontManager.getManager().getAllFonts();
		boolean notFound = true;
		int i=0;
		while(notFound && i<sFonts.length)
			if(sFonts[i++].getFontName().equals(fontName))
				notFound = false;
		
		if(notFound)
			throw new FontException("The font '" + fontName + "' is not valid in the system.");
		
		setSelectedIndex(--i);
	}
	
	public Font getSelectedFont(){
		return (Font) getSelectedItem();
	}
	
	protected void buildComboModel(){
		Font[] sFonts = FontManager.getManager().getAllFonts();
		RenderingFont[] systemFonts = new RenderingFont[sFonts.length];
		for(int i=0; i<sFonts.length; i++)
			systemFonts[i] = new RenderingFont(sFonts[i]);
		setModel(new DefaultComboBoxModel<RenderingFont>(systemFonts));
	}
	

	@Override
	public ListCellRenderer<? super Font> getRenderer() {
		return new FontsComboBoxCellRenderer();
	}
	
	
	public static void main(String[] a) {
		JFrame frame = new JFrame("FontComboBoxCellRenderer");
		
		final FontsComboBox fontsBox = new FontsComboBox();
		
		JPanel p = new JPanel();
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.rowWeights = new double[] {0.1f, 0.1f};
		thisLayout.columnWeights = new double[] {1.0f};
		p.setLayout(thisLayout);
		
		p.add(fontsBox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2,2,2,2), 0, 0));
		JButton b = new JButton("-----");
		p.add(b, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2,2,2,2), 0, 0));
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(fontsBox.getSelectedFont().getName());
			}
		});
		
		frame.setContentPane(p);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setSize(300, 100);
	}
}
