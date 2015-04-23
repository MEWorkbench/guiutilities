package pt.uminho.ceb.biosystems.mew.guiutilities.fonts;

import java.awt.Font;

public class RenderingFont extends Font{
	
	private static final long serialVersionUID = 3492213255677189140L;
	
	public static int renderingSize = 12;

	
	public RenderingFont(Font f){
		super(f.getFontName(), f.getStyle(), renderingSize); 
	}

	
	@Override
	public String toString() {
		return getName();
	}
}