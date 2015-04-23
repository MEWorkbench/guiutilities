package pt.uminho.ceb.biosystems.mew.guiutilities.fonts;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.TreeMap;

import javax.swing.table.DefaultTableCellRenderer;

public class FontManager {
	public static int size = new DefaultTableCellRenderer().getFont().getSize();
	public static int type = new DefaultTableCellRenderer().getFont().getStyle();
	
	private static FontManager fm= null;
	
	public static FontManager getManager(){
		if(fm == null)
			fm = new FontManager();
		return fm;
	}
	
	static public String convertStyle(int s){
		
		String ret = "undef";
		switch (s) {
			case Font.BOLD:
				ret = "Bold";
				break;
			case Font.ITALIC:
				ret = "Italic";
				break;
			default:
				ret="Plain";
				break;
		}
		return ret;
	}
	
	static public int convertStyle(String s){
		if(s.toLowerCase().endsWith("italic"))
			return Font.ITALIC;
		if(s.toLowerCase().endsWith("bold"))
			return Font.BOLD;
		return Font.PLAIN;
	}
	
	
//	private Font[] fonts = null;
	private TreeMap<String, Font> fontsMap= null;
	
	private FontManager(){
		init();
	}

	private void init() {
		
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] sFonts = e.getAllFonts();
		
		fontsMap = new TreeMap<String, Font>();
		
		for(Font f : sFonts)
			fontsMap.put(f.getFontName(), f);
	}
	
	public Font[] getAllFonts(){
		Font[] fonts = new Font[fontsMap.values().size()];
		return fontsMap.values().toArray(fonts);
	}
	
	public Font getFont(String name) throws FontException{
		if(fontsMap.containsKey(name))
			return fontsMap.get(name);
		throw new FontException("Invalid font '" + name + "'!");
	}
	
	public Font getRenderingFont(String name) throws FontException{
		return new RenderingFont(getFont(name));
	}
}
