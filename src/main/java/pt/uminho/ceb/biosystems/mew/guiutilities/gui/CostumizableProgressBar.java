package pt.uminho.ceb.biosystems.mew.guiutilities.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class CostumizableProgressBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private static final int BORDER = 50;
	protected ImageIcon icon;
	final protected JProgressBar progressBar;
	protected int value;
//	protected int imageWidth;
//	protected int imageHeight;
	
//	/**
//	 * @wbp.parser.constructor
//	 */
//	public CostumizableProgressBar(int max, String imagePath, int imageWidth, int imageHeight) {
//		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
//		Image iconImage;
//		try {
//			iconImage = ImageIO.read(new File(imagePath));
//			Image iconResized = iconImage.getScaledInstance(imageWidth, imageHeight, 1000);
//			icon = new ImageIcon(iconResized);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		this.imageWidth = imageWidth;
//		this.imageHeight = imageHeight;
//		
//		progressBar = new JProgressBar(0, max);
//		value = 0;
//		initGUI();
//	}

	public CostumizableProgressBar(/*JFrame mainFrame,*/ int max, ImageIcon image) {
//		super();
//		this.setUndecorated(true);	
//		super(mainFrame);
//		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		icon = image;	
		progressBar = new JProgressBar(0, max);
		value = 0;
		initGUI();
		
	}
	
	public CostumizableProgressBar(/*JFrame mainFrame,*/ int max, String imagePath) {
//		super();
//		this.setUndecorated(true);	
//		super(mainFrame);
//		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Image iconImage;
		try {
			iconImage = ImageIO.read(new File(imagePath));
			icon = new ImageIcon(iconImage);
			
			System.out.println(icon.getIconWidth());
			System.out.println(icon.getIconHeight());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		progressBar = new JProgressBar(0, max);
		value = 0;
		initGUI();
		
	}

	protected void initGUI() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10};
		gridBagLayout.rowHeights = new int[]{10, 10};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1, 1};
		this.setLayout(gridBagLayout);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		JLabel label = new JLabel();
		if(icon!=null){
			label = new JLabel(icon);
			label.setSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		}
		label.setOpaque(true);
		label.setBackground(new Color(0, 0, 0, 0));
		
		this.add(label, gbc);
		
		

		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 0, 1);
		gbc_progressBar.fill = GridBagConstraints.BOTH;
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 1;
		add(progressBar, gbc_progressBar);
		progressBar.setVisible(true);
		progressBar.setIndeterminate(false);
		progressBar.setBorderPainted(false);
		progressBar.setBackground(new Color(0, 0, 0, 0));
		this.setOpaque(true);
		this.setBackground(new Color(0, 0, 0, 0));
		
	}
	
	public void increment() {
		progressBar.setValue(value++);
//		System.out.println(progressBar.getValue() + "\t" + value);
//		updateUI();
	}

	public void setString(String text) {
		progressBar.setString(text);
		progressBar.setStringPainted(true);
	}

	public void setValue(int i) {
		progressBar.setValue(i);
		
	}
}
